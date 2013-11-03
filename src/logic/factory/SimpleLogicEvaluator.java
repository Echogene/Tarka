package logic.factory;

import logic.Nameable;
import logic.function.Function;
import logic.function.factory.FunctionFactory;
import logic.model.universe.Universe;
import logic.type.SimpleLogicTypeInferror;
import logic.type.TypeMatcher;
import logic.type.VariableAssignerFactory;
import reading.evaluating.Evaluator;
import reading.evaluating.EvaluatorException;
import reading.lexing.Token;
import reading.parsing.ParseTree;
import reading.parsing.ParseTreeNode;
import util.TreeUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.OPEN_BRACKET;
import static util.StringUtils.join;

/**
 * @author Steven Weston
 */
public class SimpleLogicEvaluator<T extends Nameable> implements Evaluator<Function<T, ?>> {

	private static final boolean LOG_FACTORY_FAILURES = false;

	private final List<FunctionFactory<T, ?, ?>> factories;
	private final SimpleLogicTypeInferror<T> typeInferror;

	public SimpleLogicEvaluator(List<FunctionFactory<T, ?, ?>> factories, Universe<T> universe) {
		this.factories = factories;

		Collection<VariableAssignerFactory> variableAssignerFactories = new HashSet<>();
		Collection<TypeMatcher> matchers = new HashSet<>();
		for (FunctionFactory<T, ?, ?> factory : factories) {
			if (factory instanceof VariableAssignerFactory) {
				variableAssignerFactories.add((VariableAssignerFactory) factory);
			}
			if (factory instanceof TypeMatcher) {
				matchers.add((TypeMatcher) factory);
			}
		}
		typeInferror = new SimpleLogicTypeInferror<>(matchers, variableAssignerFactories, universe);
	}

	@Override
	public Function<T, ?> evaluate(ParseTree tree) throws EvaluatorException {
		if (tree == null) {
			return null;
		}
		return evaluate(tree.getFirstNode().getChildren());
	}

	private Function<T, ?> evaluate(List<ParseTreeNode> nodes) throws EvaluatorException {
		List<Function<?, ?>> functions = new ArrayList<>();
		for (ParseTreeNode n : nodes) {
			if (n.getToken().isOfType(OPEN_BRACKET)) {
				functions.add(evaluate(n.getChildren()));
			}
		}
		List<Token> tokens = TreeUtils.extractTokens(nodes);
		List<String> errorMessages = new ArrayList<>();
		for (FunctionFactory<T, ?, ?> factory : factories) {
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
