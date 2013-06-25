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

import static org.junit.Assert.*;

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

	@Test
	public void testMatchesBinaryUnionWithBothFunctions() throws Exception {
		setUpTokens("() ∪ ()");
		setUpFunctions("X", "Y");
		assertTrue(factory.matchesBinaryUnionWithBothFunctions(tokens, functions));

		tokens = null;
		setUpFunctions("X", "Y");
		assertFalse(factory.matchesBinaryUnionWithBothFunctions(tokens, functions));

		setUpTokens("(X) ∪ ()");
		setUpFunctions("X", "Y");
		assertFalse(factory.matchesBinaryUnionWithBothFunctions(tokens, functions));

		setUpTokens("X) ∪ ()");
		setUpFunctions("X", "Y");
		assertFalse(factory.matchesBinaryUnionWithBothFunctions(tokens, functions));

		setUpTokens("(X ∪ ()");
		setUpFunctions("X", "Y");
		assertFalse(factory.matchesBinaryUnionWithBothFunctions(tokens, functions));

		setUpTokens("() X ()");
		setUpFunctions("X", "Y");
		assertFalse(factory.matchesBinaryUnionWithBothFunctions(tokens, functions));

		setUpTokens("() ∨ ()");
		setUpFunctions("X", "Y");
		assertFalse(factory.matchesBinaryUnionWithBothFunctions(tokens, functions));

		setUpTokens("() ∪ X)");
		setUpFunctions("X", "Y");
		assertFalse(factory.matchesBinaryUnionWithBothFunctions(tokens, functions));

		setUpTokens("() ∪ (X");
		setUpFunctions("X", "Y");
		assertFalse(factory.matchesBinaryUnionWithBothFunctions(tokens, functions));

		setUpTokens("() ∪ ()");
		functions = null;
		assertFalse(factory.matchesBinaryUnionWithBothFunctions(tokens, functions));

		setUpTokens("() ∪ ()");
		setUpFunctions("X");
		assertFalse(factory.matchesBinaryUnionWithBothFunctions(tokens, functions));

		setUpTokens("() ∪ ()");
		setUpFunctions(null, "Y");
		assertFalse(factory.matchesBinaryUnionWithBothFunctions(tokens, functions));

		setUpTokens("() ∪ ()");
		setUpFunctions("X", null);
		assertFalse(factory.matchesBinaryUnionWithBothFunctions(tokens, functions));
	}

	@Test
	public void testMatchesBinaryUnionWithSecondFunction() throws Exception {
		setUpTokens("X ∪ ()");
		setUpFunctions(null, "Y");
		assertTrue(factory.matchesBinaryUnionWithSecondFunction(tokens, functions));

		tokens = null;
		setUpFunctions(null, "Y");
		assertFalse(factory.matchesBinaryUnionWithSecondFunction(tokens, functions));

		setUpTokens("X Y ∪ ()");
		setUpFunctions(null, "Y");
		assertFalse(factory.matchesBinaryUnionWithSecondFunction(tokens, functions));

		setUpTokens("∪ ∪ ()");
		setUpFunctions(null, "Y");
		assertFalse(factory.matchesBinaryUnionWithSecondFunction(tokens, functions));

		setUpTokens("X X ()");
		setUpFunctions(null, "Y");
		assertFalse(factory.matchesBinaryUnionWithSecondFunction(tokens, functions));

		setUpTokens("X ∨ ()");
		setUpFunctions(null, "Y");
		assertFalse(factory.matchesBinaryUnionWithSecondFunction(tokens, functions));

		setUpTokens("X ∪ X)");
		setUpFunctions(null, "Y");
		assertFalse(factory.matchesBinaryUnionWithSecondFunction(tokens, functions));

		setUpTokens("X ∪ (X");
		setUpFunctions(null, "Y");
		assertFalse(factory.matchesBinaryUnionWithSecondFunction(tokens, functions));

		setUpTokens("X ∪ ()");
		functions = null;
		assertFalse(factory.matchesBinaryUnionWithSecondFunction(tokens, functions));

		setUpTokens("X ∪ ()");
		setUpFunctions("Y");
		assertFalse(factory.matchesBinaryUnionWithSecondFunction(tokens, functions));

		setUpTokens("X ∪ ()");
		setUpFunctions(null, null);
		assertFalse(factory.matchesBinaryUnionWithSecondFunction(tokens, functions));

		setUpTokens("X ∪ ()");
		setUpFunctions("X", "Y");
		assertFalse(factory.matchesBinaryUnionWithSecondFunction(tokens, functions));
	}

	@Test
	public void testMatchesBinaryUnionWithFirstFunction() throws Exception {
		setUpTokens("() ∪ Y");
		setUpFunctions("X", null);
		assertTrue(factory.matchesBinaryUnionWithFirstFunction(tokens, functions));

		tokens = null;
		setUpFunctions("X", null);
		assertFalse(factory.matchesBinaryUnionWithFirstFunction(tokens, functions));

		setUpTokens("() ∪ X Y");
		setUpFunctions("X", null);
		assertFalse(factory.matchesBinaryUnionWithFirstFunction(tokens, functions));

		setUpTokens("X) ∪ Y");
		setUpFunctions("X", null);
		assertFalse(factory.matchesBinaryUnionWithFirstFunction(tokens, functions));

		setUpTokens("(X ∪ Y");
		setUpFunctions("X", null);
		assertFalse(factory.matchesBinaryUnionWithFirstFunction(tokens, functions));

		setUpTokens("() X Y");
		setUpFunctions("X", null);
		assertFalse(factory.matchesBinaryUnionWithFirstFunction(tokens, functions));

		setUpTokens("() ∨ Y");
		setUpFunctions("X", null);
		assertFalse(factory.matchesBinaryUnionWithFirstFunction(tokens, functions));

		setUpTokens("() ∪ ∨");
		setUpFunctions("X", null);
		assertFalse(factory.matchesBinaryUnionWithFirstFunction(tokens, functions));

		setUpTokens("() ∪ Y");
		functions = null;
		assertFalse(factory.matchesBinaryUnionWithFirstFunction(tokens, functions));

		setUpTokens("() ∪ Y");
		setUpFunctions("X");
		assertFalse(factory.matchesBinaryUnionWithFirstFunction(tokens, functions));

		setUpTokens("() ∪ Y");
		setUpFunctions(null, null);
		assertFalse(factory.matchesBinaryUnionWithFirstFunction(tokens, functions));

		setUpTokens("() ∪ Y");
		setUpFunctions("X", "Y");
		assertFalse(factory.matchesBinaryUnionWithFirstFunction(tokens, functions));
	}

	@Test
	public void testMatchesBinaryUnionWithStrings() throws Exception {
		setUpTokens("X ∪ Y");
		setUpFunctions();
		assertTrue(factory.matchesBinaryUnionWithStrings(tokens, functions));

		setUpTokens("X ∪ Y");
		functions = null;
		assertTrue(factory.matchesBinaryUnionWithStrings(tokens, functions));

		setUpTokens("X ∪ Y");
		setUpFunctions(null, null);
		assertTrue(factory.matchesBinaryUnionWithStrings(tokens, functions));

		tokens = null;
		setUpFunctions();
		assertFalse(factory.matchesBinaryUnionWithStrings(tokens, functions));

		setUpTokens("X");
		setUpFunctions();
		assertFalse(factory.matchesBinaryUnionWithStrings(tokens, functions));

		setUpTokens("∪ ∪ Y");
		setUpFunctions();
		assertFalse(factory.matchesBinaryUnionWithStrings(tokens, functions));

		setUpTokens("X X Y");
		setUpFunctions();
		assertFalse(factory.matchesBinaryUnionWithStrings(tokens, functions));

		setUpTokens("X ∨ Y");
		setUpFunctions();
		assertFalse(factory.matchesBinaryUnionWithStrings(tokens, functions));

		setUpTokens("X ∪ ∪");
		setUpFunctions();
		assertFalse(factory.matchesBinaryUnionWithStrings(tokens, functions));

		setUpTokens("X ∪ Y");
		setUpFunctions("X");
		assertFalse(factory.matchesBinaryUnionWithStrings(tokens, functions));
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
