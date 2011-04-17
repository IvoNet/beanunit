package nl.ivonet.beanutils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
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
 */
@SuppressWarnings({"unchecked"})
public final class SimplePojoContractAsserter extends Asserter {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
                                                               .getLogger(SimplePojoContractAsserter.class);

    private SimplePojoContractAsserter() {
        //All static so don't create
    }

    /**
     * Tests that the getter and setter methods for <code>property</code> work in a basic fashion, which is that the
     * getter returns the exact same object as set by the setter method. (And we don't care that FindBugz says this is
     * bad, bad, bad and furthermore we disable that check in FindBugz anyway based on the Reduction of Java
     * Overengineering Act. Then again, some might argue that <i>this</i> class itself embodies Java Overengineering!)
     * <p/> Uses a default argument for basic collection types, primitive types, Dates, java.sql.Dates, and Timestamps.
     * See {@link SimplePojoContractAsserter#TYPE_ARGUMENTS}.
     *
     * @param target   the object on which to invoke the getter and setter
     * @param property the property name, e.g. "firstName"
     */
    public static <T> void assertBasicGetterSetterBehavior(final Class<T> target, final String property) {
        assertBasicGetterSetterBehavior(target, property, null);
    }

    /**
     * See {@link #assertBasicGetterSetterBehavior(Class, String...)} method. Only difference is that here we accept an
     * explicit argument for the setter method.
     *
     * @param classUnderTest the object on which to invoke the getter and setter
     * @param property       the property name, e.g. "firstName"
     * @param argument       the property value, i.e. the value the setter will be invoked with
     */
    public static <T> void assertBasicGetterSetterBehavior(final Class<T> classUnderTest, final String property,
                                                           final Object argument) {
        try {
            final T object = classUnderTest.newInstance();
            final PropertyDescriptor descriptor = new PropertyDescriptor(property, classUnderTest);
            Object arg = argument;
            final Class type = descriptor.getPropertyType();
            if (arg == null) {
                arg = retrieveDefaultValueByType(type);
            }

            final Method writeMethod = descriptor.getWriteMethod();
            final Method readMethod = descriptor.getReadMethod();

            writeMethod.invoke(object, arg);
            final Object propertyValue = readMethod.invoke(object);
            if (type.isPrimitive()) {
                assertEquals(property + " getter/setter failed test", arg, propertyValue);
            } else {
                assertSame(property + " getter/setter failed test", arg, propertyValue);
            }
        } catch (final IntrospectionException e) {
            final String msg = "Error creating PropertyDescriptor for property [" + property
                               + "]. Do you have a getter and a setter?";
            LOG.error(msg, e);
            fail(msg);
        } catch (final IllegalAccessException e) {
            final String msg = "Error accessing property. Are the getter and setter both accessible?";
            LOG.error(msg, e);
            fail(msg);
        } catch (final InvocationTargetException e) {
            final String msg = "Error invoking method on target";
            LOG.error(msg, e);
            fail(msg);
        } catch (InstantiationException e) {
            final String msg = "Error instantiating target";
            LOG.error(msg, e);
            fail(msg);
        }
    }

    /**
     * See {@link #assertBasicGetterSetterBehavior(Class, String...)} method. Only difference is that here we accept a map
     * containing property name/value pairs. Use this to test a bunch of property accessors at once. Note that the
     * values in the map can be null, and in that case we'll try to supply a default argument.
     *
     * @param target     the object on which to invoke the getter and setter
     * @param properties map of property names to argument values
     */
    public static <T> void assertBasicGetterSetterBehavior(final Class<T> target,
                                                           final Map<String, Object> properties) {
        final Set<Map.Entry<String, Object>> entries = properties.entrySet();
        for (final Map.Entry<String, Object> entry : entries) {
            assertBasicGetterSetterBehavior(target, entry.getKey(), entry.getValue());
        }
    }

    /**
     * See {@link #assertBasicGetterSetterBehavior(Class, String...)} method. Only difference is that here we accept an
     * array of property names. Use this to test a bunch of property accessors at once, using default arguments.
     *
     * @param target        the object on which to invoke the getter and setter
     * @param propertyNames the names of the propertyes you want to test
     */
    public static <T> void assertBasicGetterSetterBehavior(final Class<T> target, final String... propertyNames) {
        final Map<String, Object> properties = new LinkedHashMap<String, Object>();
        for (final String propertyName : propertyNames) {
            properties.put(propertyName, null);
        }
        assertBasicGetterSetterBehavior(target, properties);
    }

    /**
     * See {@link #assertBasicGetterSetterBehavior(Class, String...)} method. No items are blacklisted.
     *
     * @param target the object on which to invoke the getter and setter
     */
    public static <T> void assertBasicGetterSetterBehavior(final Class<T> target) {
        assertBasicGetterSetterBehaviorWithBlacklist(target);
    }

    /**
     * See {@link #assertBasicGetterSetterBehavior(Class, String...)} method. Big difference here is that we try to
     * automatically introspect the target object, finding read/write properties, and automatically testing the getter
     * and setter. Note specifically that read-only properties are ignored, as there is no way for us to know how to set
     * the value (since there isn't a public setter).
     * <p/>
     * Any property names contained in the blacklist will be skipped.
     * <p/>
     *
     * @param classUnderTest the object on which to invoke the getter and setter
     * @param propertyNames  the list of property names that should not be tested
     */
    public static <T> void assertBasicGetterSetterBehaviorWithBlacklist(final Class<T> classUnderTest,
                                                                        final String... propertyNames) {
        final List<String> blacklist = Arrays.asList(propertyNames);
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
    public static <T> void assertEqualsHashCode(final Class<T> classUnderTest, final String... excludedProperties) {
        final List<String> blacklist = new ArrayList(Arrays.asList(excludedProperties));
        blacklist.add("class");
        try {
            final T one = classUnderTest.newInstance();
            final T two = classUnderTest.newInstance();

            final Class<?> equalsDeclaringClass = classUnderTest.getMethod("equals", Object.class).getDeclaringClass();
            final Class<?> hashCodeDeclaringClass = classUnderTest.getMethod("hashCode").getDeclaringClass();
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
            fail("Could not instantiate the class. Maybe no default constructor.");
        } catch (IllegalAccessException e) {
            fail("IllegalAccessException.");
        } catch (IntrospectionException e) {
            fail("IntrospectionException");
        } catch (NoSuchMethodException e) {
            fail("There is no equals or hashCode method ");
        } catch (InvocationTargetException e) {
            fail("Could not invoke");
        }
    }

    private static class OtherType {
    }
}
