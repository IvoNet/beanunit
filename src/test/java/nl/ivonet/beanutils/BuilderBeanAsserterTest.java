package nl.ivonet.beanutils;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import builder.BuilderWithConstructorBean;
import builder.BuilderWithMultipleConstructorBean;
import builder.NotImmutableBuilderBean;
import builder.SimpleBuilderBean;

import static nl.ivonet.beanutils.BuilderBeanAsserter.assertBuildObjectGetterBehavior;

/**
 * Unit tests for the {@link nl.ivonet.beanutils.BuilderBeanAsserter} class.
 *
 * @author Ivo Woltring
 */
public class BuilderBeanAsserterTest {

    @After
    public void tearDown() throws Exception {
        BuilderBeanAsserter.resetToDefaultTypes();
    }

    @SuppressWarnings({"unchecked"})
    @Test
    public void testAssertBuildObjectGetterBehavior() throws Exception {
        BuilderBeanAsserter.registerTypeAndDefaultArgument(List.class, new ArrayList(1) {{add("String");}});
        assertBuildObjectGetterBehavior(BuilderWithConstructorBean.class, BuilderWithConstructorBean.Builder.class);
    }

    @Test
    public void testAssertBuildObjectGetterBehaviorBlacklist() throws Exception {
        assertBuildObjectGetterBehavior(BuilderWithConstructorBean.class, BuilderWithConstructorBean.Builder.class,
                                               "list");
    }

    @Test(expected = AssertionError.class)
    public void testAssertBuildOnNutImmutableBuilderBean() throws Exception {
        assertBuildObjectGetterBehavior(NotImmutableBuilderBean.class, NotImmutableBuilderBean.Builder.class);
    }

    @Test
    public void testSimpleBuilder() throws Exception {
        assertBuildObjectGetterBehavior(SimpleBuilderBean.class, SimpleBuilderBean.Builder.class);
    }

    @Test(expected = AssertionError.class)
    public void testMultipleConstructorsBuilder() throws Exception {
        assertBuildObjectGetterBehavior(BuilderWithMultipleConstructorBean.class,
                                               BuilderWithMultipleConstructorBean.Builder.class);
    }
}
