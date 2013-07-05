package logic.function.reflexiveset.union;

import logic.TestClass;
import logic.factory.SimpleLogicLexerImpl;
import logic.function.Function;
import logic.function.reflexiveset.identity.SetIdentityFunction;
import org.junit.BeforeClass;
import org.junit.Test;
import reading.lexing.Lexer;
import reading.lexing.Token;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class UnionFactoryTest {
	private static Lexer lexer;
	private List<Token> tokens;
	private List<Function<?, ?>> functions;
	private static UnionFactory<TestClass> factory;

	@BeforeClass
	public static void setUp() {
		lexer   = new SimpleLogicLexerImpl();
		factory = new UnionFactory<>();
	}

	@Test
	public void testCreateElement() throws Exception {
		Union<TestClass> expected;
		Union<TestClass> actual;

		expected = UnionFactory.createElement("X", "Y");
		setUpTokens("X ∪ Y");
		setUpFunctions(null, null);
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("X ∪ Y");
		functions = null;
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("X ∪ Y");
		actual = (Union<TestClass>) factory.createElement(tokens);
		assertEquals(expected, actual);

		setUpTokens("X ∪ Y");
		setUpFunctions();
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("() ∪ Y");
		setUpFunctions("X", null);
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("X ∪ ()");
		setUpFunctions(null, "Y");
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("() ∪ ()");
		setUpFunctions("X", "Y");
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("⋃ X Y");
		setUpFunctions(null, null);
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("⋃ Y X");
		setUpFunctions(null, null);
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		expected = UnionFactory.createElement("X", "Y", "Z");
		setUpTokens("⋃ X Y Z");
		setUpFunctions(null, null, null);
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("⋃ () () ()");
		setUpFunctions("X", "Y", "Z");
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		setUpTokens("⋃ X () ()");
		setUpFunctions(null, "Y", "Z");
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);
	}

	private void setUpFunctions(String... identityFunctionParameters) {
		functions = new ArrayList<>();
		for (String identityFunctionParameter : identityFunctionParameters) {
			if (identityFunctionParameter == null || identityFunctionParameter.isEmpty()) {
				functions.add(null);
			} else {
				functions.add(new SetIdentityFunction<TestClass>(identityFunctionParameter));
			}
		}
	}

	private void setUpTokens(String tokenString) throws Exception {
		tokens = lexer.tokeniseString(tokenString);
	}
}
