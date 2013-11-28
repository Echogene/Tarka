package logic.factory;

import reading.lexing.Token;
import reading.parsing.ParseTree;
import reading.parsing.Parser;
import reading.parsing.ParserException;

import java.util.List;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.CLOSE_BRACKET;
import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.OPEN_BRACKET;
import static util.CollectionUtils.first;
import static util.CollectionUtils.last;

/**
 * @author Steven Weston
 */
public class SimpleLogicParser implements Parser {
	@Override
	public ParseTree parseTokens(List<Token> tokens) throws ParserException {
		if (tokens == null || tokens.isEmpty()) {
			return null;
		}
		if (!first(tokens).isOfType(OPEN_BRACKET) || !last(tokens).isOfType(CLOSE_BRACKET)) {
			throw new ParserException("Did you forget to surround with brackets?");
		}
		SimpleLogicParseTree output = new SimpleLogicParseTree();
		SimpleLogicParseTreeNode currentMother = new SimpleLogicParseTreeNode(first(tokens));
		output.getNodes().add(currentMother);
		SimpleLogicParseTreeNode currentChild;
		for (int i = 1; i < tokens.size(); i++) {
			Token t = tokens.get(i);
			if (t.isOfType(OPEN_BRACKET)) {
				currentChild = new SimpleLogicParseTreeNode(currentMother, t);
				currentMother = currentChild;
			} else if (t.isOfType(CLOSE_BRACKET)) {
				SimpleLogicParseTreeNode wife = currentMother;
				currentMother = currentMother.getMother();
				SimpleLogicParseTreeNode husband = new SimpleLogicParseTreeNode(currentMother, t);
				husband.marry(wife);
				currentChild = husband;
			} else {
				currentChild = new SimpleLogicParseTreeNode(currentMother, t);
			}
			output.getNodes().add(currentChild);
		}
		return output;
	}
}
