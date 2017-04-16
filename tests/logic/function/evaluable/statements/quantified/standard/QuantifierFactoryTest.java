package logic.function.evaluable.statements.quantified.standard;

import logic.Connective;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class QuantifierFactoryTest {
	private static QuantifierFactory factory;

	@BeforeClass
	public static void setUp() {
		factory = new QuantifierFactory();
	}

	@Test
	public void testCreateElements() throws Exception {
		Connective expected;
		Connective actual;

		expected = new Quantifier(Quantifier.QuantifierType.EXISTS);
		actual = factory.createElement("∃");
		assertEquals("Expect created connective to be equal to the factory-built one", expected, actual);

		expected = new Quantifier(Quantifier.QuantifierType.NEXISTS);
		actual = factory.createElement("∄");
		assertEquals("Expect created connective to be equal to the factory-built one", expected, actual);

		expected = new Quantifier(Quantifier.QuantifierType.FOR_ALL);
		actual = factory.createElement("∀");
		assertEquals("Expect created connective to be equal to the factory-built one", expected, actual);

		expected = new Quantifier(Quantifier.QuantifierType.NFOR_ALL);
		actual = factory.createElement("¬∀");
		assertEquals("Expect created connective to be equal to the factory-built one", expected, actual);
	}
}
