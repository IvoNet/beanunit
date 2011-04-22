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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Test utility for testing beans that are immutable after construction.
 * Complete construction must be done by a constructor for the tests in this class to work.
 * <p/>
 * Construction of the immutable object is done by reflection and providing default types with values.
 * <p/>
 * This class tries to assert that behavior of the bean after construction is valid.
 * There should be no write methods in the class under test.
 * All attributes should have read methods.
 * All read methods (getters) should return the default value provided for the return type.
 * <p/>
 * Attributes can be excluded by providing their string representation as a parameter to the method.
 *
 * @author Ivo Woltring
 */
public class ConstructorImmutableBeanAsserter extends Asserter {

    private ConstructorImmutableBeanAsserter() {
        //All static so don't create
    }

    /**
     * Asserts that the Getters return the default value for the return type of the getter.
     * Asserts that the object can be created by providing default values for the wanted types to the constructor.
     * Asserts that the object does not have write methods (= immutable) for the member variables.
     * Tries to do all this for all available constructors.
     *
     * @param classUnderTest     the {@link Class} to test
     * @param excludedProperties property to exclude from testing if some of the rules are not nicely upheld :-)
     * @param <T>                the type of the class under test.
     */
    public static <T> void assertGettersOnConstructorImmutableObject(final Class<T> classUnderTest,
                                                                     final String... excludedProperties) {
        final List<String> blacklist = new ArrayList<String>(Arrays.asList(excludedProperties));
        blacklist.add("class");

        try {
            final Constructor[] constructors = classUnderTest.getDeclaredConstructors();
            for (final Constructor constructor : constructors) {
                @SuppressWarnings({"unchecked"}) final T object = (T) createObject(constructor);

                assertNotNull("Could not create the object", object);

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
                    assertEquals(arg, readMethod.invoke(object));
                }
            }

        } catch (InvocationTargetException e) {
            fail(e.getMessage());
        } catch (IntrospectionException e) {
            fail(e.getMessage());
        } catch (IllegalAccessException e) {
            fail(e.getMessage());
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
     * @param classUnderTest the implementation.class
     * @param <T>            the type of the class to test
     */
    public static <T> void assertEqualsHashCode(final Class<T> classUnderTest) {
        try {

            final Constructor[] constructors = classUnderTest.getDeclaredConstructors();
            for (final Constructor constructor : constructors) {
                @SuppressWarnings({"unchecked"}) final T one = (T) createObject(constructor);
                @SuppressWarnings({"unchecked"}) final T two = (T) createObject(constructor);

                final Class<?> equalsDeclaringClass = classUnderTest.getMethod("equals", Object.class)
                                                              .getDeclaringClass();
                final Class<?> hashCodeDeclaringClass = classUnderTest.getMethod("hashCode").getDeclaringClass();
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
            }

        } catch (NoSuchMethodException e) {
            fail("There is no equals or hashCode method. which is weird :-)");
        }
    }

    private static boolean doesNotOverrideObjectMethod(final Class<?> clazz) {
        return clazz.getName().equals(JAVA_LANG_OBJECT);
    }

    private static class OtherType {
    }
}
