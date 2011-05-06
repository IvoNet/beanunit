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

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Test;

import builder.AddressDto;
import builder.BuilderHasOtherNameBean;
import builder.BuilderMethodIsWrong;
import builder.BuilderWithConstructorBean;
import builder.BuilderWithMultipleConstructorBean;
import builder.EqualsMethodButNoHasCode;
import builder.NotImmutableBuilderBean;
import builder.SimpleBuilderBean;
import simplepojo.SimpleBean;

import static nl.ivonet.beanunit.BuilderBeanAsserter.assertBean;
import static nl.ivonet.beanunit.BuilderBeanAsserter.assertBuildObjectGetterBehavior;
import static nl.ivonet.beanunit.BuilderBeanAsserter.assertEqualsHashCode;

/**
 * Unit tests for the {@link nl.ivonet.beanunit.BuilderBeanAsserter} class.
 *
 * @author Ivo Woltring
 */
public class BuilderBeanAsserterTest {

    @After
    public void tearDown() throws Exception {
        BuilderBeanAsserter.resetToDefaultTypes();
    }

    //The following tests demonstrate the how to use beanunit.

    /**
     * All you have to do to test a default Builder Bean.
     */
    @Test
    public void testAssertBean() {
        assertBean(AddressDto.class);
    }

    /**
     * All you have to do to test a Builder bean where the builder has a different name ("Creator") and another
     * build method ("doit")
     * <p/>
     * The null, null parameters can be filled with exclusion properties.
     * The first null can be filled with builder property exclusions and the second is for exclusions of the
     * Bean to be build.
     */
    @Test
    public void testAssertBean2() {
        assertBean(BuilderHasOtherNameBean.class, BuilderHasOtherNameBean.Creator.class, "doit", null, null);
    }

    /**
     * All you have to do to test a bean where you want to provide the Builder separately.
     */
    @Test
    public void testAssertBean3() {
        assertBean(SimpleBuilderBean.class, SimpleBuilderBean.Builder.class);
    }

    /**
     * All you have to do if you want to test a Builder bean and exclude a property from testing.
     */
    @Test
    public void testAssertBean4() {
        assertBean(BuilderWithConstructorBean.class, BuilderWithConstructorBean.Builder.class, "list");
    }

    //Unit tests for the application itself.

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
    public void testSimpleBuilderOtherConvenienceMethod() throws Exception {
        assertBuildObjectGetterBehavior(SimpleBuilderBean.class, SimpleBuilderBean.Builder.class);
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

    @Test
    public void testEqualsHashcode() throws Exception {
        assertBuildObjectGetterBehavior(AddressDto.class);
        assertEqualsHashCode(AddressDto.class);
    }

    @Test(expected = AssertionError.class)
    public void testWrongBuilderMethod() throws Exception {
        assertBuildObjectGetterBehavior(BuilderMethodIsWrong.class);
    }

    @Test(expected = AssertionError.class)
    public void testEqualsNoHashCode() throws Exception {
        assertEqualsHashCode(EqualsMethodButNoHasCode.class);
    }

}
