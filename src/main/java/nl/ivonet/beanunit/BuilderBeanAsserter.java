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

package nl.ivonet.beanunit;

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
 * By preference one should call the Builder class "Builder" but this is not mandatory.
 *
 * @author Ivo Woltring
 */
public class BuilderBeanAsserter extends Asserter {

    private static final String BUILDER_NAME = "Builder";
    private static final String BUILD_METHOD_NAME = "build";
    private static final String METHOD_IGNORE_CHARACTER = "$";

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

        final List<String> blackListClassProperties = convertExclusions(excludedClassProperties);
        blackListClassProperties.add(ALWAYS_EXCLUDED);

        try {
            @SuppressWarnings({"unchecked"})
            final T objectUnderTest = (T) createBean(builderUnderTest, buildMethodName, excludedBuilderMethods);

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

    private static <B> Object createBean(final Class<B> builderUnderTest, final String buildMethodName,
                                         final List<String> excludedBuilderMethods)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        final List<String> blacklistBuilderMethods = convertExclusions(excludedBuilderMethods);
        blacklistBuilderMethods.add(ALWAYS_EXCLUDED);
        blacklistBuilderMethods.add(buildMethodName);

        final Constructor[] constructors = builderUnderTest.getDeclaredConstructors();
        if (constructors.length > 1) {
            fail("There should only be one constructor in a Builder class");
        }
        final Method buildMethod = builderUnderTest.getMethod(buildMethodName);

        @SuppressWarnings({"unchecked"})
        final B builder = (B) createObject(constructors[0]);

        final Method[] methods = builderUnderTest.getDeclaredMethods();
        assertFalse("There should be builder methods ", methods.length == 0);
        boolean hasBuilderMethods = false;
        for (final Method method : methods) {
            if (blacklistBuilderMethods.contains(method.getName())) {
                if (isReturnTypeTheBuilder(builderUnderTest, method)) {
                    hasBuilderMethods = true;
                }
                continue;
            }
            if (isReturnTypeTheBuilder(builderUnderTest, method)) {
                hasBuilderMethods = true;
                method.invoke(builder, createMethodParameterList(method));
            } else if (methodToTest(method)) {
                fail(String.format("The return type of method %s is not the Builder type.", method.getName()));
            }
        }
        assertTrue("No builder methods found in the builder. Do the builder methods have the Builder as returnType?",
                          hasBuilderMethods);
        return buildMethod.invoke(builder);
    }

    private static boolean isReturnTypeTheBuilder(final Class builderUnderTest, final Method method) {
        return builderUnderTest.getSimpleName().equals(method.getReturnType().getSimpleName());
    }

    private static boolean methodToTest(final Method method) {
        return !method.getName().contains(METHOD_IGNORE_CHARACTER);
    }

    private static List<String> convertExclusions(final List<String> excludedBuilderMethods) {
        final List<String> blacklistBuilderMethods;
        if (excludedBuilderMethods != null) {
            blacklistBuilderMethods = new ArrayList<String>(excludedBuilderMethods);
        } else {
            blacklistBuilderMethods = new ArrayList<String>(1);
        }
        return blacklistBuilderMethods;

    }

    /**
     * Tests all the flows of the overridden equals and hashCode methods of a class.
     * <p/>
     * - it ensures that if A == B, B == A also
     * - it tests the equals and hasCode methods for all the separate writable properties
     * - it only tests if the equals method and the hashCode method are both overridden
     * - it fails of only one is overridden
     * - both methods need to be overridden at the same level (not java.lang.Object)
     * <p/>
     * You can exclude properties by adding them to the method argument list.
     *
     * @param classUnderTest     the implementation.class
     * @param excludedProperties string representation of all the properties excluded from the equals test , e.g. "firstName"
     * @param <T>                the type of the class to test
     */
    public static <T> void assertEqualsHashCode(final Class<T> classUnderTest, final String... excludedProperties) {
        assertEqualsHashCode(classUnderTest, findBuilder(classUnderTest), BUILD_METHOD_NAME,
                                    convertExclusions(excludedProperties));

    }

    /**
     * Tests all the flows of the overridden equals and hashCode methods of a class.
     * <p/>
     * - it ensures that if A == B, B == A also
     * - it tests the equals and hasCode methods for all the separate writable properties
     * - it only tests if the equals method and the hashCode method are both overridden
     * - it fails of only one is overridden
     * - The both methods need to be overridden at the same level (not java.lang.Object)
     * <p/>
     * You can exclude properties by adding them to the method argument list.
     *
     * @param classUnderTest     the implementation.class
     * @param builderUnderTest   the Builder class
     * @param buildMethod        the string name of the build method
     * @param excludedProperties string representation of all the properties excluded from the equals test , e.g. "firstName"
     * @param <T>                the type of the class to test
     */
    public static <T, B> void assertEqualsHashCode(final Class<T> classUnderTest, final Class<B> builderUnderTest,
                                                   final String buildMethod, final List<String> excludedProperties) {
        final ArrayList<String> blacklist = new ArrayList<String>(excludedProperties);
        blacklist.add(ALWAYS_EXCLUDED);
        try {
            @SuppressWarnings({"unchecked"}) final T one = (T) createBean(builderUnderTest, buildMethod,
                                                                                 excludedProperties);
            @SuppressWarnings({"unchecked"}) final T two = (T) createBean(builderUnderTest, buildMethod,
                                                                                 excludedProperties);

            final Class<?> equalsDeclaringClass = retrieveEqualsMethodDeclaringClass(classUnderTest);
            final Class<?> hashCodeDeclaringClass = classUnderTest.getMethod(HASH_CODE_METHOD_NAME).getDeclaringClass();
            if (doesNotOverrideObjectMethod(equalsDeclaringClass)) {
                fail("If this test is run the equals() method must be overridden by the class under test.");
            }
            if (doesNotOverrideObjectMethod(hashCodeDeclaringClass)) {
                fail("If equals() method is overridden the hashCode() method must also be overridden by the class under test.");
            }
            if (!hashCodeDeclaringClass.getName().equals(equalsDeclaringClass.getName())) {
                fail(String.format("The equals and hashCode methods of Class<%s> have different declaring classes.",
                                          classUnderTest));
            }

            assertTrue("Two instances build the same way are not equal (o1.equals(o2))", one.equals(two));
            assertTrue("Two instances build the same way do not have the same hashcode",
                              one.hashCode() == two.hashCode());

            assertTrue("Instances with default constructor not equal (o1.equals(o1))", one.equals(one));

            assertTrue("Instances with default constructor not equal (o2.equals(o1))", two.equals(one));
            assertFalse("Equaling different types of object should not be equal", one.equals(new OtherType()));
            //noinspection ObjectEqualsNull
            assertFalse("Equaling null type should not be equal", one.equals(null));

        } catch (IllegalAccessException e) {
            fail(e.getMessage());
        } catch (NoSuchMethodException e) {
            fail(e.getMessage());
        } catch (InvocationTargetException e) {
            fail(e.getMessage());
        }
    }

