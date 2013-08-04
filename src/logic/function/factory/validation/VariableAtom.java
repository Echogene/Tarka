package logic.function.factory.validation;

import logic.function.Function;
import logic.function.factory.ValidationException;
import logic.function.factory.validation.group.NameAtom;
import logic.function.factory.validation.group.TokenGroup;
import logic.function.factory.validation.results.ValidationResult;

/**
 * @author Steven Weston
 */
public class VariableAtom extends NameAtom {
	@Override
	public ValidationResult validate(TokenGroup group, Function<?, ?> function) throws ValidationException {
		return super.validate(group, function);
	}
}
