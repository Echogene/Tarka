package logic.oldtype;

import logic.Nameable;
import logic.factory.SimpleLogicLexer;
import logic.factory.SimpleLogicParser;
import logic.model.universe.Universe;
import reading.lexing.Lexer;
import reading.lexing.LexerException;
import reading.parsing.ParseTree;
import reading.parsing.ParseTreeNode;
import reading.parsing.Parser;
import reading.parsing.ParserException;
import util.MapUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Steven Weston
 */
public class AbstractTypeInferrorTest<T extends Nameable, U extends Universe<T, ?, ?>, I extends TypeInferror> {

	protected final Lexer lexer;
	protected final Parser parser;
	protected final U universe;
	protected I inferror;

	public AbstractTypeInferrorTest(U universe) {

		this.universe = universe;

		parser = new SimpleLogicParser();
		lexer = new SimpleLogicLexer();
	}

	protected ParseTree parse(String string) throws LexerException, ParserException {
		return parser.parseTokens(lexer.tokeniseString(string));
	}

	protected <T> Map<ParseTreeNode, List<T>> createMap(
			ParseTree tree,
			List<Integer> integers,
			List<List<T>> matchers
	) {

		List<ParseTreeNode> nodes = new ArrayList<>(integers.size());
		for (Integer index : integers) {
			nodes.add(tree.getNodes().get(index));
		}
		return MapUtils.createMap(nodes, matchers);
	}
}
