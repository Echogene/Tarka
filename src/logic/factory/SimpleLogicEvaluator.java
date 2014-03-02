package logic.factory;

import logic.Nameable;
import logic.function.Function;
import logic.function.factory.FunctionFactory;
import logic.function.factory.validation.function.FunctionValidationException;
import logic.function.identity.IdentityConstructorFromType;
import logic.function.voidfunction.definition.function.definedfunction.AbstractDefinedFunctionFactory;
import logic.model.universe.Universe;
import logic.type.SimpleLogicTypeInferror;
import logic.type.TypeInferrorException;
import logic.type.VariableAssignerFactory;
import logic.type.map.Checkor;
import logic.type.map.CheckorException;
import logic.type.map.MapToErrors;
import reading.evaluating.Evaluator;
import reading.evaluating.EvaluatorException;
import reading.lexing.Token;
import reading.parsing.ParseTree;
import reading.parsing.ParseTreeNode;
import util.CollectionUtils;
import util.StringUtils;
import util.TokenUtils;

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
public class SimpleLogicEvaluator<T extends Nameable> implements Evaluator<Function<T, ?, ?>> {

	private final List<FunctionFactory<T, ?, ?>> factories;
	private final IdentityConstructorFromType<T> identityConstructorFromType;
	private final Universe<T, ?, ?> universe;
	private SimpleLogicTypeInferror<T> typeInferror;

	private final Map<String, AbstractDefinedFunctionFactory<T, ?, ?, ?>> definedFunctions = new HashMap<>();

	public SimpleLogicEvaluator(List<FunctionFactory<T, ?, ?>> factories, Universe<T, ?, ?> universe)
			throws EvaluatorException {

		this.factories = new ArrayList<>(factories);
		this.universe = universe;

		identityConstructorFromType = getIdentityConstructorFromType(factories);
	}

	private IdentityConstructorFromType<T> getIdentityConstructorFromType(List<FunctionFactory<T, ?, ?>> factories)
			throws EvaluatorException {

		for (FunctionFactory<T, ?, ?> factory : factories) {
			if (factory instanceof IdentityConstructorFromType) {
				return (IdentityConstructorFromType<T>) factory;
			}
		}
		throw new EvaluatorException("No identity constructor was specified");
	}

	@Override
	public Function<T, ?, ?> evaluate(ParseTree tree)
			throws EvaluatorException, TypeInferrorException {

		if (tree == null) {
			return null;
		}

		typeInferror = new SimpleLogicTypeInferror<>(universe, tree);

		final List<ParseTreeNode> firstChildren = tree.getFirstNode().getChildren();

		// Validate tokens recursively
		final Map<ParseTreeNode, List<FunctionFactory<T, ?, ?>>> passedFactories = new HashMap<>();
		final Map<ParseTreeNode, List<VariableAssignerFactory>> passedAssigners = new HashMap<>();
		recursivelyValidateTokens(firstChildren, passedFactories, passedAssigners);

		final Map<ParseTreeNode, Set<Type>> types = typeInferror.inferTypes(passedFactories, passedAssigners);

		// Create functions for the typed nodes
		final Map<ParseTreeNode, Function<T, ?, ?>> identityFunctions
				= createIdentityFunctionsForVariables(firstChildren, passedFactories, types);

		// Validate and construct functions recursively and return the function
		return evaluate(firstChildren, identityFunctions, passedFactories);
	}

	private void recursivelyValidateTokens(
			List<ParseTreeNode> firstChildren,
			Map<ParseTreeNode, List<FunctionFactory<T, ?, ?>>> passedFactories,
			Map<ParseTreeNode, List<VariableAssignerFactory>> passedAssigners
	) throws EvaluatorException {

		headRecurse(firstChildren, children -> {
			List<FunctionFactory<T, ?, ?>> passedFactoriesForNode = validateTokens(extractTokens(children));
			ParseTreeNode key = first(children).getMother();
			passedAssigners.put(key, CollectionUtils.filterList(passedFactoriesForNode, VariableAssignerFactory.class));
			passedFactories.put(key, passedFactoriesForNode);
		});
	}

	private Map<ParseTreeNode, Function<T, ?, ?>> createIdentityFunctionsForVariables(
			List<ParseTreeNode> firstChildren, Map<ParseTreeNode,
			List<FunctionFactory<T, ?, ?>>> passedFactories,
			Map<ParseTreeNode, Set<Type>> types
	) throws EvaluatorException {

		final Map<ParseTreeNode, Function<T, ?, ?>> identityFunctions = new HashMap<>();
		headRecurse(firstChildren, children -> {
			ParseTreeNode mother = children.get(0).getMother();
			List<FunctionFactory<T, ?, ?>> passedFactoriesForNode = passedFactories.get(mother);
			MapToErrors<FunctionFactory<T, ?, ?>> errors = new MapToErrors<>(
					passedFactoriesForNode,
					createIdentityFunctions(types, identityFunctions, children)
			);
			if (errors.allFailed()) {
				throw new EvaluatorException(errors.concatenateErrorMessages());
			}
			passedFactories.get(mother).retainAll(errors.getPassedKeys());
		});
		return identityFunctions;
	}

