/*
 * Copyright 2011 Ivo Woltring
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.ivonet.beanutils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Asserts "simple" beans that are constructed using the "Builder" pattern as documented
 * by Joshua Bloch ("Effective Java" - Second Edition).
 * <p/>
 * The Beans constructed this way are after construction immutable or at least that is
 * the intention for using the builder.
 * See http://www.javapractices.com/topic/TopicAction.do?Id=29 for good reasons to make objects immutable.
 * Construction can be done in stages by "building" the object but after the final build we can only read the object.
 * <p/>
 * The following assumptions are asserted:
 * All properties that can be set on the builder should be reflected by the read method on the class to be build.
 * All Builders should have a build() method (convention)
 * By preference one should call the Builder class "Builder"
 *
 * @author Ivo Woltring
 */
public class BuilderBeanAsserter extends Asserter {

    private static final String BUILDER_NAME = "Builder";
    private static final String BUILD_METHOD_NAME = "build";

    /**
     * Tests a bean created by a builder.
     *
     * @param classUnderTest          the class to test
     * @param builderUnderTest        the builder that creates the classUnderTest
     * @param buildMethodName         the name of the method that builds the classUnderTest
     * @param excludedBuilderMethods  properties to exclude from the test in the builder
     * @param excludedClassProperties properties to exclude from the test in de class under test
     * @param <T>                     the type of the classUnderTest
     * @param <B>                     the type of the builder
     */
    public static <T, B> void assertBuildObjectGetterBehavior(final Class<T> classUnderTest,
                                                              final Class<B> builderUnderTest,
                                                              final String buildMethodName,
                                                              final List<String> excludedBuilderMethods,
                                                              final List<String> excludedClassProperties) {

        final List<String> blacklistBuilderMethods;
        if (excludedBuilderMethods != null) {
            blacklistBuilderMethods = new ArrayList<String>(excludedBuilderMethods);
        } else {
            blacklistBuilderMethods = new ArrayList<String>(1);
        }
        blacklistBuilderMethods.add("class");

        final List<String> blackListClassProperties;
        if (excludedClassProperties != null) {
            blackListClassProperties = new ArrayList<String>(excludedClassProperties);
        } else {
            blackListClassProperties = new ArrayList<String>(1);
        }
        blackListClassProperties.add("class");

        try {
            final Constructor[] constructors = builderUnderTest.getDeclaredConstructors();
            if (constructors.length > 1) {
                fail("There should only be one constructor in a Builder class");
            }
            final Method buildMethod = builderUnderTest.getMethod(buildMethodName);

            @SuppressWarnings({"unchecked"})
            final B builder = (B) Asserter.createObject(constructors[0]);

            final Method[] methods = builderUnderTest.getDeclaredMethods();
            assertFalse("There should be builder methods ", methods.length == 0);
            boolean hasBuilderMethods = false;
            for (final Method method : methods) {
                if (builderUnderTest.getSimpleName().equals(method.getReturnType().getSimpleName())) {
                    hasBuilderMethods = true;
                    if (blacklistBuilderMethods.contains(method.getName())) {
                        continue;
                    }
                    method.invoke(builder, createMethodParameterList(method));
                }
            }
            assertTrue("No builder methods found in the builder. Do the builder methods have the Builder as returnType?",
                              hasBuilderMethods);
            @SuppressWarnings({"unchecked"})
            final T objectUnderTest = (T) buildMethod.invoke(builder);

            final BeanInfo beanInfo = Introspector.getBeanInfo(classUnderTest);
            final PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            for (final PropertyDescriptor descriptor : descriptors) {
                if (descriptor.getWriteMethod() != null) {
                    fail("This object is not immutable. It has a writeMethod for: " + descriptor.getName());
                }
                if (blackListClassProperties.contains(descriptor.getDisplayName())) {
                    continue;
                }

                final Object arg = retrieveDefaultValueByType(descriptor.getPropertyType());
                final Method readMethod = descriptor.getReadMethod();
                assertEquals(String.format("Not the expected value for method %s", readMethod.getName()), arg,
                                    readMethod.invoke(objectUnderTest));
            }
        } catch (InvocationTargetException e) {
            fail(e.getMessage());
        } catch (NoSuchMethodException e) {
            fail(e.getMessage());
        } catch (IntrospectionException e) {
            fail(e.getMessage());
        } catch (IllegalAccessException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Tests a Bean created with a Builder.
     * <p/>
     * This test assumes that the builder is called "Builder" and the build method is called "build"
     *
     * @param classUnderTest the class to test
     * @param <T>            the type of the classUnderTest
     */
    public static <T> void assertBuildObjectGetterBehavior(final Class<T> classUnderTest) {

        assertBuildObjectGetterBehavior(classUnderTest, findBuilder(classUnderTest), BUILD_METHOD_NAME,
                                               Collections.<String>emptyList(), Collections.<String>emptyList());

    }

    /**
     * Tests a Bean created with a Builder.
     * <p/>
     * This method accepts the a different Builder name than the default.
     *
     * @param classUnderTest      the class to test
     * @param exclusionProperties class under test properties to exclude from the test
     * @param <T>                 the type of the classUnderTest
     */
    public static <T> void assertBuildObjectGetterBehavior(final Class<T> classUnderTest,
                                                           final String... exclusionProperties) {

        assertBuildObjectGetterBehavior(classUnderTest, findBuilder(classUnderTest), BUILD_METHOD_NAME,
                                               Collections.<String>emptyList(), Arrays.asList(exclusionProperties));

    }

    /**
     * Tests a Bean created with a Builder.
     * <p/>
     * This method accepts the a different Builder name than the default.
     *
     * @param classUnderTest   the class to test
     * @param builderUnderTest the builder that creates the classUnderTest
     * @param <T>              the type of the classUnderTest
     * @param <B>              the type of the builder
     */
    public static <T, B> void assertBuildObjectGetterBehavior(final Class<T> classUnderTest,
                                                              final Class<B> builderUnderTest) {

        assertBuildObjectGetterBehavior(classUnderTest, builderUnderTest, BUILD_METHOD_NAME,
                                               Collections.<String>emptyList(), Collections.<String>emptyList());

    }

    /**
     * Tests a Bean created with a Builder.
     * <p/>
     * This method accepts the a different Builder name than the default.
     *
     * @param classUnderTest      the class to test
     * @param builderUnderTest    the builder that creates the classUnderTest
     * @param exclusionProperties the properties to ignore on the class under test.
     * @param <T>                 the type of the classUnderTest
     * @param <B>                 the type of the builder
     */
    public static <T, B> void assertBuildObjectGetterBehavior(final Class<T> classUnderTest,
                                                              final Class<B> builderUnderTest,
                                                              final String... exclusionProperties) {

        assertBuildObjectGetterBehavior(classUnderTest, builderUnderTest, BUILD_METHOD_NAME,
                                               Collections.<String>emptyList(), Arrays.asList(exclusionProperties));
    }

    /**
     * Tests a Bean created with a Builder.
     * <p/>
     * This method accepts the a different Builder name than the default.
     *
     * @param classUnderTest       the class to test
     * @param exclusionMethodNames the properties to ignore on the builder under test.
     * @param <T>                  the type of the classUnderTest
     */
    public static <T> void assertBuildObjectGetterBehavior(final Class<T> classUnderTest,
                                                           final List<String> exclusionMethodNames) {

        assertBuildObjectGetterBehavior(classUnderTest, findBuilder(classUnderTest), BUILD_METHOD_NAME,
                                               exclusionMethodNames, Collections.<String>emptyList());
    }

    private static Class<?> findBuilder(final Class classUnderTest) {
        final Class<?>[] declaredClasses = classUnderTest.getDeclaredClasses();
        for (final Class<?> declaredClass : declaredClasses) {
            if (BUILDER_NAME.equals(declaredClass.getSimpleName())) {
                return declaredClass;
            }
        }
        fail("No Build class found.");
        return null;
    }
}
