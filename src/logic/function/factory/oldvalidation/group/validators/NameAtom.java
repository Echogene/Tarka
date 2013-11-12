package logic.function.factory.oldvalidation.group.validators;

import logic.function.Function;
import logic.function.factory.ValidationException;
import logic.function.factory.oldvalidation.results.StringResult;
import logic.function.factory.oldvalidation.results.ValidationResult;
import logic.function.factory.validation.group.TokenGroup;

/**
 * @author Steven Weston
 * @deprecated
 */
public abstract class NameAtom extends FunctionlessAtom {
	@Override
	public ValidationResult validate(TokenGroup group, Function<?, ?> function) throws ValidationException {
		if (!group.representsName()) {
			throw new ValidationException("The group—" + group.toString() + "—did not represent a name.");
		}
		if (function != null) {
			throw new ValidationException("The function—" + function.toString() + "—was not null.");
		}
		return new StringResult(group.getValue());
	}
}
