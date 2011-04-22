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

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import static org.junit.Assert.fail;

/**
 * Parent class for all Asserters.
 * This class implements all the plumbing code to make the work of the other {@link nl.ivonet.beanunit.Asserter}s
 * easier.
 * <p/>
 * One can (de)register basic type arguments
 * <p/>
 * Note that if used in a test with the Before annotation to register new type it is good practice to de-register them
 * in the After annotation.
 *
 * @author Ivo Woltring
 */
public abstract class Asserter {
    private static final float FLOAT = 3.14159F;
    private static final double DOUBLE = 3.14159;
    private static final long LONG = 42L;
    static final int TEST_ARRAY_SIZE = 42;
    static final String JAVA_LANG_OBJECT = "java.lang.Object";
    static final String ALWAYS_EXCLUDED = "class";
    static final Map<Class, Object> TYPE_ARGUMENTS = new HashMap<Class, Object>();

    static {
        TYPE_ARGUMENTS.put(Collection.class, new ArrayList());
        TYPE_ARGUMENTS.put(List.class, new ArrayList());
        TYPE_ARGUMENTS.put(Set.class, new HashSet());
        TYPE_ARGUMENTS.put(SortedSet.class, new TreeSet());
        TYPE_ARGUMENTS.put(Map.class, new HashMap());
        TYPE_ARGUMENTS.put(SortedMap.class, new TreeMap());
        TYPE_ARGUMENTS.put(Boolean.class, true);
        TYPE_ARGUMENTS.put(Boolean.TYPE, true);
        TYPE_ARGUMENTS.put(Character.class, 'Z');
        TYPE_ARGUMENTS.put(Character.TYPE, 'Z');
        TYPE_ARGUMENTS.put(Byte.class, (byte) 42);
        TYPE_ARGUMENTS.put(Byte.TYPE, (byte) 42);
        TYPE_ARGUMENTS.put(Short.class, (short) 42);
        TYPE_ARGUMENTS.put(String.class, "String");
        TYPE_ARGUMENTS.put(Short.TYPE, (short) 42);
        TYPE_ARGUMENTS.put(Integer.class, 42);
        TYPE_ARGUMENTS.put(Integer.TYPE, 42);
        TYPE_ARGUMENTS.put(Long.class, LONG);
        TYPE_ARGUMENTS.put(Long.TYPE, LONG);
        TYPE_ARGUMENTS.put(Float.class, FLOAT);
        TYPE_ARGUMENTS.put(Float.TYPE, FLOAT);
        TYPE_ARGUMENTS.put(Double.class, DOUBLE);
        TYPE_ARGUMENTS.put(Double.TYPE, DOUBLE);
        TYPE_ARGUMENTS.put(BigDecimal.class, new BigDecimal("3.14159"));
        TYPE_ARGUMENTS.put(java.sql.Date.class, new java.sql.Date(new Date().getTime()));
        TYPE_ARGUMENTS.put(java.util.Date.class, new java.util.Date(new Date().getTime()));
        TYPE_ARGUMENTS.put(Timestamp.class, new Timestamp(new Date().getTime()));
        TYPE_ARGUMENTS.put(Calendar.class, Calendar.getInstance());
    }

    /**
     * The unmodifiable version of the TYPE_ARGUMENTS list used to reset all values when it is wanted.
     */
    static final Map<Class, Object> DEFAULT_TYPE_ARGUMENTS = Collections
                                                                     .unmodifiableMap(new HashMap<Class, Object>(TYPE_ARGUMENTS));

    /**
     * Retrieves the default value based on a Type.
     * The default values are listed in the TYPE_ARGUMENTS list.
     *
     * @param type the Type (Class) to get the default value for
     * @return an instance of the wanted type with a default value.
     */
    static Object retrieveDefaultValueByType(final Class type) {
        final Object arg;
        if (type.isArray()) {
            arg = Array.newInstance(type.getComponentType(), new int[]{TEST_ARRAY_SIZE});
        } else if (type.isEnum()) {
            arg = type.getEnumConstants()[0];
        } else if (TYPE_ARGUMENTS.containsKey(type)) {
            arg = TYPE_ARGUMENTS.get(type);
        } else {
            arg = invokeDefaultConstructorEvenIfPrivate(type);
        }
        return arg;
    }

    //Tries to invoke the default constructor even if this constructor is private.
    private static Object invokeDefaultConstructorEvenIfPrivate(final Class<?> type) {
        try {
            final Constructor<?> constructor = type.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (InstantiationException e) {
            fail(e.getMessage());
        } catch (IllegalAccessException e) {
            fail(e.getMessage());
        } catch (InvocationTargetException e) {
            fail(e.getMessage());
        } catch (NoSuchMethodException e) {
            fail(e.getMessage());
        }
        return null;
    }

    /**
     * Registers the specified type that will default to the specified <code>defaultArgument</code> as the argument to
     * setter methods. Note this method will override any existing default arguments for a type.
     *
     * @param type            the type to register
     * @param defaultArgument the default argument to use in setters
     */
    public static void registerTypeAndDefaultArgument(final Class<?> type, final Object defaultArgument) {
        TYPE_ARGUMENTS.put(type, defaultArgument);
    }

    /**
     * Removes the specified type, so that there wil no longer be a default argument for the type.
     *
     * @param type the type to deregister.
     */
    public static void deregisterType(final Class<?> type) {
        TYPE_ARGUMENTS.remove(type);
    }

    /**
     * Resets the types and default arguments to the original list.
     */
    public static void resetToDefaultTypes() {
        TYPE_ARGUMENTS.clear();
        TYPE_ARGUMENTS.putAll(DEFAULT_TYPE_ARGUMENTS);
    }

    /**
     * Creates the list of parameters based on default values for a provided constructor.
     *
     * @param constructor the constructor to create the parameter list for
     * @return array with the constructor parameters
     */
    static Object[] createConstructorParameterList(final Constructor<?> constructor) {
        final List arguments = new ArrayList();
        final Class<?>[] parameterTypes = constructor.getParameterTypes();
        for (final Class<?> parameterType : parameterTypes) {
            //noinspection unchecked
            arguments.add(retrieveDefaultValueByType(parameterType));
        }
        return arguments.toArray();
    }

    /**
     * Creates the list of parameters for the method based on the default values provided by this class.
     *
     * @param method the method to get the parameters for
     * @return object array containing the default values for the parameters
     */
    static Object[] createMethodParameterList(final Method method) {
        final List<Object> arguments = new ArrayList<Object>();
        final Class<?>[] parameterTypes = method.getParameterTypes();
        for (final Class<?> parameterType : parameterTypes) {
            arguments.add(retrieveDefaultValueByType(parameterType));
        }
        return arguments.toArray();
    }

    static Object createObject(final Constructor<?> constructor, final Object[] arguments) {
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
        fail("No object created.");
        return null;
    }

    /**
     * Creates the object belonging to the constructor based on default values.
     *
     * @param constructor the constructor to invoke
     * @return object belonging to the constructor.
     */
    static Object createObject(final Constructor<?> constructor) {
        return createObject(constructor, createConstructorParameterList(constructor));
    }

}
