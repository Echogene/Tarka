package logic.evaluable.constants;

import logic.TestClass;
import logic.factory.FactoryTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class LogicalConstantFactoryTest extends FactoryTest<LogicalConstantFactory<TestClass>> {

	public LogicalConstantFactoryTest() {
		factory = new LogicalConstantFactory<>();
	}

	@Test
	public void testCurlyBracketsThrowException() throws Exception {
		setUpTokens("{⊤}");
		expectFactoryException();
	}

	@Test
	public void testCreateElement() throws Exception {
		LogicalConstant<TestClass> expected;
		LogicalConstant<TestClass> actual;

		expected = new LogicalConstant<>(true);
		setUpTokens("(⊤)");
		actual = (LogicalConstant<TestClass>) factory.createElement(tokens);
		assertEquals("Expected created tautology to be equal to the factory-built one", expected, actual);

		expected = new LogicalConstant<>(false);
		setUpTokens("(⊥)");
		actual = (LogicalConstant<TestClass>) factory.createElement(tokens);
		assertEquals("Expected created contradiction to be equal to the factory-built one", expected, actual);
	}
}
