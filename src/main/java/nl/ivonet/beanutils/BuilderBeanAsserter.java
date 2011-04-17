package nl.ivonet.beanutils;

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
