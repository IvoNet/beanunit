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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test utility class that makes easy work of testing default behavior pojos (beans).
 * <p/>
 * The assertBasicGetterSetterBehavior methods will try to assert that the normal Bean (POJO)
 * specifications and rules are upheld.
 * <p/>
 * Getters should exactly return what setters set.
 * Write methods should have read methods and vice versa.
 * <p/>
 * The assertEqualsHashCode method will try to assert that the rules around these methods are upheld.
 * <p/>
 * If equals has been overridden the hashCode method must also be overridden and vise versa.
 * If an object is equal the hashcode must also be the same for both objects, etc.
 *
 * @author Ivo Woltring
 */
public final class SimplePojoContractAsserter extends Asserter {

    private SimplePojoContractAsserter() {
        //All static so don't create
    }

    /**
     * Tests that the getter and setter methods for <code>property</code> work in a basic fashion, which is that the
     * getter returns the exact same object as set by the setter method.
     * FindBugz says this is not correct but I don't see the bad in it :-)
     * <p/>
     * Uses a default argument for basic collection types, primitive types, Dates, java.sql.Dates, and Timestamps.
     * See {@link SimplePojoContractAsserter#TYPE_ARGUMENTS}.
     *
     * @param classUnderTest the object on which to invoke the getter and setter
     * @param property       the property name, e.g. "firstName"
     */
    protected static <T> void assertBasicGetterSetterBehavior(final Class<T> classUnderTest, final String property) {
        assertBasicGetterSetterBehavior(classUnderTest, property, null);
    }

    /**
     * The method that does the actual assertion work.
     * <p/>
     * It checks the getter and setter behavior of the property against the argument.
     *
     * @param classUnderTest the object on which to invoke the getter and setter
     * @param property       the property name, e.g. "firstName"
     * @param argument       the property value, i.e. the value the setter will be invoked with
     */
    protected static <T> void assertBasicGetterSetterBehavior(final Class<T> classUnderTest, final String property,
                                                              final Object argument) {
        try {
            final T testObject = classUnderTest.newInstance();
            final PropertyDescriptor descriptor = new PropertyDescriptor(property, classUnderTest);
            Object arg = argument;
            final Class type = descriptor.getPropertyType();
            if (arg == null) {
                arg = retrieveDefaultValueByType(type);
            }

            final Method writeMethod = descriptor.getWriteMethod();
            final Method readMethod = descriptor.getReadMethod();

            writeMethod.invoke(testObject, arg);
            final Object propertyValue = readMethod.invoke(testObject);
            if (type.isPrimitive()) {
                assertEquals(property + " getter/setter failed test", arg, propertyValue);
            } else {
                assertSame(property + " getter/setter failed test", arg, propertyValue);
            }
        } catch (final IntrospectionException e) {
            fail("Error creating PropertyDescriptor for property [" + property
                 + "]. Do you have a getter and a setter?");
        } catch (final IllegalAccessException e) {
            fail("Error accessing property. Are the getter and setter both accessible?");
        } catch (final InvocationTargetException e) {
            fail("Error invoking method on target");
        } catch (InstantiationException e) {
            fail("Error instantiating target");
        }
    }

    /**
     * @param classUnderTest the object on which to invoke the getter and setter
     * @param properties     map of property names to argument values
     * @see {@link SimplePojoContractAsserter#assertBasicGetterSetterBehavior(Class, String, Object)} method. Only difference is that here we accept a map
     *      containing property name/value pairs. Use this to test a bunch of property accessors at once. Note that the
     *      values in the map can be null, and in that case we'll try to supply a default argument.
     */
    protected static <T> void assertBasicGetterSetterBehavior(final Class<T> classUnderTest,
                                                              final Map<String, Object> properties) {
        final Set<Map.Entry<String, Object>> entries = properties.entrySet();
        for (final Map.Entry<String, Object> entry : entries) {
            assertBasicGetterSetterBehavior(classUnderTest, entry.getKey(), entry.getValue());
        }
    }

