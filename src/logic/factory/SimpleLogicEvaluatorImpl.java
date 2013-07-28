package logic.factory;

import logic.function.Function;
import logic.function.factory.FunctionFactory;
import reading.evaluating.Evaluator;
import reading.evaluating.EvaluatorException;
import reading.lexing.Token;
import reading.parsing.ParseTree;
import reading.parsing.ParseTreeNode;

import java.util.ArrayList;
import java.util.List;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.NAME;
import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.OPEN_BRACKET;

/**
 * @author Steven Weston
 */
public class SimpleLogicEvaluatorImpl implements Evaluator<Function<?, ?>> {
	protected List<FunctionFactory<?, ?>> factories;
	private static final boolean LOG_FACTORY_FAILURES = false;

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
			if (n.getToken().getType() == OPEN_BRACKET) {
				functions.add(evaluate(n.getChildren()));
			} else if (n.getToken().getType() == NAME) {
				functions.add(null);
			}
		}
		for (FunctionFactory<?, ?> factory : factories) {
			try {
				return factory.createElement(tokens, functions);
			} catch (FactoryException e) {
				if (LOG_FACTORY_FAILURES) {
					System.out.println(factory.getClass().toString() + " did not work : " + e.getMessage());
				}
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
