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

    /**
     * Tests a bean created by a builder.
     *
     * @param classUnderTest         the class to test
     * @param builderUnderTest       the builder that creates the classUnderTest
     * @param excludedBuilderMethods propertyes to exclude from the test
     * @param <T>                    the type of the classUnderTest
     * @param <B>                    the type of the builder
     */
    public static <T, B> void assertBuildObjectGetterBehavior(final Class<T> classUnderTest,
                                                              final Class<B> builderUnderTest,
                                                              final String... excludedBuilderMethods) {

        final List<String> blacklist = new ArrayList<String>(Arrays.asList(excludedBuilderMethods));
        blacklist.add("class");

        try {
            final Constructor[] constructors = builderUnderTest.getDeclaredConstructors();
            if (constructors.length > 1) {
                fail("There should only be one constructor in a Builder class");
            }
            final Method buildMethod = builderUnderTest.getMethod("build");

            @SuppressWarnings({"unchecked"})
            final B builder = (B) Asserter.createObject(constructors[0]);

            final Method[] methods = builderUnderTest.getDeclaredMethods();
            assertFalse("There should be builder methods ", methods.length == 0);
            boolean hasBuilderMethods = false;
            for (final Method method : methods) {
                if (blacklist.contains(method.getName())) {
                    continue;
                }

                if (builderUnderTest.getSimpleName().equals(method.getReturnType().getSimpleName())) {
                    method.invoke(builder, createMethodParameterList(method));
                    hasBuilderMethods = true;
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
                if (blacklist.contains(descriptor.getDisplayName())) {
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

}
