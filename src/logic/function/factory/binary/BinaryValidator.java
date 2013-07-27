package logic.function.factory.binary;

import logic.function.Function;
import logic.function.factory.FunctionFactoryInputValidator;
import logic.function.factory.ValidationException;
import logic.function.factory.ValidationResult;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexiveset.ReflexiveSetFunction;
import reading.lexing.Token;

import java.util.Arrays;
import java.util.List;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.*;
import static logic.function.factory.ValidationResult.ValidationType.FUNCTION;
import static logic.function.factory.ValidationResult.ValidationType.TOKEN;
import static logic.function.factory.ValidationResult.invalid;

/**
 * @author Steven Weston
 */
public class BinaryValidator implements FunctionFactoryInputValidator {
	private final List<String> acceptedOperatorSymbols;
	private Class<?> firstClass;
	private Class<?> secondClass;

	public BinaryValidator(Class firstClass, List<String> acceptedOperatorSymbols, Class secondClass) {
		this.acceptedOperatorSymbols = acceptedOperatorSymbols;
		this.firstClass = firstClass;
		this.secondClass = secondClass;
	}

	public BinaryValidator(List<String> acceptedOperatorSymbols) {
		this(ReflexiveFunction.class, acceptedOperatorSymbols, ReflexiveFunction.class);
	}

	@Override
	public ValidationResult validate(List<Token> tokens, List<Function<?, ?>> functions) {
		try {
			return validateWithException(tokens, functions);
		} catch (ValidationException e) {
			return invalid(e.getMessage());
		}
	}

	private ValidationResult validateWithException(List<Token> tokens, List<Function<?, ?>> functions) throws ValidationException {
		validateAtLeastThreeTokens(tokens);
		// todo: make the match functions return a validation error?
		if (matchesNoFunctions(tokens, functions)) {
			return new ValidationResult(Arrays.asList(TOKEN, TOKEN));
		} else if (matchesFirstFunction(tokens, functions)) {
			return new ValidationResult(Arrays.asList(FUNCTION, TOKEN));
		} else if (matchesSecondFunction(tokens, functions)) {
			return new ValidationResult(Arrays.asList(TOKEN, FUNCTION));
		} else if (matchesBothFunctions(tokens, functions)) {
			return new ValidationResult(Arrays.asList(FUNCTION, FUNCTION));
		} else {
			throw new ValidationException("The tokens and function should match.");
		}
	}

	private void validateAtLeastThreeTokens(List<Token> tokens) throws ValidationException {
		if (tokens == null || tokens.size() < 3) {
			throw new ValidationException("There must be at least three tokens.");
		}
	}

	/**
	 * Return whether the given {@code Token}s and {@code Function}s match the pattern formed by two name tokens
	 * (i.e. of the form "x = y").
	 * @param tokens A list of {@code Token}s to match against.
	 * @param functions A list of {@code Function}s to match against.
	 * @return Whether there are two name tokens and no functions.
	 */
	private boolean matchesNoFunctions(List<Token> tokens, List<Function<?, ?>> functions) {
		return tokens.size() == 3
				&& tokens.get(0).isOfType(NAME)
				&& tokens.get(1).isOfType(OPERATOR)
				&& acceptedOperatorSymbols.contains(tokens.get(1).getValue())
				&& tokens.get(2).isOfType(NAME)
				&& (functions == null
					|| functions.size() == 0
					|| (functions.size() == 2
						&& functions.get(0) == null
						&& functions.get(1) == null
					)
				);
	}

	/**
	 * Return whether the given {@code Token}s and {@code Function}s match the pattern formed by one function and one
	 * name token (i.e. of the form "(f) = y").
	 * @param tokens A list of {@code Token}s to match against.
	 * @param functions A list of {@code Function}s to match against.
	 * @return Whether there is one function and one name token.
	 */
	private boolean matchesFirstFunction(List<Token> tokens, List<Function<?, ?>> functions) {
		boolean firstClassNeedsNeedsRoundBrackets = false;
		if (!ReflexiveSetFunction.class.isAssignableFrom(firstClass)) {
			firstClassNeedsNeedsRoundBrackets = true;
		}
		return tokens.size() == 4
				&& tokens.get(0).isOfType(OPEN_BRACKET)
				&& (!firstClassNeedsNeedsRoundBrackets
					|| isTokenOpenParenthesis(tokens.get(0)))
				&& tokens.get(1).isOfType(CLOSE_BRACKET)
				&& (!firstClassNeedsNeedsRoundBrackets
					|| isTokenCloseParenthesis(tokens.get(1)))
				&& tokens.get(2).isOfType(OPERATOR)
				&& acceptedOperatorSymbols.contains(tokens.get(2).getValue())
				&& tokens.get(3).isOfType(NAME)
				&& functions != null
				&& functions.size() == 2
				&& functions.get(0) != null
				&& firstClass.isInstance(functions.get(0))
				&& functions.get(1) == null;
	}

