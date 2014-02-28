package logic.function.factory;

import javafx.util.Pair;
import logic.Nameable;
import logic.factory.Factory;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.function.factory.construction.FunctionConstructor;
import logic.function.factory.validation.checking.Checker;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.DisjunctiveChecker;
import logic.function.factory.validation.checking.checkers.FunctionfulChecker;
import logic.function.factory.validation.checking.checkers.NumberedChecker;
import logic.function.factory.validation.function.FunctionValidationException;
import logic.function.factory.validation.function.FunctionValidator;
import logic.function.factory.validation.function.SimpleLogicFunctionValidator;
import logic.function.factory.validation.token.SimpleLogicTokenValidator;
import logic.function.factory.validation.token.TokenValidationException;
import logic.function.factory.validation.token.TokenValidator;
import logic.function.factory.validation.token.group.TokenGroup;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.set.SetFunction;
import logic.type.TypeMatcher;
import logic.type.map.MapToErrors;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;
import util.CollectionUtils;

import java.lang.reflect.Type;
import java.util.*;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.NAME;

/**
 * @author Steven Weston
 */
public abstract class FunctionFactory<D extends Nameable, C, F extends Function<D, ?, ?>>
		implements
				TokenValidator,
				TypeMatcher,
				FunctionValidator<D>,
				FunctionConstructor<D, F>,
				Factory<F>
{

	public static final List<Pair<String,String>> STANDARD_BRACKETS = Arrays.asList(new Pair<>("(", ")"));
	protected final static List<Class> NON_VOID_FUNCTIONS = Arrays.<Class>asList(
			ReflexiveFunction.class,
			Evaluable.class,
			SetFunction.class
	);
	protected final Set<Type> nonVoidTypes;
	protected final Set<Type> allTypes;

	private final TokenValidator tokenValidator;
	private final FunctionValidator<D> functionValidator;
	private final Class<D> universeType;

	protected FunctionFactory(List<CheckerWithNumber> checkers, List<Pair<String, String>> acceptedBracketPairs, Class<D> universeType) {
		this.universeType = universeType;
		tokenValidator = new SimpleLogicTokenValidator(checkers, acceptedBracketPairs);
		functionValidator = new SimpleLogicFunctionValidator<>(extractFunctionCheckers(checkers));

		nonVoidTypes = CollectionUtils.createSet(getUniverseType(), Boolean.class, logic.set.Set.class);
		allTypes = CollectionUtils.createSet(getUniverseType(), Boolean.class, logic.set.Set.class, Void.class);
	}

	private List<CheckerWithNumber> extractFunctionCheckers(List<CheckerWithNumber> checkers) {
		ArrayList<CheckerWithNumber> output = new ArrayList<>();
		for (CheckerWithNumber checker : checkers) {
			if (isOrContainsFunctionChecker(checker)) {
				output.add(checker);
			}
		}
		return output;
	}

	private boolean isOrContainsFunctionChecker(Checker checker) {
		if (checker instanceof FunctionfulChecker) {
			return true;
		} else if (checker instanceof DisjunctiveChecker) {
			DisjunctiveChecker disjunctiveChecker = (DisjunctiveChecker) checker;
			for (Checker subChecker : disjunctiveChecker.getSubCheckers()) {
				if (isOrContainsFunctionChecker(subChecker)) {
					return true;
				}
			}
		} else if (checker instanceof NumberedChecker) {
			NumberedChecker numberedChecker = (NumberedChecker) checker;
			return isOrContainsFunctionChecker(numberedChecker.getChecker());
		}
		return false;
	}

	@Override
	public MapToErrors<TokenGroup> validateTokens(List<Token> tokens) throws TokenValidationException {
		return tokenValidator.validateTokens(tokens);
	}

	@Override
	public MapToErrors<Function<D, ?, ?>> validateFunctions(List<Function<D, ?, ?>> functions) throws FunctionValidationException {
		return functionValidator.validateFunctions(functions);
	}

	@Override
	public final F createElement(List<Token> tokens) throws FactoryException {
		return createElement(tokens, null, null);
	}

	public final F createElement(List<Token> tokens, List<Function<D, ?, ?>> functions, Map<String, Set<Type>> boundVariables) throws FactoryException {
		return construct(tokens, functions, boundVariables);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

	protected Class<D> getUniverseType() {
		return universeType;
	}

	protected List<ParseTreeNode> getSingleVariableWithIndex(List<ParseTreeNode> nodes, int index) {
		ParseTreeNode node = nodes.get(index);
		if (node.getToken().isOfType(NAME)) {
			return Arrays.asList(node);
		}
		return new ArrayList<>();
	}

	protected List<ParseTreeNode> getAllVariables(List<ParseTreeNode> nodes) {
		List<ParseTreeNode> output = new ArrayList<>();
		for (ParseTreeNode node : nodes) {
			if (node.getToken().isOfType(NAME)) {
				output.add(node);
			}
		}
		return output;
	}

	protected List<ParseTreeNode> getAllVariablesExcept(List<ParseTreeNode> nodes, Integer exception) {
		List<ParseTreeNode> output = new ArrayList<>();
		for (int i = 0; i < nodes.size(); i++) {
			if (i == exception) {
				continue;
			}
			ParseTreeNode node = nodes.get(i);
			if (node.getToken().isOfType(NAME)) {
				output.add(node);
			}
		}
		return output;
	}
}
