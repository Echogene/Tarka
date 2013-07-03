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

import static org.junit.Assert.assertEquals;

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
