package logic.function.factory.oldvalidation.group;

import logic.function.Function;
import logic.function.factory.ValidationException;
import logic.function.factory.oldvalidation.results.ValidationResult;

/**
 * @author Steven Weston
 * @deprecated
 */
public interface GroupValidator {
	ValidationResult validate(TokenGroup group, Function<?, ?> function) throws ValidationException;
	boolean requiresFunction();
}
