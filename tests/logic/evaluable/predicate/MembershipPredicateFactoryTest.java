package logic.evaluable.predicate;

import logic.TestClass;
import logic.factory.SimpleLogicLexerImpl;
import logic.function.Function;
import logic.function.reflexive.IdentityFunction;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexiveset.ReflexiveSetFunction;
import logic.function.reflexiveset.identity.SetIdentityFunction;
import org.junit.BeforeClass;
import org.junit.Test;
import reading.lexing.Token;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Steven Weston
 */
public class MembershipPredicateFactoryTest {
	private static List<Token> tokens;
	private static List<Function<?, ?>> functions;
	private static SimpleLogicLexerImpl lexer;
	private static MembershipPredicateFactory<TestClass> factory;

	@BeforeClass
	public static void setUp() {
		lexer   = new SimpleLogicLexerImpl();
		factory = new MembershipPredicateFactory<>();
	}

	@Test
	public void testCreateElement() throws Exception {
		Function<TestClass, Boolean> expected;
		Function<TestClass, Boolean> actual;

		expected = new MembershipPredicate<>("x", "X");
		setUpTokens("x ∊ X");
		actual = factory.createElement(tokens);
		assertEquals("Expected created membership predicate to be equal to the factory-built one", expected, actual);

		setUpFunctions("x", "");
		expected = new MembershipPredicate<>((ReflexiveFunction<TestClass>) functions.get(0), "X");
		setUpTokens("() ∊ X");
		actual = factory.createElement(tokens, functions);
		assertEquals("Expected created membership predicate to be equal to the factory-built one", expected, actual);

		setUpFunctions("", "X");
		expected = new MembershipPredicate<>("x", (ReflexiveSetFunction<TestClass>) functions.get(1));
		setUpTokens("x ∊ ()");
		actual = factory.createElement(tokens, functions);
		assertEquals("Expected created membership predicate to be equal to the factory-built one", expected, actual);

		setUpFunctions("x", "X");
		expected = new MembershipPredicate<>(
				(ReflexiveFunction<TestClass>) functions.get(0),
				(ReflexiveSetFunction<TestClass>) functions.get(1));
		setUpTokens("() ∊ ()");
		actual = factory.createElement(tokens, functions);
		assertEquals("Expected created membership predicate to be equal to the factory-built one", expected, actual);
	}

	@Test
	public void testMatchesTwoNameTokens() throws Exception {
		setUpTokens("x∊X");
		setUpFunctions("", "");
		assertTrue("Expect two name tokens to match two name tokens", factory.matchesTwoNameTokens(tokens, functions));

		setUpTokens("x∊X");
		functions = null;
		assertTrue("Expect null functions to match two name tokens", factory.matchesTwoNameTokens(tokens, functions));

		tokens = null;
		setUpFunctions("", "");
		assertFalse("Expect null tokens to not match two name tokens", factory.matchesTwoNameTokens(tokens, functions));

		setUpTokens("x X");
		setUpFunctions("", "");
		assertFalse("Expect wrong number of tokens to not match two name tokens", factory.matchesTwoNameTokens(tokens, functions));

		setUpTokens("(∊X");
		setUpFunctions("", "");
		assertFalse("Expect bad name token to not match two name tokens", factory.matchesTwoNameTokens(tokens, functions));

		setUpTokens("x∊(");
		setUpFunctions("", "");
		assertFalse("Expect bad name token to not match two name tokens", factory.matchesTwoNameTokens(tokens, functions));

		setUpTokens("x(X");
		setUpFunctions("", "");
		assertFalse("Expect bad operator token to not match two name tokens", factory.matchesTwoNameTokens(tokens, functions));

		setUpTokens("x=X");
		setUpFunctions("", "");
		assertFalse("Expect wrong operator token to not match two name tokens", factory.matchesTwoNameTokens(tokens, functions));

		setUpTokens("x∊X");
		setUpFunctions("", "");
		functions.add(null);
		assertFalse("Expect wrong number of functions to not match two name tokens", factory.matchesTwoNameTokens(tokens, functions));

		setUpTokens("x∊X");
		setUpFunctions("x", "");
		assertFalse("Expect additional function to not match two name tokens", factory.matchesTwoNameTokens(tokens, functions));

		setUpTokens("x∊X");
		setUpFunctions("", "X");
		assertFalse("Expect additional function to not match two name tokens", factory.matchesTwoNameTokens(tokens, functions));

		setUpTokens("x∊X");
		setUpFunctions("x", "X");
		assertFalse("Expect additional function to not match two name tokens", factory.matchesTwoNameTokens(tokens, functions));
	}