	private Checkor<FunctionFactory<T, ?, ?>> createIdentityFunctions(
			Map<ParseTreeNode, Set<Type>> types,
			Map<ParseTreeNode, Function<T, ?, ?>> identityFunctions,
			List<ParseTreeNode> children
	) throws EvaluatorException {

		return factory -> {
			List<ParseTreeNode> variables = factory.getVariables(surroundWithParentNodes(children));
			for (ParseTreeNode variable : variables) {
				createIdentityFunctionForVariable(types, identityFunctions, variable);
			}
		};
	}

	private void createIdentityFunctionForVariable(
			Map<ParseTreeNode, Set<Type>> types,
			Map<ParseTreeNode, Function<T, ?, ?>> identityFunctions,
			ParseTreeNode variable
	) throws EvaluatorException {

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

	/**
	 * @param tokens A list of tokens to validate against all factories
	 * @return A list of factories for which the given tokens were valid
	 * @throws EvaluatorException
	 */
	private List<FunctionFactory<T, ?, ?>> validateTokens(List<Token> tokens) throws EvaluatorException {
		return validate(
				factories,
				tokens,
				(validator, validatee) -> validator.validateTokens(validatee),
				TokenUtils::toString
		);
	}

	private List<FunctionFactory<T, ?, ?>> validateFunctions(
			List<FunctionFactory<T, ?, ?>> factories,
			List<Function<T, ?, ?>> functions
	) throws EvaluatorException {

		return validate(
				factories,
				functions,
				(validator, validatee) -> validator.validateFunctions(validatee),
				CollectionUtils::toString
		);
	}

	private <V, W, R> List<V> validate(
			List<V> keys,
			W values,
			Validator<V, W, R> validator,
			java.util.function.Function<W, String> stringifier
	) throws EvaluatorException {

		MapToErrors<V> mapToErrors = new MapToErrors<>(
				keys,
				key -> {
					MapToErrors<R> mapToErrors2 = validator.validate(key, values);
					if (!mapToErrors2.allPassed()) {
						// todo: this shouldn't be function validation exception
						throw new FunctionValidationException(mapToErrors2.concatenateErrorMessages());
					}
				}
		);
		if (mapToErrors.allFailed()) {
			throw new EvaluatorException(
					MessageFormat.format(
							"Validation failed for {0} because:\n{1}",
							stringifier.apply(values),
							StringUtils.addCharacterAfterEveryNewline(mapToErrors.concatenateErrorMessages(), '\t')
					)
			);
		}
		return new ArrayList<>(mapToErrors.getPassedKeys());
	}

	public void addFactory(FunctionFactory<T, ?, ?> factory) {
		factories.add(factory);
		if (factory instanceof AbstractDefinedFunctionFactory) {
			AbstractDefinedFunctionFactory<T, ?, ?, ?> definedFunctionFactory
					= (AbstractDefinedFunctionFactory<T, ?, ?, ?>) factory;
			definedFunctions.put(definedFunctionFactory.getFunctionSymbol(), definedFunctionFactory);
		}
	}

	private interface Validator<V, W, R> {
		MapToErrors<R> validate(V validator, W validatee) throws CheckorException;
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

	private Function<T, ?, ?> evaluate(
			List<ParseTreeNode> nodes,
			Map<ParseTreeNode, Function<T, ?, ?>> identityFunctions,
			Map<ParseTreeNode, List<FunctionFactory<T, ?, ?>>> passedFactoriesMap
	) throws EvaluatorException {

		final List<Function<T, ?, ?>> functions = new ArrayList<>();
		for (ParseTreeNode n : nodes) {
			if (shouldWalkDownAt(n)) {
				functions.add(evaluate(n.getChildren(), identityFunctions, passedFactoriesMap));
			} else if (identityFunctions.containsKey(n)) {
				functions.add(identityFunctions.get(n));
			}
		}

		ParseTreeNode mother = first(nodes).getMother();
		FunctionFactory<T, ?, ?> factory = validateSingleFactoryLeft(passedFactoriesMap, functions, mother);

		List<Token> tokens = extractTokens(nodes);
		Map<String, Set<Type>> boundVariables = typeInferror.getBoundVariableMap().get(mother);
		try {
			return factory.createElement(tokens, functions, boundVariables);
		} catch (FactoryException e) {
			throw new EvaluatorException(
					MessageFormat.format(
							"Something went wrong when creating the function for {0} because:\n{1}",
							tokens.toString(),
							StringUtils.addCharacterAfterEveryNewline(e.getMessage(), '\t')
					)
			);
		}
	}

	private FunctionFactory<T, ?, ?> validateSingleFactoryLeft(
			Map<ParseTreeNode, List<FunctionFactory<T, ?, ?>>> passedFactoriesMap,
			List<Function<T, ?, ?>> functions,
			ParseTreeNode mother
	) throws EvaluatorException {

		List<FunctionFactory<T, ?, ?>> passedFactories = passedFactoriesMap.get(mother);
		List<FunctionFactory<T, ?, ?>> remainingFactories = validateFunctions(passedFactories, functions);
		if (remainingFactories.size() > 1) {
			throw new EvaluatorException(
					MessageFormat.format(
							"The functions {0} were ambiguous.  The factories {1} remain.",
							functions,
							CollectionUtils.toString(remainingFactories)
					)
			);
		}

		return first(remainingFactories);
	}

	public AbstractDefinedFunctionFactory<T, ?, ?, ?> getDefinedFunctionFactory(String functionSymbol) {
		return definedFunctions.get(functionSymbol);
	}

	private boolean shouldWalkDownAt(ParseTreeNode n) {
		return n.getToken().isOfType(OPEN_BRACKET);
	}
}
