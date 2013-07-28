package logic.factory;

import reading.lexing.Token;
import reading.parsing.ParseTree;
import reading.parsing.Parser;

import java.util.List;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.CLOSE_BRACKET;
import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.OPEN_BRACKET;

/**
 * @author Steven Weston
 */
public class SimpleLogicParserImpl implements Parser {
	@Override
	public ParseTree parseTokens(List<Token> tokens) {
		SimpleLogicParseTree output = new SimpleLogicParseTree();
		SimpleLogicParseTreeNode currentMother = new SimpleLogicParseTreeNode(tokens.get(0));
		output.getNodes().add(currentMother);
		SimpleLogicParseTreeNode currentChild;
		for (int i = 1; i < tokens.size(); i++) {
			Token t = tokens.get(i);
			if (t.getType() == OPEN_BRACKET) {
				currentChild = new SimpleLogicParseTreeNode(currentMother, t);
				currentMother = currentChild;
			} else if (t.getType() == CLOSE_BRACKET) {
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
