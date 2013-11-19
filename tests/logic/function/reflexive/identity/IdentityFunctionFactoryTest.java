package logic.function.reflexive.identity;

import logic.TestClass;
import logic.factory.FactoryTest;
import logic.identity.IdentityFunctionFactory;
import logic.identity.MemberIdentityFunction;
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
		MemberIdentityFunction<TestClass> expected;
		MemberIdentityFunction<TestClass> actual;

		expected = new MemberIdentityFunction<>("x");
		setUpTokens("(id x)");
		actual = (MemberIdentityFunction<TestClass>) factory.createElement(tokens);
		assertEquals("Expected created identity function to be equal to the factory-built one", expected, actual);

		expected = new MemberIdentityFunction<>("x");
		setUpTokens("(x)");
		actual = (MemberIdentityFunction<TestClass>) factory.createElement(tokens);
		assertEquals("Expected created identity function to be equal to the factory-built one", expected, actual);

		expected = new MemberIdentityFunction<>(new MemberIdentityFunction<>("x"));
		setUpTokens("(id ())");
		setUpIdentityFunction("x");
		actual = (MemberIdentityFunction<TestClass>) factory.createElement(tokens, functions);
		assertEquals("Expected created identity function to be equal to the factory-built one", expected, actual);

		expected = new MemberIdentityFunction<>(new MemberIdentityFunction<>("x"));
		setUpTokens("(())");
		setUpIdentityFunction("x");
		actual = (MemberIdentityFunction<TestClass>) factory.createElement(tokens, functions);
		assertEquals("Expected created identity function to be equal to the factory-built one", expected, actual);
	}
}
