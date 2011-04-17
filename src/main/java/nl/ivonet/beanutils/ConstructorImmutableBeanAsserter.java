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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Test utility for testing beans that are immutable after construction.
 * Construction must be done by a constructor for the tests in this class to work.
 *
 * @author Ivo Woltring
 */
public class ConstructorImmutableBeanAsserter extends Asserter {
    private ConstructorImmutableBeanAsserter() {
    }

    public static <T> void assertGettersOnConstructorImmutableObject(final Class<T> classUnderTest,
                                                                     final String... excludedProperties) {
        final List<String> blacklist = new ArrayList<String>(Arrays.asList(excludedProperties));
        blacklist.add("class");

        try {
            final Constructor[] constructors = classUnderTest.getDeclaredConstructors();
            for (final Constructor constructor : constructors) {
                final List arguments = new ArrayList();
                final Class[] parameterTypes = constructor.getParameterTypes();
                for (final Class parameterType : parameterTypes) {
                    //noinspection unchecked
                    arguments.add(retrieveDefaultValueByType(parameterType));
                }
                final Object object = createObject(constructor, arguments.toArray());

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

    private static Object createObject(final Constructor constructor, final Object[] arguments) {
        try {
            return constructor.newInstance(arguments);
        } catch (InstantiationException e) {
            fail(e.getMessage());
        } catch (IllegalAccessException e) {
            fail(e.getMessage());
        } catch (IllegalArgumentException e) {
            fail(e.getMessage());
        } catch (InvocationTargetException e) {
            fail(e.getMessage());
        }
        fail("No object created");
        return null;
    }

    //TODO Finish the code below
//    public static <T, B> void assertEqualsHashCode(final Class<T> classUnderTest, final Class<B> builderUnderTest,
//                                                   final String... excludedProperties) {
//
//        try {
//            final B builder = builderUnderTest.newInstance();
//            final Method build = builderUnderTest.getMethod("build");
//
//            final T one = (T) build.invoke(builder);
//            //test
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//
//    }

    //TODO finish the code below
//    public static <T> void assertEqualsHashCode(final Class<T> classUnderTest, final String... excludedProperties) {
//        final List<String> blacklist = Arrays.asList(excludedProperties);
//
//        final Class[] nested = classUnderTest.getDeclaredClasses();
//        for (final Class aClass : nested) {
//            if (Modifier.isStatic(aClass.getModifiers())) {
//                if (aClass.getName().endsWith(BUILDER)) {
//
//                    System.out.println(String.format("Builder found in class [%s] named [%s]", classUnderTest.getName(),
//                                                            aClass.getName()));
//
//                    try {
//                        final Method build = aClass.getMethod("build");
//
//                        build.invoke(aClass);
//                    } catch (NoSuchMethodException e) {
//                        LOG.error("No build method found in the static inner class.");
//                    } catch (InvocationTargetException e) {
//                        LOG.error("Could not invoke the build method of the Builder");
//                    } catch (IllegalAccessException e) {
//                        LOG.error("Illegal Access Exception");
//                    }
//                    break;
//                }
//            }
//        }
//    }

}
