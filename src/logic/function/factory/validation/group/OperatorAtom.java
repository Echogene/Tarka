package logic.function.factory.validation.group;

import logic.function.Function;
import logic.function.factory.ValidationException;

import java.util.List;

/**
 * @author Steven Weston
 */
public class OperatorAtom implements AtomicValidator {
	private final List<String> acceptedOperatorSymbols;

	public OperatorAtom(List<String> acceptedOperatorSymbols) {
		this.acceptedOperatorSymbols = acceptedOperatorSymbols;
	}

	@Override
	public GroupValidator validate(TokenGroup group, Function<?, ?> function) throws ValidationException {
		if (function != null) {
			throw new ValidationException("The function—" + function.toString() + "—was not null.");
		}
		if (!group.representsOperator()) {
			throw new ValidationException("The group—" + group.toString() + "—did not represent an operator.");
		}
		if (!acceptedOperatorSymbols.contains(group.getValue())) {
			throw new ValidationException("The operator—" + group.toString() + "—was not in " + acceptedOperatorSymbols +".");
		}
		return this;
	}
}
