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