    /**
     * @param classUnderTest     the object on which to invoke the getter and setter
     * @param excludedProperties the list of property names that should not be tested
     * @see {@link SimplePojoContractAsserter#assertBasicGetterSetterBehavior(Class, String, Object)} method. Big difference here is that we try to
     *      automatically introspect the target object, finding read/write properties, and automatically testing the getter
     *      and setter. Note specifically that read-only properties are ignored, as there is no way for us to know how to set
     *      the value (since there isn't a public setter).
     *      <p/>
     *      Any property names contained in the blacklist will be skipped.
     *      <p/>
     */
    protected static <T> void assertBasicGetterSetterBehavior(final Class<T> classUnderTest,
                                                              final String... excludedProperties) {
        final List<String> blacklist = Arrays.asList(excludedProperties);
        try {
            final BeanInfo beanInfo = Introspector.getBeanInfo(classUnderTest);
            final PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            for (final PropertyDescriptor descriptor : descriptors) {
                if (descriptor.getWriteMethod() == null) {
                    continue;
                }
                if (!blacklist.contains(descriptor.getDisplayName())) {
                    assertBasicGetterSetterBehavior(classUnderTest, descriptor.getDisplayName());
                }
            }
        } catch (final IntrospectionException e) {
            fail(String.format("Failed while introspecting [%s].", classUnderTest));
        }
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
     * @param excludedProperties string representation of all the properties excluded from the equals test , e.g. "firstName"
     * @param <T>                the type of the class to test
     */
    protected static <T> void assertEqualsHashCode(final Class<T> classUnderTest, final String... excludedProperties) {
        final List<String> blacklist = new ArrayList<String>(Arrays.asList(excludedProperties));
        blacklist.add(ALWAYS_EXCLUDED);
        try {
            final T one = classUnderTest.newInstance();
            final T two = classUnderTest.newInstance();

            final Class<?> equalsDeclaringClass = retrieveEqualsMethodDeclaringClass(classUnderTest);
            final Class<?> hashCodeDeclaringClass = classUnderTest.getMethod(HASH_CODE_METHOD_NAME).getDeclaringClass();
            if (equalsDeclaringClass.getName().equals(JAVA_LANG_OBJECT) && hashCodeDeclaringClass.getName()
                                                                                   .equals(JAVA_LANG_OBJECT)) {
                fail(String.format("The Class<%s> under test does not override the equals and hashCode method",
                                          classUnderTest));

            }
            if (!hashCodeDeclaringClass.getName().equals(equalsDeclaringClass.getName())) {
                fail(String.format("The equals and hashCode methods of Class<%s> have different declaring classes.",
                                          classUnderTest));
            }

            assertTrue("Instances with default constructor not equal (o1.equals(o2))", one.equals(two));
            assertTrue("Instances with default constructor not equal (o1.equals(o1))", one.equals(one));
            assertTrue("Instances with default constructor not equal (o2.equals(o1))", two.equals(one));
            assertFalse("Equaling different types of object should not be equal", one.equals(new OtherType()));
            //noinspection ObjectEqualsNull
            assertFalse("Equaling null type should not be equal", one.equals(null));

            final BeanInfo beanInfo = Introspector.getBeanInfo(classUnderTest);
            final PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            for (final PropertyDescriptor descriptor : descriptors) {
                if (descriptor.getWriteMethod() == null) {
                    continue;
                }
                if (blacklist.contains(descriptor.getDisplayName())) {
                    continue;
                }

                final Object arg;
                final Class type = descriptor.getPropertyType();
                arg = retrieveDefaultValueByType(type);

                final Method writeMethod = descriptor.getWriteMethod();
                writeMethod.invoke(one, arg);

                assertFalse("Instances with o1 having " + descriptor.getName()
                            + " set and o2 having it not set are equal", one.equals(two));

                writeMethod.invoke(two, arg);

                assertTrue("Instances of " + classUnderTest.getSimpleName() + " with method " + writeMethod.getName()
                           + "() set and second instance having it set to the same object are not equal",
                                  one.equals(two));

                assertTrue("Instances of " + classUnderTest.getSimpleName() + " with method " + writeMethod.getName()
                           + "() set and second instance having it set to the same object have different hashCode",
                                  one.hashCode() == two.hashCode());

                if (!type.isPrimitive()) {
                    final Object a = null;
                    writeMethod.invoke(one, a);

                    assertFalse("Instances with o1 having " + descriptor.getName()
                                + " set to null and o2 having it not set are equal", one.equals(two));

                    writeMethod.invoke(two, a);

                    assertTrue("Instances of " + classUnderTest.getSimpleName() + " with method "
                               + writeMethod.getName()
                               + "() set to null and second instance having it set tonull are not equal",
                                      one.equals(two));

                    assertTrue("Instances of " + classUnderTest.getSimpleName() + " with method "
                               + writeMethod.getName()
                               + "() set to null and second instance having it set to null have different hashCode",
                                      one.hashCode() == two.hashCode());
                }
            }
        } catch (InstantiationException e) {
            fail(e.getMessage());
        } catch (IllegalAccessException e) {
            fail(e.getMessage());
        } catch (IntrospectionException e) {
            fail(e.getMessage());
        } catch (NoSuchMethodException e) {
            fail(e.getMessage());
        } catch (InvocationTargetException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Tests the complete pojo in one complete run.
     * <p/>
     * In most cases the method to use. Tests everything on the pojo.
     *
     * @param classUnderTest     the implementation.class
     * @param excludedProperties string representation of all the properties excluded from the equals test , e.g. "firstName"
     * @param <T>                the type of the class to test
     */
    public static <T> void assertBean(final Class<T> classUnderTest, final String... excludedProperties) {

        assertBasicGetterSetterBehavior(classUnderTest, excludedProperties);

        try {
            final Class<?> declaringClass = retrieveEqualsMethodDeclaringClass(classUnderTest);
            if (classUnderTest.getSimpleName().equals(declaringClass.getSimpleName())) {
                assertEqualsHashCode(classUnderTest, excludedProperties);
            }
        } catch (NoSuchMethodException e) {
            fail("Should never be possible unless the equals class has been removed from Object");
        }
    }

    private static Class<?> retrieveEqualsMethodDeclaringClass(final Class<?> classUnderTest)
            throws NoSuchMethodException {
        return classUnderTest.getMethod(EQUALS_METHOD_NAME, Object.class).getDeclaringClass();
    }

}
