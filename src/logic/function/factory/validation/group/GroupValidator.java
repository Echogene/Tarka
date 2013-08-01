package logic.function.factory.validation.group;

import logic.function.Function;
import logic.function.factory.ValidationException;

/**
 * @author Steven Weston
 */
public interface GroupValidator {
	GroupValidator validate(TokenGroup group, Function<?, ?> function) throws ValidationException;
}
