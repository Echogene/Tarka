package logic.factory;

import logic.Nameable;
import logic.function.Function;
import logic.function.factory.FunctionFactory;
import logic.function.factory.validation.function.FunctionValidationException;
import logic.function.identity.IdentityConstructorFromType;
import logic.model.universe.Universe;
import logic.type.SimpleLogicTypeInferror;
import logic.type.TypeInferrorException;
import logic.type.VariableAssignerFactory;
import logic.type.map.CheckorException;
import logic.type.map.MapToErrors;
import reading.evaluating.Evaluator;
import reading.evaluating.EvaluatorException;
import reading.lexing.Token;
import reading.parsing.ParseTree;
import reading.parsing.ParseTreeNode;
import util.CollectionUtils;
import util.StringUtils;

import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.*;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.OPEN_BRACKET;
import static util.CollectionUtils.first;
import static util.TreeUtils.extractTokens;
import static util.TreeUtils.surroundWithParentNodes;

/**
 * @author Steven Weston
 */
public class SimpleLogicEvaluator<T extends Nameable> implements Evaluator<Function<T, ?>> {

	private final List<FunctionFactory<T, ?, ?>> factories;
	private final SimpleLogicTypeInferror<T> typeInferror;
	private final IdentityConstructorFromType<T> identityConstructorFromType;

	public SimpleLogicEvaluator(List<FunctionFactory<T, ?, ?>> factories, Universe<T> universe) {
		this.factories = new ArrayList<>(factories);

		identityConstructorFromType = getIdentityConstructorFromType(factories);
		typeInferror = new SimpleLogicTypeInferror<>(universe);
	}

	private IdentityConstructorFromType<T> getIdentityConstructorFromType(List<FunctionFactory<T, ?, ?>> factories) {
		for (FunctionFactory<T, ?, ?> factory : factories) {
			if (factory instanceof IdentityConstructorFromType) {
				return (IdentityConstructorFromType<T>) factory;
			}
		}
		return null;
	}

	@Override
	public Function<T, ?> evaluate(ParseTree tree) throws EvaluatorException, TypeInferrorException {
		if (tree == null) {
			return null;
		}

		final List<ParseTreeNode> firstChildren = tree.getFirstNode().getChildren();

		// Validate tokens recursively
		final Map<ParseTreeNode, List<FunctionFactory<T, ?, ?>>> passedFactories = new HashMap<>();
		final Map<ParseTreeNode, List<VariableAssignerFactory>> passedAssigners = new HashMap<>();
		headRecurse(firstChildren, children -> {
			List<FunctionFactory<T, ?, ?>> passedFactoriesForNode = validateTokens(extractTokens(children));
			ParseTreeNode key = first(children).getMother();
			passedAssigners.put(key, CollectionUtils.filterList(passedFactoriesForNode, VariableAssignerFactory.class));
			passedFactories.put(key, passedFactoriesForNode);
		});

		final Map<ParseTreeNode, Set<Type>> types = typeInferror.inferTypes(tree, passedFactories, passedAssigners);

		// Create functions for the typed nodes
		final Map<ParseTreeNode, Function<T, ?>> identityFunctions = createIdentityFunctionsForVariables(firstChildren, passedFactories, types);

		// Validate and construct functions recursively and return the function
		return evaluate(firstChildren, identityFunctions, passedFactories);
	}

	private Map<ParseTreeNode, Function<T, ?>> createIdentityFunctionsForVariables(List<ParseTreeNode> firstChildren, Map<ParseTreeNode, List<FunctionFactory<T, ?, ?>>> passedFactories, Map<ParseTreeNode, Set<Type>> types) throws EvaluatorException {
		final Map<ParseTreeNode, Function<T, ?>> identityFunctions = new HashMap<>();
		headRecurse(firstChildren, children -> {
			List<FunctionFactory<T, ?, ?>> passedFactoriesForNode = passedFactories.get(children.get(0).getMother());
			for (FunctionFactory<T, ?, ?> factory : passedFactoriesForNode) {
				List<ParseTreeNode> variables = factory.getVariables(surroundWithParentNodes(children));
				for (ParseTreeNode variable : variables) {
					if (!types.containsKey(variable) || types.get(variable) == null) {
						throw new EvaluatorException(
								MessageFormat.format(
										"{0} had no type bound to it after type inference.",
										variable.getToken().getValue()
								)
						);
					}
					identityFunctions.put(
							variable,
							identityConstructorFromType.create(
									variable.getToken().getValue(),
									types.get(variable)
							)
					);
				}
			}
		});
		return identityFunctions;
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
			List<Function<T, ?>> functions
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
					+ StringUtils.addCharacterAfterEveryNewline(mapToErrors.concatenateErrorMessages(), '\t')
			);
		}
		return new ArrayList<>(mapToErrors.getPassedKeys());
	}

	public void addFactory(FunctionFactory<T, ?, ?> factory) {
		factories.add(factory);
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

		final List<Function<T, ?>> functions = new ArrayList<>();
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

		List<Token> tokens = extractTokens(nodes);
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
