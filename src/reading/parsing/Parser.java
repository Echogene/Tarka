package reading.parsing;

import reading.lexing.Token;

import java.util.List;

/**
 * @author Steven Weston
 */
public interface Parser {
	ParseTree parseTokens(List<Token> tokens) throws ParserException;
}
