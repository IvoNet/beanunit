package nl.ivonet.beanutils;

import org.junit.Test;

import simplepojo.Address;
import simplepojo.Person;
import simplepojo.SimpleBean;
import simplepojo.WrongSinpleBean;
import simplepojo.ZipCode;

import static nl.ivonet.beanutils.SimplePojoContractAsserter.assertBasicGetterSetterBehavior;
import static nl.ivonet.beanutils.SimplePojoContractAsserter.assertBasicGetterSetterBehaviorWithBlacklist;
import static nl.ivonet.beanutils.SimplePojoContractAsserter.assertEqualsHashCode;

/**
 * Unit tests for the {@link nl.ivonet.beanutils.SimplePojoContractAsserter} class
 *
 * @author Ivo Woltring
 */
public class SimplePojoContractAsserterTest {
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
        assertBasicGetterSetterBehaviorWithBlacklist(Person.class, "name", "birthDate");
        assertBasicGetterSetterBehaviorWithBlacklist(Address.class, "zip");
        assertBasicGetterSetterBehaviorWithBlacklist(SimpleBean.class, "times");
    }
}
