package logic.function.factory.validation;

import logic.function.Function;
import logic.function.factory.ValidationException;
import logic.function.factory.validation.group.GroupValidator;
import logic.function.factory.validation.group.TokenGroup;
import logic.function.factory.validation.results.ValidationResult;

/**
* @author Steven Weston
*/
public class GroupValidatorWithNumber implements GroupValidator {
	private final GroupValidator validator;
	private final Number number;

	public GroupValidatorWithNumber(GroupValidator validator, Number number) {
		this.validator = validator;
		this.number    = number;
	}

	public Number getNumber() {
		return number;
	}

	@Override
	public ValidationResult validate(TokenGroup group, Function<?, ?> function) throws ValidationException {
		return validator.validate(group, function);
	}

	@Override
	public boolean requiresFunction() {
		return validator.requiresFunction();
	}

	public enum Number {
		ONE, MANY
	}
}
