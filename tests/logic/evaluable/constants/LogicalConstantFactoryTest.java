package logic.evaluable.constants;

import logic.TestClass;
import logic.factory.FactoryTest;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Steven Weston
 */
public class LogicalConstantFactoryTest extends FactoryTest<LogicalConstantFactory<TestClass>> {

	public LogicalConstantFactoryTest() {
		factory = new LogicalConstantFactory<>();
	}

	@Test
	public void testCreateElement() throws Exception {
		LogicalConstant<TestClass> expected;
		LogicalConstant<TestClass> actual;

		expected = new LogicalConstant<>(true);
		setUpTokens("⊤");
		actual = (LogicalConstant<TestClass>) factory.createElement(tokens);
		assertEquals("Expected created tautology to be equal to the factory-built one", expected, actual);

		expected = new LogicalConstant<>(false);
		setUpTokens("⊥");
		actual = (LogicalConstant<TestClass>) factory.createElement(tokens);
		assertEquals("Expected created contradiction to be equal to the factory-built one", expected, actual);
	}

	@Test
	public void testMatches() throws Exception {
		setUpTokens("⊤");
		assertTrue("Expect tautology to match", factory.matches(tokens));

		setUpTokens("⊥");
		assertTrue("Expect tautology to match", factory.matches(tokens));

		setUpTokens("⊤ ⊤");
		assertFalse("Expect wrong number of tokens to not match", factory.matches(tokens));

		setUpTokens("x");
		assertFalse("Expect bad token to not match", factory.matches(tokens));

		setUpTokens("∨");
		assertFalse("Expect wrong operator to not match", factory.matches(tokens));
	}
}
