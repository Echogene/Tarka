package logic.evaluable.predicate;

import logic.TestClass;
import logic.evaluable.predicate.equality.EqualityPredicateFactory;
import logic.factory.SimpleLogicLexerImpl;
import logic.function.Function;
import logic.function.reflexive.IdentityFunction;
import org.junit.BeforeClass;
import org.junit.Test;
import reading.lexing.Token;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

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

		expected =EqualityPredicateFactory.createElement("x", "y");
		setUpTokens("x = y");
		actual = factory.createElement(tokens);
		assertEquals("Expected created equality predicate to be equal to the factory-built one", expected, actual);

		setUpFunctions("", "y");
		setUpTokens("x = ()");
		actual = factory.createElement(tokens, functions);
		assertEquals("Expected created equality predicate to be equal to the factory-built one", expected, actual);

		setUpFunctions("x", "");
		setUpTokens("() = y");
		actual = factory.createElement(tokens, functions);
		assertEquals("Expected created equality predicate to be equal to the factory-built one", expected, actual);

		setUpFunctions("x", "y");
		setUpTokens("() = ()");
		actual = factory.createElement(tokens, functions);
		assertEquals("Expected created equality predicate to be equal to the factory-built one", expected, actual);
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
