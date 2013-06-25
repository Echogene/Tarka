package reading.lexing;

import java.util.List;

/**
 * A lexer is a simple interface that tokenises a string.
 * @author Steven Weston
 */
public interface Lexer {
	/**
	 * Turn a string into a list of tokens.
	 * @param string The string to tokenise.
	 * @return A list of tokens that represent the string.
	 */
	public List<Token> tokeniseString(String string) throws LexerException;
}
