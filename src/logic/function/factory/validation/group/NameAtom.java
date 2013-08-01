package logic.function.factory.validation.group;

import logic.function.Function;
import logic.function.factory.ValidationException;

/**
 * @author Steven Weston
 */
public abstract class NameAtom implements AtomicValidator {
	@Override
	public GroupValidator validate(TokenGroup group, Function<?, ?> function) throws ValidationException {
		if (!group.representsName()) {
			throw new ValidationException("The group—" + group.toString() + "—did not represent a name.");
		}
		if (function != null) {
			throw new ValidationException("The function—" + function.toString() + "—was not null.");
		}
		return this;
	}
}
