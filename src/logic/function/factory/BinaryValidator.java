package logic.function.factory;

import logic.function.Function;
import logic.function.reflexive.ReflexiveFunction;
import reading.lexing.Token;

import java.util.Arrays;
import java.util.List;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.*;
import static logic.function.factory.ValidationResult.INVALID;
import static logic.function.factory.ValidationResult.ValidationType.FUNCTION;
import static logic.function.factory.ValidationResult.ValidationType.TOKEN;

/**
 * @author Steven Weston
 */
public class BinaryValidator implements FunctionFactoryInputValidator {
	private final List<String> acceptedOperatorSymbols;

	public BinaryValidator(List<String> acceptedOperatorSymbols) {
		this.acceptedOperatorSymbols = acceptedOperatorSymbols;
	}

	@Override
	public ValidationResult validate(List<Token> tokens, List<Function<?, ?>> functions) {
		if (tokens == null || tokens.size() < 3) {
			return INVALID;
		}
		if (matchesNoFunctions(tokens, functions)) {
			return new ValidationResult(Arrays.asList(TOKEN, TOKEN));
		} else if (matchesFirstFunction(tokens, functions)) {
			return new ValidationResult(Arrays.asList(FUNCTION, TOKEN));
		} else if (matchesSecondFunction(tokens, functions)) {
			return new ValidationResult(Arrays.asList(TOKEN, FUNCTION));
		} else if (matchesBothFunctions(tokens, functions)) {
			return new ValidationResult(Arrays.asList(FUNCTION, FUNCTION));
		} else {
			return INVALID;
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
		return tokens.size() == 4
				&& tokens.get(0).isOfType(OPEN_PAREN)
				&& tokens.get(1).isOfType(CLOSE_PAREN)
				&& tokens.get(2).isOfType(OPERATOR)
				&& acceptedOperatorSymbols.contains(tokens.get(2).getValue())
				&& tokens.get(3).isOfType(NAME)
				&& functions != null
				&& functions.size() == 2
				&& functions.get(0) != null
				&& functions.get(0) instanceof ReflexiveFunction<?>
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
		return tokens.size() == 4
				&& tokens.get(0).isOfType(NAME)
				&& tokens.get(1).isOfType(OPERATOR)
				&& acceptedOperatorSymbols.contains(tokens.get(1).getValue())
				&& tokens.get(2).isOfType(OPEN_PAREN)
				&& tokens.get(3).isOfType(CLOSE_PAREN)
				&& functions != null
				&& functions.size() == 2
				&& functions.get(0) == null
				&& functions.get(1) != null
				&& functions.get(1) instanceof ReflexiveFunction<?>;
	}

	/**
	 * Return whether the given {@code Token}s and {@code Function}s match the pattern formed by two functions
	 * (i.e. of the form "(f) = (g)").
	 * @param tokens A list of {@code Token}s to match against.
	 * @param functions A list of {@code Function}s to match against.
	 * @return Whether there are two functions and no name tokens.
	 */
	private boolean matchesBothFunctions(List<Token> tokens, List<Function<?, ?>> functions) {
		return tokens.size() == 5
				&& tokens.get(0).isOfType(OPEN_PAREN)
				&& tokens.get(1).isOfType(CLOSE_PAREN)
				&& tokens.get(2).isOfType(OPERATOR)
				&& acceptedOperatorSymbols.contains(tokens.get(2).getValue())
				&& tokens.get(3).isOfType(OPEN_PAREN)
				&& tokens.get(4).isOfType(CLOSE_PAREN)
				&& functions != null
				&& functions.size() == 2
				&& functions.get(0) != null
				&& functions.get(0) instanceof ReflexiveFunction<?>
				&& functions.get(1) != null
				&& functions.get(1) instanceof ReflexiveFunction<?>;
	}
}
