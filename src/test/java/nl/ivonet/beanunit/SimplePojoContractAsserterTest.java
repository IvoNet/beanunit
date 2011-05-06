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

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import simplepojo.Address;
import simplepojo.Person;
import simplepojo.PrivateConstructor;
import simplepojo.SimpleBean;
import simplepojo.WrongSinpleBean;
import simplepojo.ZipCode;

import static nl.ivonet.beanunit.SimplePojoContractAsserter.assertBasicGetterSetterBehavior;
import static nl.ivonet.beanunit.SimplePojoContractAsserter.assertBean;
import static nl.ivonet.beanunit.SimplePojoContractAsserter.assertEqualsHashCode;
import static nl.ivonet.beanunit.SimplePojoContractAsserter.createObject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for the {@link nl.ivonet.beanunit.SimplePojoContractAsserter} class
 *
 * @author Ivo Woltring
 */
public class SimplePojoContractAsserterTest {

    //The following tests demonstrate the how to use beanunit

    @Test
    public void testAssertPojo() throws Exception {
        assertBean(Person.class);
    }

    /**
     * All you have to declare to test a simple pojo almost completely.
     */
    @Test
    public void testPojoNoEquals() {
        assertBean(ZipCode.class);
    }

    /**
     * All you have to declare to test a pojo and exclude a property from the test.
     */
    @Test
    public void testPojoExcludePropertie() {
        assertBean(Person.class, "name");
    }

    @Test
    public void tesCreateObject() throws Exception {
        final SimpleBean bean = createObject(SimpleBean.class);
        assertNotNull(bean);
        assertEquals("SimpleBean{hello='String', times=42, trueOrFalse=true}", bean.toString());
    }

    //Tests below are to test the code itself not to demonstrate the usage

    @Test(expected = AssertionError.class)
    public void testAssertEqualsHashCodeNotOverridden() throws Exception {
        assertEqualsHashCode(ZipCode.class);
    }

    @Test
    public void testAssertEqualsHashCodeOverridden() throws Exception {
        assertEqualsHashCode(SimpleBean.class);
    }

    @Test(expected = AssertionError.class)
    public void testEqualsAndHashCodeNotOverriddenAtSameLevel() throws Exception {
        assertEqualsHashCode(Address.class);
    }

    @Test(expected = AssertionError.class)
    public void testAssertEqualsHashCodeWrongGetterAndSetterEquals() throws Exception {
        assertEqualsHashCode(WrongSinpleBean.class);
    }

    @Test
    public void testProperties() throws Exception {
        assertBasicGetterSetterBehavior(Person.class);
        assertBasicGetterSetterBehavior(Address.class);
        assertBasicGetterSetterBehavior(SimpleBean.class);
        assertBasicGetterSetterBehavior(ZipCode.class);
    }

    @Test(expected = AssertionError.class)
    public void testWrongBeanProperties() throws Exception {
        assertBasicGetterSetterBehavior(WrongSinpleBean.class);
    }

    @Test
    public void testSpecificProperties() throws Exception {
        assertBasicGetterSetterBehavior(Person.class, "name", "birthDate");
        assertBasicGetterSetterBehavior(Address.class, "zip");
        assertBasicGetterSetterBehavior(SimpleBean.class, "times");
    }

    @Test
    public void testPropertiesBlackList() throws Exception {
        assertBasicGetterSetterBehavior(Person.class, "name", "birthDate");
        assertBasicGetterSetterBehavior(Address.class, "zip");
        assertBasicGetterSetterBehavior(SimpleBean.class, "times");
    }

    @Test
    public void testOtherThanDefaultProperties() throws Exception {
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("name", "Piet");
        properties.put("birthDate", Date.valueOf("2011-04-11"));
        assertBasicGetterSetterBehavior(Person.class, properties);

    }

    @Test(expected = AssertionError.class)
    public void testPrivateConstructor() throws Exception {
        assertBasicGetterSetterBehavior(PrivateConstructor.class);
    }

}