	@Test
	public void testMatchesFirstFunctionSecondNameToken() throws Exception {
		setUpTokens("()∊X");
		setUpFunctions("x", "");
		assertTrue("Expect tokens to match one function and one name token", factory.matchesFirstFunctionSecondNameToken(tokens, functions));

		tokens = null;
		setUpFunctions("x", "");
		assertFalse("Expect null tokens to not match one function and one name token", factory.matchesFirstFunctionSecondNameToken(tokens, functions));

		setUpTokens("() X");
		setUpFunctions("x", "");
		assertFalse("Expect wrong number of tokens to not match one function and one name token", factory.matchesFirstFunctionSecondNameToken(tokens, functions));

		setUpTokens("x)∊X");
		setUpFunctions("x", "");
		assertFalse("Expect bad bracket token to not match one function and one name token", factory.matchesFirstFunctionSecondNameToken(tokens, functions));

		setUpTokens("(x∊X");
		setUpFunctions("x", "");
		assertFalse("Expect bad bracket token to not match one function and one name token", factory.matchesFirstFunctionSecondNameToken(tokens, functions));

		setUpTokens("() x X");
		setUpFunctions("x", "");
		assertFalse("Expect bad operator token to not match one function and one name token", factory.matchesFirstFunctionSecondNameToken(tokens, functions));

		setUpTokens("()=X");
		setUpFunctions("x", "");
		assertFalse("Expect wrong operator token to not match one function and one name token", factory.matchesFirstFunctionSecondNameToken(tokens, functions));

		setUpTokens("()∊(");
		setUpFunctions("x", "");
		assertFalse("Expect bad name token to not match one function and one name token", factory.matchesFirstFunctionSecondNameToken(tokens, functions));

		setUpTokens("()∊X");
		functions = null;
		assertFalse("Expect null functions to not match one function and one name token", factory.matchesFirstFunctionSecondNameToken(tokens, functions));

		setUpTokens("()∊X");
		setUpFunctions("x", "");
		functions.add(null);
		assertFalse("Expect wrong number of functions to not match one function and one name token", factory.matchesFirstFunctionSecondNameToken(tokens, functions));

		setUpTokens("()∊X");
		setUpFunctions("", "");
		assertFalse("Expect missing function to not match one function and one name token", factory.matchesFirstFunctionSecondNameToken(tokens, functions));

		setUpTokens("()∊X");
		functions = new ArrayList<>(2);
		functions.add(new SetIdentityFunction<TestClass>("X"));
		functions.add(null);
		assertFalse("Expect wrong function type to not match one function and one name token", factory.matchesFirstFunctionSecondNameToken(tokens, functions));

		setUpTokens("()∊X");
		setUpFunctions("x", "X");
		assertFalse("Expect additional function to not match one function and one name token", factory.matchesFirstFunctionSecondNameToken(tokens, functions));
	}

	@Test
	public void testMatchesFirstNameTokenSecondFunction() throws Exception {
		setUpTokens("x∊()");
		setUpFunctions("", "X");
		assertTrue("Expect tokens to match one name token and one function", factory.matchesFirstNameTokenSecondFunction(tokens, functions));

		tokens = null;
		setUpFunctions("", "X");
		assertFalse("Expect null tokens to not match one name token and one function", factory.matchesFirstNameTokenSecondFunction(tokens, functions));

		setUpTokens("x ()");
		setUpFunctions("", "X");
		assertFalse("Expect wrong number of tokens to not match one name token and one function", factory.matchesFirstNameTokenSecondFunction(tokens, functions));

		setUpTokens("(∊()");
		setUpFunctions("", "X");
		assertFalse("Expect bad name token to not match one name token and one function", factory.matchesFirstNameTokenSecondFunction(tokens, functions));

		setUpTokens("x(()");
		setUpFunctions("", "X");
		assertFalse("Expect bad operator token to not match one name token and one function", factory.matchesFirstNameTokenSecondFunction(tokens, functions));

		setUpTokens("x=()");
		setUpFunctions("", "X");
		assertFalse("Expect wrong operator token to not match one name token and one function", factory.matchesFirstNameTokenSecondFunction(tokens, functions));

		setUpTokens("x∊X)");
		setUpFunctions("", "X");
		assertFalse("Expect wrong operator token to not match one name token and one function", factory.matchesFirstNameTokenSecondFunction(tokens, functions));

		setUpTokens("x∊(X");
		setUpFunctions("", "X");
		assertFalse("Expect wrong operator token to not match one name token and one function", factory.matchesFirstNameTokenSecondFunction(tokens, functions));

		setUpTokens("x∊()");
		functions = null;
		assertFalse("Expect null functions to not match one name token and one function", factory.matchesFirstNameTokenSecondFunction(tokens, functions));

		setUpTokens("x∊()");
		setUpFunctions("", "X");
		functions.add(null);
		assertFalse("Expect wrong number of functions to not match one name token and one function", factory.matchesFirstNameTokenSecondFunction(tokens, functions));

		setUpTokens("x∊()");
		setUpFunctions("x", "X");
		assertFalse("Expect additional function to not match one name token and one function", factory.matchesFirstNameTokenSecondFunction(tokens, functions));

		setUpTokens("x∊()");
		setUpFunctions("", "");
		assertFalse("Expect missing function to not match one name token and one function", factory.matchesFirstNameTokenSecondFunction(tokens, functions));

		setUpTokens("x∊()");
		functions = new ArrayList<>(2);
		functions.add(null);
		functions.add(new IdentityFunction<TestClass>("x"));
		assertFalse("Expect wrong function type to not match one name token and one function", factory.matchesFirstNameTokenSecondFunction(tokens, functions));
	}

