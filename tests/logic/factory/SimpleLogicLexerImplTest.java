package logic.factory;

import org.junit.BeforeClass;
import org.junit.Test;
import reading.lexing.Token;

import java.util.Arrays;
import java.util.List;

import static logic.factory.SimpleLogicLexerImpl.lexAtom;
import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.*;
import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class SimpleLogicLexerImplTest {
	private static SimpleLogicLexerImpl lexer;

	@BeforeClass
	public static void setUp() {
		lexer = new SimpleLogicLexerImpl();
	}

	@Test
	public void testTokeniseString() throws Exception {
		testTokensKeysEqualIntArray("(test∊testSet)", Arrays.asList(
				OPEN_PAREN,
				NAME,
				OPERATOR,
				NAME,
				CLOSE_PAREN
		), "expect simple predicate be lexed correctly");

		testTokensKeysEqualIntArray("(¬∀x(x=y))", Arrays.asList(
				OPEN_PAREN,
				QUANTIFIER,
				NAME,
				OPEN_PAREN,
				NAME,
				OPERATOR,
				NAME,
				CLOSE_PAREN,
				CLOSE_PAREN
		), "expect simple quantified statement be lexed correctly");

		testTokensKeysEqualIntArray("(∃!x(x=y))", Arrays.asList(
				OPEN_PAREN,
				QUANTIFIER,
				NAME,
				OPEN_PAREN,
				NAME,
				OPERATOR,
				NAME,
				CLOSE_PAREN,
				CLOSE_PAREN
		), "expect simple quantified statement be lexed correctly");

		testTokensKeysEqualIntArray("((test ∊ testSet) ∨ (y = Y))", Arrays.asList(
				OPEN_PAREN,
				OPEN_PAREN,
				NAME,
				OPERATOR,
				NAME,
				CLOSE_PAREN,
				OPERATOR,
				OPEN_PAREN,
				NAME,
				OPERATOR,
				NAME,
				CLOSE_PAREN,
				CLOSE_PAREN
		), "expect simple connected predicates be lexed correctly");

		testTokensKeysEqualIntArray("((function test)∊testSet)", Arrays.asList(
				OPEN_PAREN,
				OPEN_PAREN,
				NAME,
				NAME,
				CLOSE_PAREN,
				OPERATOR,
				NAME,
				CLOSE_PAREN
		), "expect simple function and predicate be lexed correctly");

		testTokensKeysEqualIntArray("(∀test((function test)∊testSet))", Arrays.asList(
				OPEN_PAREN,
				QUANTIFIER,
				NAME,
				OPEN_PAREN,
				OPEN_PAREN,
				NAME,
				NAME,
				CLOSE_PAREN,
				OPERATOR,
				NAME,
				CLOSE_PAREN,
				CLOSE_PAREN
		), "expect simple function, quantifier and predicate be lexed correctly");

		testTokensKeysEqualIntArray("((∃x(((f x y)=z)∨((g y z)∊Z)))∧(x=t))", Arrays.asList(
				OPEN_PAREN,
				OPEN_PAREN,
				QUANTIFIER,
				NAME,
				OPEN_PAREN,
				OPEN_PAREN,
				OPEN_PAREN,
				NAME,
				NAME,
				NAME,
				CLOSE_PAREN,
				OPERATOR,
				NAME,
				CLOSE_PAREN,
				OPERATOR,
				OPEN_PAREN,
				OPEN_PAREN,
				NAME,
				NAME,
				NAME,
				CLOSE_PAREN,
				OPERATOR,
				NAME,
				CLOSE_PAREN,
				CLOSE_PAREN,
				CLOSE_PAREN,
				OPERATOR,
				OPEN_PAREN,
				NAME,
				OPERATOR,
				NAME,
				CLOSE_PAREN,
				CLOSE_PAREN
		), "expect complicated expression be lexed correctly");

		testTokensKeysEqualIntArray("(∀x(∃y(((f x y) = testValue)→(testValue∊X))))", Arrays.asList(
				OPEN_PAREN,
				QUANTIFIER,
				NAME,
				OPEN_PAREN,
				QUANTIFIER,
				NAME,
				OPEN_PAREN,
				OPEN_PAREN,
				OPEN_PAREN,
				NAME,
				NAME,
				NAME,
				CLOSE_PAREN,
				OPERATOR,
				NAME,
				CLOSE_PAREN,
				OPERATOR,
				OPEN_PAREN,
				NAME,
				OPERATOR,
				NAME,
				CLOSE_PAREN,
				CLOSE_PAREN,
				CLOSE_PAREN,
				CLOSE_PAREN
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
		list = Arrays.asList("(");
		testStringListAgainstTokenKey(OPEN_PAREN, list, "Expect open paren to be lexed correctly");
		list = Arrays.asList(")");
		testStringListAgainstTokenKey(CLOSE_PAREN, list, "Expect close paren to be lexed correctly");
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
