package logic.function.reflexiveset.union;

import logic.TestClass;
import logic.factory.SimpleLogicLexerImpl;
import logic.function.Function;
import logic.function.reflexiveset.ReflexiveSetFunction;
import logic.function.reflexiveset.identity.SetIdentityFunction;
import org.junit.BeforeClass;
import org.junit.Test;
import reading.lexing.Lexer;
import reading.lexing.Token;

import java.util.ArrayList;
import java.util.Arrays;
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

		expected = new Union<>(Arrays.asList("X", "Y"), Arrays.<ReflexiveSetFunction<TestClass>>asList(null, null));
		setUpTokens("X ∪ Y");
		setUpFunctions(null, null);
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		expected = new Union<>(Arrays.asList("X", "Y"), null);
		setUpTokens("X ∪ Y");
		functions = null;
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		expected = new Union<>(Arrays.asList("X", "Y"), null);
		setUpTokens("X ∪ Y");
		actual = (Union<TestClass>) factory.createElement(tokens);
		assertEquals(expected, actual);

		expected = new Union<>(Arrays.asList("X", "Y"), new ArrayList<>());
		setUpTokens("X ∪ Y");
		setUpFunctions();
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		expected = new Union<>(Arrays.asList("Y"), Arrays.<ReflexiveSetFunction<TestClass>>asList(
				new SetIdentityFunction<TestClass>("X")));
		setUpTokens("() ∪ Y");
		setUpFunctions("X", null);
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		expected = new Union<>(Arrays.asList("X"), Arrays.<ReflexiveSetFunction<TestClass>>asList(
				new SetIdentityFunction<TestClass>("Y")));
		setUpTokens("X ∪ ()");
		setUpFunctions(null, "Y");
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		expected = new Union<>(null, Arrays.<ReflexiveSetFunction<TestClass>>asList(
				new SetIdentityFunction<TestClass>("X"),
				new SetIdentityFunction<TestClass>("Y")));
		setUpTokens("() ∪ ()");
		setUpFunctions("X", "Y");
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		expected = new Union<>(Arrays.asList("X", "Y"), null);
		setUpTokens("⋃ X Y");
		setUpFunctions(null, null);
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		expected = new Union<>(Arrays.asList("X", "Y"), null);
		setUpTokens("⋃ Y X");
		setUpFunctions(null, null);
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		expected = new Union<>(Arrays.asList("X", "Y", "Z"), null);
		setUpTokens("⋃ X Y Z");
		setUpFunctions(null, null, null);
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		expected = new Union<>(null, Arrays.<ReflexiveSetFunction<TestClass>>asList(
				new SetIdentityFunction<TestClass>("X"),
				new SetIdentityFunction<TestClass>("Y"),
				new SetIdentityFunction<TestClass>("Z")));
		setUpTokens("⋃ () () ()");
		setUpFunctions("X", "Y", "Z");
		actual = (Union<TestClass>) factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		expected = new Union<>(Arrays.asList("X"), Arrays.<ReflexiveSetFunction<TestClass>>asList(
				null,
				new SetIdentityFunction<TestClass>("Y"),
				new SetIdentityFunction<TestClass>("Z")));
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
