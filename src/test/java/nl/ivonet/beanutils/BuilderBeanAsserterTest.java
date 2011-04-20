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

package nl.ivonet.beanutils;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import builder.BuilderHasOtherNameBean;
import builder.BuilderWithConstructorBean;
import builder.BuilderWithMultipleConstructorBean;
import builder.NotImmutableBuilderBean;
import builder.SimpleBuilderBean;
import simplepojo.SimpleBean;

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
        assertBuildObjectGetterBehavior(BuilderWithConstructorBean.class);
    }

    @Test
    public void testAssertBuildObjectGetterBehaviorBlacklist() throws Exception {
        assertBuildObjectGetterBehavior(BuilderWithConstructorBean.class, BuilderWithConstructorBean.Builder.class,
                                               "list");
    }

    @Test
    public void testBlacklistWithTheRestDefault() throws Exception {
        assertBuildObjectGetterBehavior(BuilderWithConstructorBean.class, "list");
    }

    @Test
    public void testBlacklistOnBuilderWithTheRestDefault() throws Exception {
        assertBuildObjectGetterBehavior(BuilderWithConstructorBean.class, new ArrayList<String>(1) {{add("add");}});
    }

    @Test(expected = AssertionError.class)
    public void testAssertBuildOnNutImmutableBuilderBean() throws Exception {
        assertBuildObjectGetterBehavior(NotImmutableBuilderBean.class);
    }

    @Test
    public void testSimpleBuilder() throws Exception {
        assertBuildObjectGetterBehavior(SimpleBuilderBean.class);
    }

    @Test
    public void testSimpleBuilderDefaultBuilder() throws Exception {
        assertBuildObjectGetterBehavior(SimpleBuilderBean.class);
    }

    @Test(expected = AssertionError.class)
    public void testNotABuilder() throws Exception {
        assertBuildObjectGetterBehavior(SimpleBean.class);
    }

    @Test(expected = AssertionError.class)
    public void testBuilderHasOtherName() throws Exception {
        assertBuildObjectGetterBehavior(BuilderHasOtherNameBean.class);
    }

    @Test
    public void testBuilderHasOtherNameAndProvided() throws Exception {
        assertBuildObjectGetterBehavior(BuilderHasOtherNameBean.class, BuilderHasOtherNameBean.Creator.class, "doit",
                                               null, null);
    }

    @Test(expected = AssertionError.class)
    public void testMultipleConstructorsBuilder() throws Exception {
        assertBuildObjectGetterBehavior(BuilderWithMultipleConstructorBean.class);
    }
}
