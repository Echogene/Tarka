package logic.function.reflexive.identity;

import logic.TestClass;
import logic.factory.FactoryTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class IdentityFunctionFactoryTest extends FactoryTest<IdentityFunctionFactory<TestClass>> {

	public IdentityFunctionFactoryTest() {
		factory = new IdentityFunctionFactory<>();
	}

	@Test
	public void testCurlyBracketsThrowException() throws Exception {
		setUpTokens("{x}");
		setUpFunctions("");
		expectFactoryException();

		setUpTokens("{id x}");
		setUpFunctions("");
		expectFactoryException();
	}

	@Test
	public void testCreateElements() throws Exception {
		IdentityFunction<TestClass> expected;
		IdentityFunction<TestClass> actual;

		expected = new IdentityFunction<>("x");
		setUpTokens("(id x)");
		actual = (IdentityFunction<TestClass>) factory.createElement(tokens);
		assertEquals("Expected created identity function to be equal to the factory-built one", expected, actual);

		expected = new IdentityFunction<>("x");
		setUpTokens("(x)");
		actual = (IdentityFunction<TestClass>) factory.createElement(tokens);
		assertEquals("Expected created identity function to be equal to the factory-built one", expected, actual);

		expected = new IdentityFunction<>(new IdentityFunction<>("x"));
		setUpTokens("(id ())");
		setUpIdentityFunction("x");
		actual = (IdentityFunction<TestClass>) factory.createElement(tokens, functions);
		assertEquals("Expected created identity function to be equal to the factory-built one", expected, actual);

		expected = new IdentityFunction<>(new IdentityFunction<>("x"));
		setUpTokens("(())");
		setUpIdentityFunction("x");
		actual = (IdentityFunction<TestClass>) factory.createElement(tokens, functions);
		assertEquals("Expected created identity function to be equal to the factory-built one", expected, actual);
	}
}
