package logic.factory;

import reading.lexing.Token;

/**
 * @author Steven Weston
 */
public class SimpleLogicLexerToken extends Token {
	public SimpleLogicLexerToken(TokenType key, String value) {
		super(key, value);
	}

	public enum SimpleLogicLexerTokenType implements TokenType {
		OPEN_BRACKET, CLOSE_BRACKET, NAME, OPERATOR, QUANTIFIER;
		public static final String CLOSE_PARENTHESIS = ")";
		public static final String OPEN_PARENTHESIS = "(";

		public static boolean isTokenOpenParenthesis(Token token) {
			return token.isOfType(OPEN_BRACKET)
					&& token.getValue().equals(OPEN_PARENTHESIS);
		}

		public static boolean isTokenCloseParenthesis(Token token) {
			return token.isOfType(CLOSE_BRACKET)
					&& token.getValue().equals(CLOSE_PARENTHESIS);
		}
	}
}
