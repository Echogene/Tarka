package logic.evaluable.predicate;

import logic.TestClass;
import logic.factory.SimpleLogicLexerImpl;
import logic.function.Function;
import logic.function.reflexive.IdentityFunction;
import logic.function.reflexive.ReflexiveFunction;
import org.junit.BeforeClass;
import org.junit.Test;
import reading.lexing.Token;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Steven Weston
 */
public class EqualityPredicateFactoryTest {
	private static List<Token> tokens;
	private static List<Function<?, ?>> functions;
	private static SimpleLogicLexerImpl lexer;
	private static EqualityPredicateFactory<TestClass> factory;

	@BeforeClass
	public static void setUp() {
		lexer   = new SimpleLogicLexerImpl();
		factory = new EqualityPredicateFactory<>();
	}

	@Test
	public void testCreateElements() throws Exception {
		Function<TestClass, Boolean> expected;
		Function<TestClass, Boolean> actual;

		expected = new EqualityPredicate<>("x", "y");
		setUpTokens("x = y");
		actual = factory.createElement(tokens);
		assertEquals("Expected created equality predicate to be equal to the factory-built one", expected, actual);

		setUpFunctions("", "y");
		expected = new EqualityPredicate<>("x", (ReflexiveFunction<TestClass>) functions.get(1));
		setUpTokens("x = ()");
		actual = factory.createElement(tokens, functions);
		assertEquals("Expected created equality predicate to be equal to the factory-built one", expected, actual);

		setUpFunctions("x", "");
		expected = new EqualityPredicate<>((ReflexiveFunction<TestClass>) functions.get(0), "y");
		setUpTokens("() = y");
		actual = factory.createElement(tokens, functions);
		assertEquals("Expected created equality predicate to be equal to the factory-built one", expected, actual);

		setUpFunctions("x", "y");
		expected = new EqualityPredicate<>(
				(ReflexiveFunction<TestClass>) functions.get(0),
				(ReflexiveFunction<TestClass>) functions.get(1));
		setUpTokens("() = ()");
		actual = factory.createElement(tokens, functions);
		assertEquals("Expected created equality predicate to be equal to the factory-built one", expected, actual);
	}

	@Test
	public void testMatchesTwoNameTokens() throws Exception {
		setUpTokens("x=y");
		setUpFunctions("", "");
		assertTrue("Expect two name tokens to match two name tokens", factory.matchesTwoNameTokens(tokens, functions));

		setUpTokens("( = y");
		setUpFunctions("", "");
		assertFalse("Expect bad first name to not match two name tokens", factory.matchesTwoNameTokens(tokens, functions));

		setUpTokens("x = (");
		setUpFunctions("", "");
		assertFalse("Expect bad last name to not match two name tokens", factory.matchesTwoNameTokens(tokens, functions));

		setUpTokens("x ( y");
		setUpFunctions("", "");
		assertFalse("Expect bad operator to not match two name tokens", factory.matchesTwoNameTokens(tokens, functions));

		setUpTokens("x ∊ y");
		setUpFunctions("", "");
		assertFalse("Expect wrong operator to not match two name tokens", factory.matchesTwoNameTokens(tokens, functions));

		setUpTokens("x = y");
		setUpFunctions("x", "");
		assertFalse("Expect additional function to not match two name tokens", factory.matchesTwoNameTokens(tokens, functions));

		setUpTokens("x = y");
		setUpFunctions("", "y");
		assertFalse("Expect additional function to not match two name tokens", factory.matchesTwoNameTokens(tokens, functions));

		setUpTokens("x = y");
		setUpFunctions("x", "y");
		assertFalse("Expect additional functions to not match two name tokens", factory.matchesTwoNameTokens(tokens, functions));
	}

