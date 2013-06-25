package logic.evaluable.statements.binary;

import logic.Connective;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class BinaryConnectiveFactoryTest {
	private static BinaryConnectiveFactory factory;

	@BeforeClass
	public static void setUp() {
		factory = new BinaryConnectiveFactory();
	}

	@Test
	public void testCreateElements() throws Exception {
		Connective expected;
		Connective actual;

		expected = new BinaryConnective(BinaryConnective.BinaryConnectiveType.OR);
		actual = factory.createElement("∨");
		assertEquals("Expect created connective to be equal to the factory-built one", expected, actual);

		expected = new BinaryConnective(BinaryConnective.BinaryConnectiveType.NOR);
		actual = factory.createElement("⊽");
		assertEquals("Expect created connective to be equal to the factory-built one", expected, actual);

		expected = new BinaryConnective(BinaryConnective.BinaryConnectiveType.AND);
		actual = factory.createElement("∧");
		assertEquals("Expect created connective to be equal to the factory-built one", expected, actual);

		expected = new BinaryConnective(BinaryConnective.BinaryConnectiveType.NAND);
		actual = factory.createElement("⊼");
		assertEquals("Expect created connective to be equal to the factory-built one", expected, actual);

		expected = new BinaryConnective(BinaryConnective.BinaryConnectiveType.IMPLIES);
		actual = factory.createElement("→");
		assertEquals("Expect created connective to be equal to the factory-built one", expected, actual);

		expected = new BinaryConnective(BinaryConnective.BinaryConnectiveType.NIMPLIES);
		actual = factory.createElement("↛");
		assertEquals("Expect created connective to be equal to the factory-built one", expected, actual);

		expected = new BinaryConnective(BinaryConnective.BinaryConnectiveType.IFF);
		actual = factory.createElement("↔");
		assertEquals("Expect created connective to be equal to the factory-built one", expected, actual);

		expected = new BinaryConnective(BinaryConnective.BinaryConnectiveType.NIFF);
		actual = factory.createElement("↮");
		assertEquals("Expect created connective to be equal to the factory-built one", expected, actual);

		expected = new BinaryConnective(BinaryConnective.BinaryConnectiveType.IMPLIED_BY);
		actual = factory.createElement("←");
		assertEquals("Expect created connective to be equal to the factory-built one", expected, actual);

		expected = new BinaryConnective(BinaryConnective.BinaryConnectiveType.NIMPLIED_BY);
		actual = factory.createElement("↚");
		assertEquals("Expect created connective to be equal to the factory-built one", expected, actual);
	}
}
