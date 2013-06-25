package logic.factory;

import logic.function.Function;
import logic.function.FunctionFactory;
import reading.evaluating.Evaluator;
import reading.evaluating.EvaluatorException;
import reading.lexing.Token;
import reading.parsing.ParseTree;
import reading.parsing.ParseTreeNode;

import java.util.ArrayList;
import java.util.List;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.NAME;
import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.OPEN_PAREN;

/**
 * @author Steven Weston
 */
public class SimpleLogicEvaluatorImpl implements Evaluator<Function<?, ?>> {
	protected List<FunctionFactory<?, ?>> factories;

	public SimpleLogicEvaluatorImpl(List<FunctionFactory<?, ?>> factories) {
		this.factories = factories;
	}

	@Override
	public Function<?, ?> evaluate(ParseTree tree) throws EvaluatorException {
		return evaluate(tree.getNodes().get(0).getChildren());
	}

	Function<?, ?> evaluate(List<ParseTreeNode> nodes) throws EvaluatorException {
		List<Token> tokens = extractTokens(nodes);
		List<Function<?, ?>> functions = new ArrayList<>();
		for (ParseTreeNode n : nodes) {
			if (n.getToken().getType() == OPEN_PAREN) {
				functions.add(evaluate(n.getChildren()));
			} else if (n.getToken().getType() == NAME) {
				functions.add(null);
			}
		}
		for (FunctionFactory<?, ?> factory : factories) {
			try {
				return factory.createElement(tokens, functions);
			} catch (FactoryException e) {
			}
		}
		throw new EvaluatorException();
	}

	List<Token> extractTokens(List<ParseTreeNode> nodes) {
		List<Token> output = new ArrayList<>(nodes.size());
		for (ParseTreeNode n : nodes) {
			output.add(n.getToken());
		}
		return output;
	}
}