	@Test
	public void testMatchesFirstFunctionSecondNameToken() throws Exception {
		setUpTokens("()=y");
		setUpFunctions("x", "");
		assertTrue("Expect one function and one name token to match one function and one name token", factory.matchesFirstFunctionSecondNameToken(tokens, functions));

		setUpTokens("x)=y");
		setUpFunctions("x", "");
		assertFalse("Expect bad bracket to not match one function and one name token", factory.matchesFirstFunctionSecondNameToken(tokens, functions));

		setUpTokens("(x=y");
		setUpFunctions("x", "");
		assertFalse("Expect bad bracket to not match one function and one name token", factory.matchesFirstFunctionSecondNameToken(tokens, functions));

		setUpTokens("()=(");
		setUpFunctions("x", "");
		assertFalse("Expect bad parameter to not match one function and one name token", factory.matchesFirstFunctionSecondNameToken(tokens, functions));

		setUpTokens("()(y");
		setUpFunctions("x", "");
		assertFalse("Expect bad operator to not match one function and one name token", factory.matchesFirstFunctionSecondNameToken(tokens, functions));

		setUpTokens("()∊y");
		setUpFunctions("x", "");
		assertFalse("Expect wrong operator to not match one function and one name token", factory.matchesFirstFunctionSecondNameToken(tokens, functions));

		setUpTokens("()=y");
		functions = null;
		assertFalse("Expect null functions to not match one function and one name token", factory.matchesFirstFunctionSecondNameToken(tokens, functions));

		setUpTokens("()=y");
		functions = new ArrayList<>();
		functions.add(null);
		assertFalse("Expect wrong number of functions to not match one function and one name token", factory.matchesFirstFunctionSecondNameToken(tokens, functions));

		setUpTokens("()=y");
		setUpFunctions("", "");
		assertFalse("Expect missing function to not match one function and one name token", factory.matchesFirstFunctionSecondNameToken(tokens, functions));

		setUpTokens("()=y");
		setUpFunctions("x", "y");
		assertFalse("Expect extra function to not match one function and one name token", factory.matchesFirstFunctionSecondNameToken(tokens, functions));
	}

	@Test
	public void testMatchesFirstNameTokenSecondFunction() throws Exception {
		setUpTokens("x=()");
		setUpFunctions("", "y");
		assertTrue("Expect one name token and one function to match one name token and one function", factory.matchesFirstNameTokenSecondFunction(tokens, functions));

		setUpTokens("(=()");
		setUpFunctions("", "y");
		assertFalse("Expect bad name to not match one name token and one function", factory.matchesFirstNameTokenSecondFunction(tokens, functions));

		setUpTokens("x=x)");
		setUpFunctions("", "y");
		assertFalse("Expect bad bracket to not match one name token and one function", factory.matchesFirstNameTokenSecondFunction(tokens, functions));

		setUpTokens("x=(x");
		setUpFunctions("", "y");
		assertFalse("Expect bad bracket to not match one name token and one function", factory.matchesFirstNameTokenSecondFunction(tokens, functions));

		setUpTokens("x(()");
		setUpFunctions("", "y");
		assertFalse("Expect bad operator to not match one name token and one function", factory.matchesFirstNameTokenSecondFunction(tokens, functions));

		setUpTokens("x∊()");
		setUpFunctions("", "y");
		assertFalse("Expect wrong operator to not match one name token and one function", factory.matchesFirstNameTokenSecondFunction(tokens, functions));

		setUpTokens("x=()");
		functions = null;
		assertFalse("Expect null functions to not match one name token and one function", factory.matchesFirstNameTokenSecondFunction(tokens, functions));

		setUpTokens("x=()");
		functions = new ArrayList<>();
		functions.add(null);
		assertFalse("Expect wrong number of functions to not match one name token and one function", factory.matchesFirstNameTokenSecondFunction(tokens, functions));

		setUpTokens("x=()");
		setUpFunctions("", "");
		assertFalse("Expect missing function to not match one name token and one function", factory.matchesFirstNameTokenSecondFunction(tokens, functions));

		setUpTokens("x=()");
		setUpFunctions("x", "y");
		assertFalse("Expect extra function to not match one name token and one function", factory.matchesFirstNameTokenSecondFunction(tokens, functions));
	}

