package logic.factory;

import reading.lexing.Token;
import reading.parsing.ParseTree;
import reading.parsing.Parser;

import java.util.List;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.CLOSE_PAREN;
import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.OPEN_PAREN;

/**
 * @author Steven Weston
 */
public class SimpleLogicParserImpl implements Parser {
	@Override
	public ParseTree parseTokens(List<Token> tokens) {
		SimpleLogicParseTree output = new SimpleLogicParseTree();
		SimpleLogicParseTree.Node currentParent = new SimpleLogicParseTree.Node(tokens.get(0));
		output.getNodes().add(currentParent);
		SimpleLogicParseTree.Node currentChild;
		for (int i = 1; i < tokens.size(); i++) {
			Token t = tokens.get(i);
			if (t.getType() == OPEN_PAREN) {
				currentChild = new SimpleLogicParseTree.Node(currentParent, t);
				currentParent = currentChild;
			} else if (t.getType() == CLOSE_PAREN) {
				SimpleLogicParseTree.Node spouse = currentParent;
				currentParent = currentParent.getParent();
				currentChild = new SimpleLogicParseTree.Node(currentParent, t);
				currentChild.marry(spouse);
			} else {
				currentChild = new SimpleLogicParseTree.Node(currentParent, t);
			}
			output.getNodes().add(currentChild);
		}
		return output;
	}
}