	/**
	 * Return whether the given {@code Token}s and {@code Function}s match the pattern formed by one name token and one
	 * function (i.e. of the form "x = (g)").
	 * @param tokens A list of {@code Token}s to match against.
	 * @param functions A list of {@code Function}s to match against.
	 * @return Whether there is one name token and one function
	 */
	private boolean matchesSecondFunction(List<Token> tokens, List<Function<?, ?>> functions) {
		boolean secondClassNeedsNeedsRoundBrackets = false;
		if (!ReflexiveSetFunction.class.isAssignableFrom(secondClass)) {
			secondClassNeedsNeedsRoundBrackets = true;
		}
		return tokens.size() == 4
				&& tokens.get(0).isOfType(NAME)
				&& tokens.get(1).isOfType(OPERATOR)
				&& acceptedOperatorSymbols.contains(tokens.get(1).getValue())
				&& tokens.get(2).isOfType(OPEN_BRACKET)
				&& (!secondClassNeedsNeedsRoundBrackets
					|| isTokenOpenParenthesis(tokens.get(2)))
				&& tokens.get(3).isOfType(CLOSE_BRACKET)
				&& (!secondClassNeedsNeedsRoundBrackets
					|| isTokenCloseParenthesis(tokens.get(3)))
				&& functions != null
				&& functions.size() == 2
				&& functions.get(0) == null
				&& functions.get(1) != null
				&& secondClass.isInstance(functions.get(1));
	}

	/**
	 * Return whether the given {@code Token}s and {@code Function}s match the pattern formed by two functions
	 * (i.e. of the form "(f) = (g)").
	 * @param tokens A list of {@code Token}s to match against.
	 * @param functions A list of {@code Function}s to match against.
	 * @return Whether there are two functions and no name tokens.
	 */
	private boolean matchesBothFunctions(List<Token> tokens, List<Function<?, ?>> functions) {
		boolean firstClassNeedsNeedsRoundBrackets = false;
		if (!ReflexiveSetFunction.class.isAssignableFrom(firstClass)) {
			firstClassNeedsNeedsRoundBrackets = true;
		}
		boolean secondClassNeedsNeedsRoundBrackets = false;
		if (!ReflexiveSetFunction.class.isAssignableFrom(secondClass)) {
			secondClassNeedsNeedsRoundBrackets = true;
		}
		return tokens.size() == 5
				&& tokens.get(0).isOfType(OPEN_BRACKET)
				&& (!firstClassNeedsNeedsRoundBrackets
					|| isTokenOpenParenthesis(tokens.get(0)))
				&& tokens.get(1).isOfType(CLOSE_BRACKET)
				&& (!firstClassNeedsNeedsRoundBrackets
					|| isTokenCloseParenthesis(tokens.get(1)))
				&& tokens.get(2).isOfType(OPERATOR)
				&& acceptedOperatorSymbols.contains(tokens.get(2).getValue())
				&& tokens.get(3).isOfType(OPEN_BRACKET)
				&& (!secondClassNeedsNeedsRoundBrackets
					|| isTokenOpenParenthesis(tokens.get(3)))
				&& tokens.get(4).isOfType(CLOSE_BRACKET)
				&& (!secondClassNeedsNeedsRoundBrackets
					|| isTokenCloseParenthesis(tokens.get(4)))
				&& functions != null
				&& functions.size() == 2
				&& functions.get(0) != null
				&& firstClass.isInstance(functions.get(0))
				&& functions.get(1) != null
				&& secondClass.isInstance(functions.get(1));
	}
}
