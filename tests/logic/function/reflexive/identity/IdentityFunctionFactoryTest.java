package logic.function.reflexive.identity;

import logic.TestClass;
import logic.factory.FactoryTest;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Steven Weston
 */
public class IdentityFunctionFactoryTest extends FactoryTest<IdentityFunctionFactory<TestClass>> {

	public IdentityFunctionFactoryTest() {
		factory = new IdentityFunctionFactory<>();
	}

	@Test
	public void testCreateElements() throws Exception {
		IdentityFunction<TestClass> expected;
		IdentityFunction<TestClass> actual;

		expected = new IdentityFunction<>("x");
		setUpTokens("id x");
		actual = (IdentityFunction<TestClass>) factory.createElement(tokens);
		assertEquals("Expected created identity function to be equal to the factory-built one", expected, actual);

		expected = new IdentityFunction<>("x");
		setUpTokens("x");
		actual = (IdentityFunction<TestClass>) factory.createElement(tokens);
		assertEquals("Expected created identity function to be equal to the factory-built one", expected, actual);

		expected = new IdentityFunction<>(new IdentityFunction<>("x"));
		setUpTokens("id ()");
		setUpIdentityFunction("x");
		actual = (IdentityFunction<TestClass>) factory.createElement(tokens, functions);
		assertEquals("Expected created identity function to be equal to the factory-built one", expected, actual);

		expected = new IdentityFunction<>(new IdentityFunction<>("x"));
		setUpTokens("()");
		setUpIdentityFunction("x");
		actual = (IdentityFunction<TestClass>) factory.createElement(tokens, functions);
		assertEquals("Expected created identity function to be equal to the factory-built one", expected, actual);
	}

	@Test
	public void testMatchesStandardForm() throws Exception {
		setUpTokens("id x");
		setUpIdentityFunction("");
		assertTrue("Expect standard tokens to match standard form", factory.matchesStandardForm(tokens, functions));

		setUpTokens("id x y");
		setUpIdentityFunction("");
		assertFalse("Expect additional parameter to not match standard form", factory.matchesStandardForm(tokens, functions));

		setUpTokens("f x");
		setUpIdentityFunction("");
		assertFalse("Expect bad function name to not match standard form", factory.matchesStandardForm(tokens, functions));

		setUpTokens("id (");
		setUpIdentityFunction("");
		assertFalse("Expect bad parameter name to not match standard form", factory.matchesStandardForm(tokens, functions));

		setUpTokens("id x");
		setUpIdentityFunction("x");
		assertFalse("Expect unexpected function to not match standard form", factory.matchesStandardForm(tokens, functions));

		setUpTokens("id x");
		setUpIdentityFunction("");
		functions.add(null);
		assertFalse("Expect additional function to not match standard form", factory.matchesStandardForm(tokens, functions));
	}

	@Test
	public void testMatchesSingleNameToken() throws Exception {
		setUpTokens("x");
		setUpIdentityFunction("");
		assertTrue("Expect single name token to match single name token", factory.matchesSingleNameToken(tokens, functions));

		setUpTokens("id");
		setUpIdentityFunction("");
		assertTrue("Expect confusing name token to match single name token", factory.matchesSingleNameToken(tokens, functions));

		setUpTokens("(");
		setUpIdentityFunction("");
		assertFalse("Expect bad token to not match single name token", factory.matchesSingleNameToken(tokens, functions));

		setUpTokens("x y");
		setUpIdentityFunction("");
		assertFalse("Expect additional parameter to not match single name token", factory.matchesSingleNameToken(tokens, functions));

		setUpTokens("x");
		setUpIdentityFunction("x");
		assertFalse("Expect unexpected function to not match single name token", factory.matchesSingleNameToken(tokens, functions));
	}

	@Test
	public void testNoFunctions() throws Exception {
		setUpIdentityFunction("");
		assertTrue("Expect single null function to count as no function", factory.noFunctions(functions));

		functions = null;
		assertTrue("Expect no functions to count as no function", factory.noFunctions(functions));

		setUpIdentityFunction("x");
		assertFalse("Expect single function to not count as no function", factory.noFunctions(functions));
	}

	@Test
	public void testMatchesStandardFormWithFunction() throws Exception {
		setUpTokens("id ()");
		setUpIdentityFunction("x");
		assertTrue("Expect standard form with function to match standard form with function", factory.matchesStandardFormWithFunction(tokens, functions));

		setUpTokens("id ()");
		setUpIdentityFunction("");
		assertFalse("Expect missing function to not match standard form with function", factory.matchesStandardFormWithFunction(tokens, functions));

		setUpTokens("x ()");
		setUpIdentityFunction("x");
		assertFalse("Expect function name to not match standard form with function", factory.matchesStandardFormWithFunction(tokens, functions));

		setUpTokens("id (x");
		setUpIdentityFunction("x");
		assertFalse("Expect bad brackets to not match standard form with function", factory.matchesStandardFormWithFunction(tokens, functions));
	}

	@Test
	public void testMatchesSingleFunction() throws Exception {
		setUpTokens("()");
		setUpIdentityFunction("x");
		assertTrue("Expect single function to match single function", factory.matchesSingleFunction(tokens, functions));

		setUpTokens("()");
		setUpIdentityFunction("");
		assertFalse("Expect missing function to not match single function", factory.matchesSingleFunction(tokens, functions));

		setUpTokens("(x");
		setUpIdentityFunction("x");
		assertFalse("Expect bad brackets to not match single function", factory.matchesSingleFunction(tokens, functions));
	}

	@Test
	public void testOneFunction() throws Exception {
		setUpIdentityFunction("");
		assertFalse("Expect single null function to not count as one function", factory.oneFunction(functions));

		functions = null;
		assertFalse("Expect no functions to not count as one function", factory.oneFunction(functions));

		setUpIdentityFunction("x");
		assertTrue("Expect single function to count as one function", factory.oneFunction(functions));

		setUpIdentityFunction("x");
		functions.add(null);
		assertFalse("Expect additional function to not count as one function", factory.oneFunction(functions));
	}
}
