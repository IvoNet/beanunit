package nl.ivonet.beanutils;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
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

/**
 * Parent class for all Asserters.
 * This class implements all the plumbing code to make the work of the other {@link nl.ivonet.beanutils.Asserter}s
 * easier.
 *
 * @author Ivo Woltring
 */
public abstract class Asserter {
    private static final float FLOAT = 3.14159F;
    private static final double DOUBLE = 3.14159;
    private static final long LONG = 10L;
    static final int TEST_ARRAY_SIZE = 10;
    static final String JAVA_LANG_OBJECT = "java.lang.Object";
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
        TYPE_ARGUMENTS.put(Byte.class, (byte) 10);
        TYPE_ARGUMENTS.put(Byte.TYPE, (byte) 10);
        TYPE_ARGUMENTS.put(Short.class, (short) 10);
        TYPE_ARGUMENTS.put(String.class, "String");
        TYPE_ARGUMENTS.put(Short.TYPE, (short) 10);
        TYPE_ARGUMENTS.put(Integer.class, 10);
        TYPE_ARGUMENTS.put(Integer.TYPE, 10);
        TYPE_ARGUMENTS.put(Long.class, LONG);
        TYPE_ARGUMENTS.put(Long.TYPE, LONG);
        TYPE_ARGUMENTS.put(Float.class, FLOAT);
        TYPE_ARGUMENTS.put(Float.TYPE, FLOAT);
        TYPE_ARGUMENTS.put(Double.class, DOUBLE);
        TYPE_ARGUMENTS.put(Double.TYPE, DOUBLE);
        TYPE_ARGUMENTS.put(BigDecimal.class, new BigDecimal("3.14159"));
        TYPE_ARGUMENTS.put(java.sql.Date.class, new java.sql.Date(new Date().getTime()));
        TYPE_ARGUMENTS.put(Timestamp.class, new Timestamp(new Date().getTime()));
        TYPE_ARGUMENTS.put(Calendar.class, Calendar.getInstance());
    }

    static final Map<Class, Object> DEFAULT_TYPE_ARGUMENTS = Collections
                                                                     .unmodifiableMap(new HashMap<Class, Object>(TYPE_ARGUMENTS));

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

    private static Object invokeDefaultConstructorEvenIfPrivate(final Class type) {
        try {
            final Constructor ctor = type.getDeclaredConstructor();
            ctor.setAccessible(true);
            return ctor.newInstance();
        } catch (final Exception ex) {
            throw new RuntimeException("Could not invoke default constructor on type " + type, ex);
        }
    }

    /**
     * Registers the specified type that will default to the specified <code>defaultArgument</code> as the argument to
     * setter methods. Note this method will override any existing default arguments for a type.
     *
     * @param type            the type to register
     * @param defaultArgument the default argument to use in setters
     */
    public static void registerTypeAndDefaultArgument(final Class type, final Object defaultArgument) {
        TYPE_ARGUMENTS.put(type, defaultArgument);
    }

    /**
     * Removes the specified type, so that there wil no longer be a default argument for the type.
     *
     * @param type the type to deregister.
     */
    public static void deregisterType(final Class type) {
        TYPE_ARGUMENTS.remove(type);
    }

    /**
     * Resets the types and default arguments.
     */
    public static void resetToDefaultTypes() {
        TYPE_ARGUMENTS.clear();
        TYPE_ARGUMENTS.putAll(DEFAULT_TYPE_ARGUMENTS);
    }

    /**
     * Returns the default argument for the specified type.
     *
     * @param type the type
     * @return the type's default argument
     */
    public static Object defaultArgumentForType(final Class type) {
        return TYPE_ARGUMENTS.get(type);
    }

}
