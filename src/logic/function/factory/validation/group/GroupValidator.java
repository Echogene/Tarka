package logic.function.factory.validation.group;

import logic.function.Function;
import logic.function.factory.ValidationException;
import logic.function.factory.validation.results.ValidationResult;

/**
 * @author Steven Weston
 */
public interface GroupValidator {
	ValidationResult validate(TokenGroup group, Function<?, ?> function) throws ValidationException;
	boolean requiresFunction();
}
