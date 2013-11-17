package logic.factory;

import logic.Nameable;
import logic.function.Function;
import logic.function.factory.FunctionFactory;
import logic.function.factory.validation.function.FunctionValidationException;
import logic.identity.IdentityConstructorFromType;
import logic.model.universe.Universe;
import logic.type.SimpleLogicTypeInferror;
import logic.type.TypeInferrorException;
import logic.type.TypeMatcher;
import logic.type.VariableAssignerFactory;
import logic.type.map.CheckorException;
import logic.type.map.MapToErrors;
import reading.evaluating.Evaluator;
import reading.evaluating.EvaluatorException;
import reading.lexing.Token;
import reading.parsing.ParseTree;
import reading.parsing.ParseTreeNode;
import util.StringUtils;
import util.TreeUtils;

import java.lang.reflect.Type;
import java.util.*;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.OPEN_BRACKET;
import static util.CollectionUtils.first;

/**
 * @author Steven Weston
 */
public class SimpleLogicEvaluator<T extends Nameable> implements Evaluator<Function<T, ?>> {

	private final List<FunctionFactory<T, ?, ?>> factories;
	private final SimpleLogicTypeInferror<T> typeInferror;
	private final IdentityConstructorFromType<T> identityConstructorFromType;

	public SimpleLogicEvaluator(List<FunctionFactory<T, ?, ?>> factories, Universe<T> universe) {
		this.factories = factories;

		Collection<VariableAssignerFactory> variableAssignerFactories = new HashSet<>();
		Collection<TypeMatcher> matchers = new HashSet<>();
		for (FunctionFactory<T, ?, ?> factory : factories) {
			if (factory instanceof VariableAssignerFactory) {
				variableAssignerFactories.add((VariableAssignerFactory) factory);
			}
			if (factory != null) {
				matchers.add(factory);
			}
		}
		typeInferror = new SimpleLogicTypeInferror<>(matchers, variableAssignerFactories, universe);
		identityConstructorFromType = null; //todo
	}

	@Override
	public Function<T, ?> evaluate(ParseTree tree) throws EvaluatorException, TypeInferrorException {
		if (tree == null) {
			return null;
		}

		final List<ParseTreeNode> firstChildren = tree.getFirstNode().getChildren();

		// Validate tokens recursively
		final Map<ParseTreeNode, List<FunctionFactory<T, ?, ?>>> passedFactories = new HashMap<>();
		headRecurse(firstChildren, children -> {
			passedFactories.put(first(children).getMother(), validateTokens(TreeUtils.extractTokens(children)));
		});

		final Map<ParseTreeNode, Type> types = typeInferror.inferTypes(tree);

		// Create functions for the typed nodes
		final Map<ParseTreeNode, Function<T, ?>> identityFunctions = new HashMap<>();
		for (ParseTreeNode node : tree.getNodes()) {
			if (types.containsKey(node)) {
				identityFunctions.put(
						node,
						identityConstructorFromType.create(
								node.getToken().getValue(),
								types.get(node)
						)
				);
			}
		}

		// Validate and construct functions recursively and return the function
		return evaluate(firstChildren, identityFunctions, passedFactories);
	}

	/**
	 * Given some tokens, find out the list of factories for which the tokens were valid.
	 * @param tokens
	 * @return
	 * @throws EvaluatorException
	 */
	private List<FunctionFactory<T, ?, ?>> validateTokens(List<Token> tokens) throws EvaluatorException {
		return validate(factories, (functionFactory) -> functionFactory.validateTokens(tokens));
	}

	private List<FunctionFactory<T, ?, ?>> validateFunctions(
			List<FunctionFactory<T, ?, ?>> factories,
			List<Function<?, ?>> functions
	) throws EvaluatorException {

		return validate(factories, (functionFactory) -> functionFactory.validateFunctions(functions));
	}

	private <K, V> List<K> validate(List<K> keys, Validator<K, V> validator) throws EvaluatorException {
		MapToErrors<K> mapToErrors = new MapToErrors<>(
				keys,
				key -> {
					MapToErrors<V> mapToErrors2 = validator.validate(key);
					if (!mapToErrors2.allPassed()) {
						throw new FunctionValidationException(mapToErrors2.concatenateErrorMessages());
					}
				}
		);
		if (mapToErrors.allFailed()) {
			throw new EvaluatorException(
					"Validation failed because:\n"
					+ StringUtils.addCharacterAfterEveryNewline(mapToErrors.concatenateErrorMessages(), '\n')
			);
		}
		return new ArrayList<>(mapToErrors.getPassedKeys());
	}

	private interface Validator<K, V> {
		MapToErrors<V> validate(K k) throws CheckorException;
	}

	private void headRecurse(List<ParseTreeNode> nodes, NodeProcessor nodeProcessor) throws EvaluatorException {
		for (ParseTreeNode n : nodes) {
			if (shouldWalkDownAt(n)) {
				headRecurse(n.getChildren(), nodeProcessor);
			}
		}
		nodeProcessor.process(nodes);
	}

	private interface NodeProcessor {
		void process(List<ParseTreeNode> nodes) throws EvaluatorException;
	}

	private Function<T, ?> evaluate(
			List<ParseTreeNode> nodes,
			Map<ParseTreeNode, Function<T, ?>> identityFunctions,
			Map<ParseTreeNode, List<FunctionFactory<T, ?, ?>>> passedFactoriesMap
	) throws EvaluatorException {

		final List<Function<?, ?>> functions = new ArrayList<>();
		for (ParseTreeNode n : nodes) {
			if (shouldWalkDownAt(n)) {
				functions.add(evaluate(n.getChildren(), identityFunctions, passedFactoriesMap));
			} else if (identityFunctions.containsKey(n)) {
				functions.add(identityFunctions.get(n));
			}
		}

		List<FunctionFactory<T, ?, ?>> passedFactories = passedFactoriesMap.get(first(nodes).getMother());
		List<FunctionFactory<T, ?, ?>> remainingFactories = validateFunctions(passedFactories, functions);
		if (remainingFactories.size() > 1) {
			throw new EvaluatorException("The functions " + functions.toString() + " were ambiguous.");
		}

		FunctionFactory<T, ?, ?> factory = first(remainingFactories);

		List<Token> tokens = TreeUtils.extractTokens(nodes);
		try {
			return factory.createElement(tokens, functions);
		} catch (FactoryException e) {
			throw new EvaluatorException("Something went wrong when creating the function for "
					+ tokens.toString()
					+ " because:\n"
					+ StringUtils.addCharacterAfterEveryNewline(e.getMessage(), '\t')
			);
		}
	}

	private boolean shouldWalkDownAt(ParseTreeNode n) {
		return n.getToken().isOfType(OPEN_BRACKET);
	}
}
