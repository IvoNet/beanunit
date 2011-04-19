package nl.ivonet.beanutils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import immutable.AddressDto;
import immutable.BusinessLocationDto;
import immutable.InventoryDto;
import immutable.NotImmutableBuildingDto;

/**
 * Unit tests for the {@link SimplePojoContractAsserter} class.
 *
 * @author Ivo Woltring
 */
public class ConstructorImmutableBeanAsserterTest {
    @Before
    public void setUp() throws Exception {
        SimplePojoContractAsserter
                .registerTypeAndDefaultArgument(AddressDto.class, new AddressDto.Builder().setCity("c").build());

    }

    @After
    public void tearDown() throws Exception {
        SimplePojoContractAsserter.resetToDefaultTypes();
    }

    @Test(expected = AssertionError.class)
    public void testImmutableObjectThatIsNotImmutable() {
        ConstructorImmutableBeanAsserter.assertGettersOnConstructorImmutableObject(NotImmutableBuildingDto.class);
    }

    @Test
    public void testImmutableDto() throws Exception {
        ConstructorImmutableBeanAsserter.assertGettersOnConstructorImmutableObject(BusinessLocationDto.class);
    }

    @Test
    public void testExcludePropertiesFromTest() throws Exception {
        ConstructorImmutableBeanAsserter.assertGettersOnConstructorImmutableObject(InventoryDto.class, "insuredAmount");
    }

    @Test
    public void testExcludePropertiesFromTestNoExclusions() throws Exception {
        ConstructorImmutableBeanAsserter.assertGettersOnConstructorImmutableObject(InventoryDto.class, "insuredAmount");
    }
}