	@Test
	public void testMatchesTwoFunctions() throws Exception {
		setUpTokens("()∊()");
		setUpFunctions("x", "X");
		assertTrue("Expect tokens to match two functions", factory.matchesTwoFunctions(tokens, functions));

		tokens = null;
		setUpFunctions("x", "X");
		assertFalse("Expect null tokens to not match two functions", factory.matchesTwoFunctions(tokens, functions));

		setUpTokens("x∊()");
		setUpFunctions("x", "X");
		assertFalse("Expect wrong number of tokens to not match two functions", factory.matchesTwoFunctions(tokens, functions));

		setUpTokens("x)∊()");
		setUpFunctions("x", "X");
		assertFalse("Expect bad bracket token to not match two functions", factory.matchesTwoFunctions(tokens, functions));

		setUpTokens("(x∊()");
		setUpFunctions("x", "X");
		assertFalse("Expect bad bracket token to not match two functions", factory.matchesTwoFunctions(tokens, functions));

		setUpTokens("() x ()");
		setUpFunctions("x", "X");
		assertFalse("Expect bad operator token to not match two functions", factory.matchesTwoFunctions(tokens, functions));

		setUpTokens("()=()");
		setUpFunctions("x", "X");
		assertFalse("Expect wrong operator token to not match two functions", factory.matchesTwoFunctions(tokens, functions));

		setUpTokens("()∊X)");
		setUpFunctions("x", "X");
		assertFalse("Expect bad bracket token to not match two functions", factory.matchesTwoFunctions(tokens, functions));

		setUpTokens("()∊(X");
		setUpFunctions("x", "X");
		assertFalse("Expect bad bracket token to not match two functions", factory.matchesTwoFunctions(tokens, functions));

		setUpTokens("()∊()");
		functions = null;
		assertFalse("Expect null functions to not match two functions", factory.matchesTwoFunctions(tokens, functions));

		setUpTokens("()∊()");
		setUpFunctions("x", "X");
		functions.add(null);
		assertFalse("Expect wrong number of functions to not match two functions", factory.matchesTwoFunctions(tokens, functions));

		setUpTokens("()∊()");
		setUpFunctions("", "X");
		assertFalse("Expect missing function to not match two functions", factory.matchesTwoFunctions(tokens, functions));

		setUpTokens("()∊()");
		setUpFunctions("x", "");
		assertFalse("Expect missing function to not match two functions", factory.matchesTwoFunctions(tokens, functions));

		setUpTokens("()∊()");
		functions = new ArrayList<>(2);
		functions.add(new IdentityFunction<TestClass>("x"));
		functions.add(new IdentityFunction<TestClass>("x"));
		assertFalse("Expect wrong function type to not match two functions", factory.matchesTwoFunctions(tokens, functions));

		setUpTokens("()∊()");
		functions = new ArrayList<>(2);
		functions.add(new SetIdentityFunction<TestClass>("X"));
		functions.add(new SetIdentityFunction<TestClass>("X"));
		assertFalse("Expect wrong function type to not match two functions", factory.matchesTwoFunctions(tokens, functions));
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
			functions.add(new SetIdentityFunction<TestClass>(identityFunctionParameter2));
		}
	}

	private void setUpTokens(String tokenString) throws Exception {
		tokens = lexer.tokeniseString(tokenString);
	}
}
