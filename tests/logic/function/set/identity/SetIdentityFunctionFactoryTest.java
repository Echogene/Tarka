package logic.function.set.identity;

import logic.TestClass;
import logic.factory.FactoryTest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class SetIdentityFunctionFactoryTest extends FactoryTest<SetIdentityFunctionFactory<TestClass>> {
	public SetIdentityFunctionFactoryTest() {
		super();
		factory = new SetIdentityFunctionFactory<>();
		functionFactory = new SetIdentityFunctionFactory<>();
	}

	@Test
	public void testCurlyBracketsThrowException() throws Exception {
		setUpTokens("{Id X}");
		setUpFunctions("");
		expectFactoryException();
	}

	@Test
	public void testCreateElement() throws Exception {
		SetIdentityFunction<TestClass> expected;
		SetIdentityFunction<TestClass> actual;

		expected = new SetIdentityFunction<>("X");
		setUpTokens("(Id X)");
		actual = (SetIdentityFunction<TestClass>) factory.createElement(tokens);
		assertEquals("Expected created identity function to be equal to the factory-built one", expected, actual);

		expected = new SetIdentityFunction<>(new SetIdentityFunction<>("X"));
		setUpTokens("(Id ())");
		setUpSetIdentityFunction("X");
		actual = (SetIdentityFunction<TestClass>) factory.createElement(tokens, functions);
		assertEquals("Expected created identity function to be equal to the factory-built one", expected, actual);
	}
}
