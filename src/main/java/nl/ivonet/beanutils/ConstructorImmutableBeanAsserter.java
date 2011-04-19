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

}
