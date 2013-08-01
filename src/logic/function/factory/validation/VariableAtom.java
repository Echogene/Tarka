package logic.function.factory.validation;

import logic.function.Function;
import logic.function.factory.ValidationException;
import logic.function.factory.validation.group.GroupValidator;
import logic.function.factory.validation.group.NameAtom;
import logic.function.factory.validation.group.TokenGroup;

/**
 * @author Steven Weston
 */
public class VariableAtom extends NameAtom {
	@Override
	public GroupValidator validate(TokenGroup group, Function<?, ?> function) throws ValidationException {
		super.validate(group, function);
		return this;
	}
}
