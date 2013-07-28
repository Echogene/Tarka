package logic.function.factory.multary;

import logic.function.Function;
import logic.function.factory.FunctionFactoryInputValidator;
import logic.function.factory.ValidationException;
import logic.function.factory.ValidationResult;
import logic.function.set.SetFunction;
import reading.lexing.Token;

import java.util.ArrayList;
import java.util.List;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.*;
import static logic.function.factory.ValidationResult.ValidationType;
import static logic.function.factory.ValidationResult.ValidationType.FUNCTION;
import static logic.function.factory.ValidationResult.ValidationType.TOKEN;
import static logic.function.factory.ValidationResult.invalid;
import static util.CollectionUtils.safeGet;

/**
 * @author Steven Weston
 */
public class MultaryValidator implements FunctionFactoryInputValidator {
	private final List<String> acceptedOperatorSymbols;
	private final Class parameterClass;

	public MultaryValidator(List<String> acceptedOperatorSymbols, Class parameterClass) {
		this.acceptedOperatorSymbols = acceptedOperatorSymbols;
		this.parameterClass = parameterClass;
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
		validateTokensNotEmpty(tokens);
		List<ValidationType> validationTypes = new ArrayList<>();
		int currentFunctionIndex = 0;
		int currentTokenIndex = 0;
		Token lastToken = null;
		for (Token token : tokens) {
			if (token.isOfType(CLOSE_BRACKET)) {
				validateCloseParenthesis(token);
				validatePreviousTokenWasOpenParen(lastToken);
				Function function = safeGet(functions, currentFunctionIndex++);
				validateFunction(function);
				validationTypes.add(FUNCTION);
			} else if (token.isOfType(OPEN_BRACKET)) {
				validateOpenParenthesis(token);
				validateOpenParenIsNotFirstToken(currentTokenIndex);
			} else if (token.isOfType(OPERATOR)) {
				validateOperatorIsFirstToken(currentTokenIndex);
				validateOperatorIsAccepted(token);
			} else if (token.isOfType(NAME)) {
				validateNameTokenIsNotFirst(currentTokenIndex);
				Function function = safeGet(functions, currentFunctionIndex++);
				validateFunctionIsNull(function);
				validationTypes.add(TOKEN);
			}
			currentTokenIndex++;
			lastToken = token;
		}
		validateFunctionsSize(functions, currentFunctionIndex);
		return new ValidationResult(validationTypes);
	}

	private void validateFunctionsSize(List<Function<?, ?>> functions, int currentFunctionIndex) throws ValidationException {
		if (currentFunctionIndex != functions.size()) {
			throw new ValidationException("There must be " + currentFunctionIndex + " tokens.");
		}
	}

	private void validateFunctionIsNull(Function function) throws ValidationException {
		if (function != null) {
			throw new ValidationException("The function must be null.");
		}
	}

	private void validateNameTokenIsNotFirst(int currentTokenIndex) throws ValidationException {
		if (currentTokenIndex == 0) {
			throw new ValidationException("The first token cannot be a name token.");
		}
	}

	private void validateOperatorIsAccepted(Token token) throws ValidationException {
		if (!acceptedOperatorSymbols.contains(token.getValue())) {
			throw new ValidationException("The operator must be one of " + acceptedOperatorSymbols.toString() + ".");
		}
	}

	private void validateOperatorIsFirstToken(int currentTokenIndex) throws ValidationException {
		if (currentTokenIndex != 0) {
			throw new ValidationException("The operator must be the first token.");
		}
	}

	private void validateOpenParenIsNotFirstToken(int currentTokenIndex) throws ValidationException {
		if (currentTokenIndex == 0) {
			throw new ValidationException("The first token cannot be an opening parenthesis.");
		}
	}

	private void validateFunction(Function function) throws ValidationException {
		if (function == null) {
			throw new ValidationException("A function must exist.");
		} else if (!parameterClass.isInstance(function)) {
			throw new ValidationException("The function must be a " + parameterClass.getName() + ".");
		}
	}

	private void validatePreviousTokenWasOpenParen(Token lastToken) throws ValidationException {
		if (lastToken == null || !lastToken.isOfType(OPEN_BRACKET)) {
			throw new ValidationException("A closing parenthesis must be immediately after an opening parenthesis.");
		}
	}

	private void validateTokensNotEmpty(List<Token> tokens) throws ValidationException {
		if (tokens == null || tokens.isEmpty()) {
			throw new ValidationException("There must be at least one token.");
		}
	}

	private void validateOpenParenthesis(Token token) throws ValidationException {
		if (!(SetFunction.class.isAssignableFrom(parameterClass) || isTokenOpenParenthesis(token))) {
			throw new ValidationException("The token must be an open parenthesis");
		}
	}

	private void validateCloseParenthesis(Token token) throws ValidationException {
		if (!(SetFunction.class.isAssignableFrom(parameterClass) || isTokenCloseParenthesis(token))) {
			throw new ValidationException("The token must be a close parenthesis");
		}
	}
}
