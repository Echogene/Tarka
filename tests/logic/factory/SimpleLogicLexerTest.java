package logic.factory;

import org.junit.BeforeClass;
import org.junit.Test;
import reading.lexing.Token;

import java.util.Arrays;
import java.util.List;

import static logic.factory.SimpleLogicLexer.lexAtom;
import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.*;
import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class SimpleLogicLexerTest {
	private static SimpleLogicLexer lexer;

	@BeforeClass
	public static void setUp() {
		lexer = new SimpleLogicLexer();
	}

	@Test
	public void testTokeniseString() throws Exception {
		testTokensKeysEqualIntArray("(test∊testSet)", Arrays.asList(
				OPEN_BRACKET,
				NAME,
				OPERATOR,
				NAME,
				CLOSE_BRACKET
		), "expect simple predicate be lexed correctly");

		testTokensKeysEqualIntArray("(x ∊ {1 2 3})", Arrays.asList(
				OPEN_BRACKET,
				NAME,
				OPERATOR,
				OPEN_BRACKET,
				NAME,
				NAME,
				NAME,
				CLOSE_BRACKET,
				CLOSE_BRACKET
		), "expect simple predicate be lexed correctly");

		testTokensKeysEqualIntArray("(¬∀x(x=y))", Arrays.asList(
				OPEN_BRACKET,
				QUANTIFIER,
				NAME,
				OPEN_BRACKET,
				NAME,
				OPERATOR,
				NAME,
				CLOSE_BRACKET,
				CLOSE_BRACKET
		), "expect simple quantified statement be lexed correctly");

		testTokensKeysEqualIntArray("(∃!x(x=y))", Arrays.asList(
				OPEN_BRACKET,
				QUANTIFIER,
				NAME,
				OPEN_BRACKET,
				NAME,
				OPERATOR,
				NAME,
				CLOSE_BRACKET,
				CLOSE_BRACKET
		), "expect simple quantified statement be lexed correctly");

		testTokensKeysEqualIntArray("((test ∊ testSet) ∨ (y = Y))", Arrays.asList(
				OPEN_BRACKET,
				OPEN_BRACKET,
				NAME,
				OPERATOR,
				NAME,
				CLOSE_BRACKET,
				OPERATOR,
				OPEN_BRACKET,
				NAME,
				OPERATOR,
				NAME,
				CLOSE_BRACKET,
				CLOSE_BRACKET
		), "expect simple connected predicates be lexed correctly");

		testTokensKeysEqualIntArray("((function test)∊testSet)", Arrays.asList(
				OPEN_BRACKET,
				OPEN_BRACKET,
				NAME,
				NAME,
				CLOSE_BRACKET,
				OPERATOR,
				NAME,
				CLOSE_BRACKET
		), "expect simple function and predicate be lexed correctly");

		testTokensKeysEqualIntArray("(∀test((function test)∊testSet))", Arrays.asList(
				OPEN_BRACKET,
				QUANTIFIER,
				NAME,
				OPEN_BRACKET,
				OPEN_BRACKET,
				NAME,
				NAME,
				CLOSE_BRACKET,
				OPERATOR,
				NAME,
				CLOSE_BRACKET,
				CLOSE_BRACKET
		), "expect simple function, quantifier and predicate be lexed correctly");

		testTokensKeysEqualIntArray("((∃x(((f x y)=z)∨((g y z)∊Z)))∧(x=t))", Arrays.asList(
				OPEN_BRACKET,
				OPEN_BRACKET,
				QUANTIFIER,
				NAME,
				OPEN_BRACKET,
				OPEN_BRACKET,
				OPEN_BRACKET,
				NAME,
				NAME,
				NAME,
				CLOSE_BRACKET,
				OPERATOR,
				NAME,
				CLOSE_BRACKET,
				OPERATOR,
				OPEN_BRACKET,
				OPEN_BRACKET,
				NAME,
				NAME,
				NAME,
				CLOSE_BRACKET,
				OPERATOR,
				NAME,
				CLOSE_BRACKET,
				CLOSE_BRACKET,
				CLOSE_BRACKET,
				OPERATOR,
				OPEN_BRACKET,
				NAME,
				OPERATOR,
				NAME,
				CLOSE_BRACKET,
				CLOSE_BRACKET
		), "expect complicated expression be lexed correctly");

		testTokensKeysEqualIntArray("(∀x(∃y(((f x y) = testValue)→(testValue∊X))))", Arrays.asList(
				OPEN_BRACKET,
				QUANTIFIER,
				NAME,
				OPEN_BRACKET,
				QUANTIFIER,
				NAME,
				OPEN_BRACKET,
				OPEN_BRACKET,
				OPEN_BRACKET,
				NAME,
				NAME,
				NAME,
				CLOSE_BRACKET,
				OPERATOR,
				NAME,
				CLOSE_BRACKET,
				OPERATOR,
				OPEN_BRACKET,
				NAME,
				OPERATOR,
				NAME,
				CLOSE_BRACKET,
				CLOSE_BRACKET,
				CLOSE_BRACKET,
				CLOSE_BRACKET
		), "expect another complicated expression be lexed correctly");
	}

	@Test
	public void testLexAtom() throws Exception {
		List<String> list = Arrays.asList("x", "test", "test2", "test_name", "2");
		testStringListAgainstTokenKey(NAME, list, "Expect name to be lexed correctly");
		list = Arrays.asList("=", "∊", "∨", "∧", "¬",  "+");
		testStringListAgainstTokenKey(OPERATOR, list, "Expect operator to be lexed correctly");
		list = Arrays.asList("∀", "∃", "∃!", "¬∀");
		testStringListAgainstTokenKey(QUANTIFIER, list, "Expect quantifier to be lexed correctly");
		list = Arrays.asList("(", "{");
		testStringListAgainstTokenKey(OPEN_BRACKET, list, "Expect open paren to be lexed correctly");
		list = Arrays.asList(")", "}");
		testStringListAgainstTokenKey(CLOSE_BRACKET, list, "Expect close paren to be lexed correctly");
	}

	private void testTokensKeysEqualIntArray(String test, List<? extends Token.TokenType> expectedArray, String assertion) throws Exception {
		List<Token> tokens = lexer.tokeniseString(test);
		int i = 0;
		for (Token t : tokens) {
			Token.TokenType a = expectedArray.get(i);
			assertEquals(assertion + " at index " + i, a, t.getType());
			i++;
		}
		assertEquals(assertion, expectedArray.size(), i);
	}

	private void testStringListAgainstTokenKey(Token.TokenType key, List<String> list, String assertion) throws Exception {
		for (String s : list) {
			assertEquals(assertion, new Token(key, s), lexAtom(s));
		}
	}
}