	@Test
	public void testMatchesTwoFunctions() throws Exception {
		setUpTokens("()=()");
		setUpFunctions("x", "y");
		assertTrue("Expect two functions to match two functions", factory.matchesTwoFunctions(tokens, functions));

		setUpTokens("x)=()");
		setUpFunctions("x", "y");
		assertFalse("Expect bad bracket to not match two functions", factory.matchesTwoFunctions(tokens, functions));

		setUpTokens("(x=()");
		setUpFunctions("x", "y");
		assertFalse("Expect bad bracket to not match two functions", factory.matchesTwoFunctions(tokens, functions));

		setUpTokens("()=x)");
		setUpFunctions("x", "y");
		assertFalse("Expect bad bracket to not match two functions", factory.matchesTwoFunctions(tokens, functions));

		setUpTokens("()=(x");
		setUpFunctions("x", "y");
		assertFalse("Expect bad bracket to not match two functions", factory.matchesTwoFunctions(tokens, functions));

		setUpTokens("()(()");
		setUpFunctions("x", "y");
		assertFalse("Expect bad operator to not match two functions", factory.matchesTwoFunctions(tokens, functions));

		setUpTokens("()∊()");
		setUpFunctions("x", "y");
		assertFalse("Expect wrong operator to not match two functions", factory.matchesTwoFunctions(tokens, functions));

		setUpTokens("()=()");
		functions = null;
		assertFalse("Expect null functions to not match two functions", factory.matchesTwoFunctions(tokens, functions));

		setUpTokens("()=()");
		functions = new ArrayList<>();
		functions.add(null);
		assertFalse("Expect wrong number of functions to not match two functions", factory.matchesTwoFunctions(tokens, functions));

		setUpTokens("()=()");
		setUpFunctions("", "");
		assertFalse("Expect missing functions to not match two functions", factory.matchesTwoFunctions(tokens, functions));

		setUpTokens("()=()");
		setUpFunctions("x", "");
		assertFalse("Expect missing function to not match two functions", factory.matchesTwoFunctions(tokens, functions));
	}

	@Test
	public void testMatchFunction() throws Exception {
		setUpFunctions("", "");
		assertTrue("Expect first function to match null", factory.matchFunction(0, false, functions));
		assertFalse("Expect first function to not match existent function", factory.matchFunction(0, true, functions));
		assertTrue("Expect second function to match null", factory.matchFunction(1, false, functions));
		assertFalse("Expect second function to not match existent function", factory.matchFunction(1, true, functions));

		setUpFunctions("x", "");
		assertTrue("Expect first function to match existent function", factory.matchFunction(0, true, functions));
		assertFalse("Expect first function to not match null", factory.matchFunction(0, false, functions));
		assertTrue("Expect second function to match null", factory.matchFunction(1, false, functions));
		assertFalse("Expect second function to not match existent function", factory.matchFunction(1, true, functions));

		setUpFunctions("", "y");
		assertTrue("Expect first function to match null", factory.matchFunction(0, false, functions));
		assertFalse("Expect first function to not match existent function", factory.matchFunction(0, true, functions));
		assertTrue("Expect second function to match existent function", factory.matchFunction(1, true, functions));
		assertFalse("Expect second function to not match null", factory.matchFunction(1, false, functions));

		setUpFunctions("x", "y");
		assertTrue("Expect first function to match existent function", factory.matchFunction(0, true, functions));
		assertFalse("Expect first function to not match null", factory.matchFunction(0, false, functions));
		assertTrue("Expect second function to match existent function", factory.matchFunction(1, true, functions));
		assertFalse("Expect second function to not match null", factory.matchFunction(1, false, functions));
	}

	private void setUpFunctions(String identityFunctionParameter1, String identityFunctionParameter2) {
		functions = new ArrayList<>(2);
		if (identityFunctionParameter1.isEmpty()) {
			functions.add(null);
		} else {
			functions.add(new IdentityFunction<TestClass>(identityFunctionParameter1));
		}
		if (identityFunctionParameter2.isEmpty()) {
			functions.add(null);
		} else {
			functions.add(new IdentityFunction<TestClass>(identityFunctionParameter2));
		}
	}

	private void setUpTokens(String tokenString) throws Exception {
		tokens = lexer.tokeniseString(tokenString);
	}
}
