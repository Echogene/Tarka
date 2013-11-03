package logic.factory;

import logic.function.Function;
import logic.function.factory.FunctionFactory;
import reading.evaluating.Evaluator;
import reading.evaluating.EvaluatorException;
import reading.lexing.Token;
import reading.parsing.ParseTree;
import reading.parsing.ParseTreeNode;
import util.TreeUtils;

import java.util.ArrayList;
import java.util.List;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.OPEN_BRACKET;
import static util.StringUtils.join;

/**
 * @author Steven Weston
 */
public class SimpleLogicEvaluator implements Evaluator<Function<?, ?>> {
	protected List<FunctionFactory<?, ?, ?>> factories;
	private static final boolean LOG_FACTORY_FAILURES = false;

	public SimpleLogicEvaluator(List<FunctionFactory<?, ?, ?>> factories) {
		this.factories = factories;
	}

	@Override
	public Function<?, ?> evaluate(ParseTree tree) throws EvaluatorException {
		if (tree == null) {
			return null;
		}
		return evaluate(tree.getNodes().get(0).getChildren());
	}

	Function<?, ?> evaluate(List<ParseTreeNode> nodes) throws EvaluatorException {
		List<Function<?, ?>> functions = new ArrayList<>();
		for (ParseTreeNode n : nodes) {
			if (n.getToken().isOfType(OPEN_BRACKET)) {
				functions.add(evaluate(n.getChildren()));
			}
		}
		List<Token> tokens = TreeUtils.extractTokens(nodes);
		List<String> errorMessages = new ArrayList<>();
		for (FunctionFactory<?, ?, ?> factory : factories) {
			try {
				return factory.createElement(tokens, functions);
			} catch (FactoryException e) {
				String error = factory.getClass().getSimpleName() + " did not work: " + e.getMessage();
				errorMessages.add(error);
				if (LOG_FACTORY_FAILURES) {
					System.out.println(error);
				}
			}
		}
		throw new EvaluatorException(join(errorMessages, "\n"));
	}
}
