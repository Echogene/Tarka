package logic.function.reflexive.identity;

import logic.TestClass;
import logic.factory.SimpleLogicLexerImpl;
import logic.function.Function;
import org.junit.BeforeClass;
import org.junit.Test;
import reading.lexing.Token;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Steven Weston
 */
public class IdentityFunctionFactoryTest {
	private static List<Token> tokens;
	private static List<Function<?, ?>> functions;
	private static SimpleLogicLexerImpl lexer;
	private static IdentityFunctionFactory<TestClass> factory;

	@BeforeClass
	public static void setUp() {
		lexer   = new SimpleLogicLexerImpl();
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
		setUpFunction("x");
		actual = (IdentityFunction<TestClass>) factory.createElement(tokens, functions);
		assertEquals("Expected created identity function to be equal to the factory-built one", expected, actual);

		expected = new IdentityFunction<>(new IdentityFunction<>("x"));
		setUpTokens("()");
		setUpFunction("x");
		actual = (IdentityFunction<TestClass>) factory.createElement(tokens, functions);
		assertEquals("Expected created identity function to be equal to the factory-built one", expected, actual);
	}

	@Test
	public void testMatchesStandardForm() throws Exception {
		setUpTokens("id x");
		setUpFunction("");
		assertTrue("Expect standard tokens to match standard form", factory.matchesStandardForm(tokens, functions));

		setUpTokens("id x y");
		setUpFunction("");
		assertFalse("Expect additional parameter to not match standard form", factory.matchesStandardForm(tokens, functions));

		setUpTokens("f x");
		setUpFunction("");
		assertFalse("Expect bad function name to not match standard form", factory.matchesStandardForm(tokens, functions));

		setUpTokens("id (");
		setUpFunction("");
		assertFalse("Expect bad parameter name to not match standard form", factory.matchesStandardForm(tokens, functions));

		setUpTokens("id x");
		setUpFunction("x");
		assertFalse("Expect unexpected function to not match standard form", factory.matchesStandardForm(tokens, functions));

		setUpTokens("id x");
		setUpFunction("");
		functions.add(null);
		assertFalse("Expect additional function to not match standard form", factory.matchesStandardForm(tokens, functions));
	}

	@Test
	public void testMatchesSingleNameToken() throws Exception {
		setUpTokens("x");
		setUpFunction("");
		assertTrue("Expect single name token to match single name token", factory.matchesSingleNameToken(tokens, functions));

		setUpTokens("id");
		setUpFunction("");
		assertTrue("Expect confusing name token to match single name token", factory.matchesSingleNameToken(tokens, functions));

		setUpTokens("(");
		setUpFunction("");
		assertFalse("Expect bad token to not match single name token", factory.matchesSingleNameToken(tokens, functions));

		setUpTokens("x y");
		setUpFunction("");
		assertFalse("Expect additional parameter to not match single name token", factory.matchesSingleNameToken(tokens, functions));

		setUpTokens("x");
		setUpFunction("x");
		assertFalse("Expect unexpected function to not match single name token", factory.matchesSingleNameToken(tokens, functions));
	}

	@Test
	public void testNoFunctions() throws Exception {
		setUpFunction("");
		assertTrue("Expect single null function to count as no function", factory.noFunctions(functions));

		functions = null;
		assertTrue("Expect no functions to count as no function", factory.noFunctions(functions));

		setUpFunction("x");
		assertFalse("Expect single function to not count as no function", factory.noFunctions(functions));
	}

	@Test
	public void testMatchesStandardFormWithFunction() throws Exception {
		setUpTokens("id ()");
		setUpFunction("x");
		assertTrue("Expect standard form with function to match standard form with function", factory.matchesStandardFormWithFunction(tokens, functions));

		setUpTokens("id ()");
		setUpFunction("");
		assertFalse("Expect missing function to not match standard form with function", factory.matchesStandardFormWithFunction(tokens, functions));

		setUpTokens("x ()");
		setUpFunction("x");
		assertFalse("Expect function name to not match standard form with function", factory.matchesStandardFormWithFunction(tokens, functions));

		setUpTokens("id (x");
		setUpFunction("x");
		assertFalse("Expect bad brackets to not match standard form with function", factory.matchesStandardFormWithFunction(tokens, functions));
	}

	@Test
	public void testMatchesSingleFunction() throws Exception {
		setUpTokens("()");
		setUpFunction("x");
		assertTrue("Expect single function to match single function", factory.matchesSingleFunction(tokens, functions));

		setUpTokens("()");
		setUpFunction("");
		assertFalse("Expect missing function to not match single function", factory.matchesSingleFunction(tokens, functions));

		setUpTokens("(x");
		setUpFunction("x");
		assertFalse("Expect bad brackets to not match single function", factory.matchesSingleFunction(tokens, functions));
	}

	@Test
	public void testOneFunction() throws Exception {
		setUpFunction("");
		assertFalse("Expect single null function to not count as one function", factory.oneFunction(functions));

		functions = null;
		assertFalse("Expect no functions to not count as one function", factory.oneFunction(functions));

		setUpFunction("x");
		assertTrue("Expect single function to count as one function", factory.oneFunction(functions));

		setUpFunction("x");
		functions.add(null);
		assertFalse("Expect additional function to not count as one function", factory.oneFunction(functions));
	}
	private void setUpFunction(String identityFunctionParameter) {
		functions = new ArrayList<>(1);
		if (identityFunctionParameter == null || identityFunctionParameter.isEmpty()) {
			functions.add(null);
		} else {
			functions.add(new IdentityFunction<TestClass>(identityFunctionParameter));
		}
	}

	private void setUpTokens(String tokenString) throws Exception {
		tokens = lexer.tokeniseString(tokenString);
	}
}
