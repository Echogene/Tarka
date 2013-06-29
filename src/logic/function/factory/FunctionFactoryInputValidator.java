package logic.function.factory;

import logic.function.Function;
import reading.lexing.Token;

import java.util.List;

/**
 * @author Steven Weston
 */
public interface FunctionFactoryInputValidator {
	ValidationResult validate(List<Token> tokens, List<Function<?, ?>> functions);
}
