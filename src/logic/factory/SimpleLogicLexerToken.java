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
		OPEN_BRACKET, CLOSE_BRACKET, NAME, OPERATOR, QUANTIFIER
	}
}