    private static List<String> convertExclusions(final String... excludedBuilderMethods) {
        return convertExclusions(Arrays.asList(excludedBuilderMethods));
    }

    private static boolean doesNotOverrideObjectMethod(final Class<?> clazz) {
        return clazz.getName().equals(JAVA_LANG_OBJECT);
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
        fail(String.format("No Builder class found for class under test [%s].", classUnderTest.getSimpleName()));
        return null;
    }

    /**
     * Performs all the tests defined in this class on a Builder bean.
     * <p/>
     * In most situations this is the method to use.
     *
     * @param classUnderTest       the class to test
     * @param exclusionMethodNames the properties to ignore on the builder under test.
     * @param <T>                  the type of the classUnderTest
     */
    public static <T> void assertBean(final Class<T> classUnderTest, final String... exclusionMethodNames) {
        assertBuildObjectGetterBehavior(classUnderTest, exclusionMethodNames);
        try {
            final Class<?> declaringClass = retrieveEqualsMethodDeclaringClass(classUnderTest);
            if (classUnderTest.getSimpleName().equals(declaringClass.getSimpleName())) {
                assertEqualsHashCode(classUnderTest, exclusionMethodNames);
            }
        } catch (NoSuchMethodException e) {
            fail("Should never be possible unless the equals class has been removed from Object");
        }
    }

    /**
     * Performs all the tests defined in this class on a Builder bean.
     * <p/>
     * In most situations this is the method to use.
     *
     * @param classUnderTest      the class to test
     * @param builderUnderTest    the builder that creates the classUnderTest
     * @param exclusionProperties the properties to ignore on the class under test.
     * @param <T>                 the type of the classUnderTest
     * @param <B>                 the type of the builder
     */
    public static <T, B> void assertBean(final Class<T> classUnderTest, final Class<B> builderUnderTest,
                                         final String... exclusionProperties) {
        assertBuildObjectGetterBehavior(classUnderTest, builderUnderTest, exclusionProperties);
        try {
            final Class<?> declaringClass = retrieveEqualsMethodDeclaringClass(classUnderTest);
            if (classUnderTest.getSimpleName().equals(declaringClass.getSimpleName())) {
                assertEqualsHashCode(classUnderTest, builderUnderTest, BUILD_METHOD_NAME,
                                            convertExclusions(exclusionProperties));
            }
        } catch (NoSuchMethodException e) {
            fail("Should never be possible unless the equals class has been removed from Object");
        }
    }

    /**
     * Performs all the tests defined in this class on a Builder bean.
     * <p/>
     * In most situations this is the method to use.
     *
     * @param classUnderTest          the class to test
     * @param builderUnderTest        the builder that creates the classUnderTest
     * @param buildMethodName         the name of the method that builds the classUnderTest
     * @param excludedBuilderMethods  properties to exclude from the test in the builder
     * @param excludedClassProperties properties to exclude from the test in de class under test
     * @param <T>                     the type of the classUnderTest
     * @param <B>                     the type of the builder
     */
    public static <T, B> void assertBean(final Class<T> classUnderTest, final Class<B> builderUnderTest,
                                         final String buildMethodName, final List<String> excludedBuilderMethods,
                                         final List<String> excludedClassProperties) {
        assertBuildObjectGetterBehavior(classUnderTest, builderUnderTest, buildMethodName, excludedBuilderMethods,
                                               excludedClassProperties);
        try {
            final Class<?> declaringClass = retrieveEqualsMethodDeclaringClass(classUnderTest);
            if (classUnderTest.getSimpleName().equals(declaringClass.getSimpleName())) {
                assertEqualsHashCode(classUnderTest, builderUnderTest, buildMethodName,
                                            convertExclusions(excludedBuilderMethods));
            }
        } catch (NoSuchMethodException e) {
            fail("Should never be possible unless the equals class has been removed from Object");
        }
    }

    private static Class<?> retrieveEqualsMethodDeclaringClass(final Class<?> classUnderTest)
            throws NoSuchMethodException {
        return classUnderTest.getMethod(EQUALS_METHOD_NAME, Object.class).getDeclaringClass();
    }

    private static class OtherType {
    }
}
