package logic.factory;

import reading.lexing.Token;
import reading.parsing.ParseTree;
import reading.parsing.Parser;

import java.util.List;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.CLOSE_BRACKET;
import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.OPEN_BRACKET;
import static logic.factory.SimpleLogicParseTree.Node;

/**
 * @author Steven Weston
 */
public class SimpleLogicParserImpl implements Parser {
	@Override
	public ParseTree parseTokens(List<Token> tokens) {
		SimpleLogicParseTree output = new SimpleLogicParseTree();
		Node currentParent = new Node(tokens.get(0));
		output.getNodes().add(currentParent);
		Node currentChild;
		for (int i = 1; i < tokens.size(); i++) {
			Token t = tokens.get(i);
			if (t.getType() == OPEN_BRACKET) {
				currentChild = new Node(currentParent, t);
				currentParent = currentChild;
			} else if (t.getType() == CLOSE_BRACKET) {
				Node spouse = currentParent;
				currentParent = currentParent.getParent();
				currentChild = new Node(currentParent, t);
				currentChild.marry(spouse);
			} else {
				currentChild = new Node(currentParent, t);
			}
			output.getNodes().add(currentChild);
		}
		return output;
	}
}
