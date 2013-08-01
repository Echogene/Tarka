package logic.function.factory.validation.group;

import logic.function.Function;
import logic.function.factory.ValidationException;

import java.util.List;

/**
 * @author Steven Weston
 */
public class QuantifierAtom implements AtomicValidator {
	private final List<String> acceptedQuantifierSymbols;

	public QuantifierAtom(List<String> acceptedQuantifierSymbols) {
		this.acceptedQuantifierSymbols = acceptedQuantifierSymbols;
	}

	@Override
	public GroupValidator validate(TokenGroup group, Function<?, ?> function) throws ValidationException {
		if (function != null) {
			throw new ValidationException("The function—" + function.toString() + "—was not null.");
		}
		if (!group.representsQuantifier()) {
			throw new ValidationException("The group—" + group.toString() + "—did not represent a quantifier.");
		}
		if (!acceptedQuantifierSymbols.contains(group.getValue())) {
			throw new ValidationException("The quantifier—" + group.toString() + "—was not in " + acceptedQuantifierSymbols +".");
		}
		return this;
	}
}
