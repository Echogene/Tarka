package logic.function.factory;

import logic.function.Function;
import reading.lexing.Token;

import java.util.ArrayList;
import java.util.List;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.*;
import static logic.function.factory.ValidationResult.INVALID;
import static logic.function.factory.ValidationResult.ValidationType;
import static logic.function.factory.ValidationResult.ValidationType.FUNCTION;
import static logic.function.factory.ValidationResult.ValidationType.TOKEN;
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
		List<ValidationType> validationTypes = new ArrayList<>();
		int currentFunctionIndex = 0;
		int currentTokenIndex = 0;
		Token lastToken = null;
		for (Token token : tokens) {
			if (token.isOfType(CLOSE_PAREN)) {
				if (lastToken == null || !lastToken.isOfType(OPEN_PAREN)) {
					return INVALID;
				}
				Function function = safeGet(functions, currentFunctionIndex++);
				if (function == null || !parameterClass.isInstance(function)) {
					return INVALID;
				}
				validationTypes.add(FUNCTION);
			} else if (token.isOfType(OPEN_PAREN)) {

			} else if (token.isOfType(OPERATOR)) {
				if (!acceptedOperatorSymbols.contains(token.getValue())
						|| currentTokenIndex != 0) {
					return INVALID;
				}
			} else if (token.isOfType(NAME)) {
				Function function = safeGet(functions, currentFunctionIndex++);
				if (function != null) {
					return INVALID;
				}
				validationTypes.add(TOKEN);
			}
			currentTokenIndex++;
			lastToken = token;
		}
		if (currentFunctionIndex != functions.size()) {
			return INVALID;
		}
		return new ValidationResult(validationTypes);
	}
}
