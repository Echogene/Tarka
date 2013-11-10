package logic.function.factory.oldvalidation;

import logic.function.Function;
import logic.function.factory.ValidationException;
import logic.function.factory.oldvalidation.group.TokenGroup;
import logic.function.factory.oldvalidation.group.validators.NameAtom;
import logic.function.factory.oldvalidation.results.ValidationResult;

/**
 * @author Steven Weston
 */
public class VariableAtom extends NameAtom {
	@Override
	public ValidationResult validate(TokenGroup group, Function<?, ?> function) throws ValidationException {
		return super.validate(group, function);
	}
}
