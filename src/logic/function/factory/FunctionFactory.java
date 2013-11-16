package logic.function.factory;

import javafx.util.Pair;
import logic.Nameable;
import logic.factory.Factory;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.construction.FunctionConstructor;
import logic.function.factory.validation.checking.Checker;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.DisjunctiveChecker;
import logic.function.factory.validation.checking.checkers.FunctionfulChecker;
import logic.function.factory.validation.function.FunctionValidationException;
import logic.function.factory.validation.function.FunctionValidator;
import logic.function.factory.validation.function.SimpleLogicFunctionValidator;
import logic.function.factory.validation.token.SimpleLogicTokenValidator;
import logic.function.factory.validation.token.TokenValidationException;
import logic.function.factory.validation.token.TokenValidator;
import logic.function.factory.validation.token.group.TokenGroup;
import logic.type.TypeMatcher;
import logic.type.map.MapToErrors;
import reading.lexing.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Steven Weston
 */
public abstract class FunctionFactory<D extends Nameable, C, F extends Function<D, ?>>
		implements
				TokenValidator,
				TypeMatcher,
				FunctionValidator,
				FunctionConstructor<F>,
				Factory<F>
{

	private final TokenValidator tokenValidator;
	private final FunctionValidator functionValidator;

	protected FunctionFactory(List<CheckerWithNumber> checkers, List<Pair<String, String>> acceptedBracketPairs) {
		tokenValidator = new SimpleLogicTokenValidator(checkers, acceptedBracketPairs);
		functionValidator = new SimpleLogicFunctionValidator(extractFunctionCheckers(checkers));
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
		}
		return false;
	}

	@Override
	public MapToErrors<TokenGroup> validateTokens(List<Token> tokens) throws TokenValidationException {
		return tokenValidator.validateTokens(tokens);
	}

	@Override
	public MapToErrors<Function<?, ?>> validateFunctions(List<Function<?, ?>> functions) throws FunctionValidationException {
		return functionValidator.validateFunctions(functions);
	}

	@Override
	public final F createElement(List<Token> tokens) throws FactoryException {
		return createElement(tokens, null);
	}

	public final F createElement(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		return construct(tokens, functions);
	}
}
