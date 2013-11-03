package logic.function.factory.validation.group.validators;

import logic.function.Function;
import logic.function.factory.ValidationException;
import logic.function.factory.validation.group.GroupValidator;
import logic.function.factory.validation.group.TokenGroup;
import logic.function.factory.validation.results.ValidationResult;

import java.util.ArrayList;
import java.util.List;

import static util.StringUtils.join;

/**
 * @author Steven Weston
 */
public class DisjunctiveValidator implements GroupValidator {

	List<GroupValidator> validators;

	public DisjunctiveValidator(List<GroupValidator> validators) {
		this.validators = validators;
	}

	@Override
	public logic.function.factory.validation.results.ValidationResult validate(TokenGroup group, Function<?, ?> function) throws ValidationException {
		List<ValidationResult> passedResults = new ArrayList<>();
		List<String> failedValidatorMessages = new ArrayList<>();
		for (GroupValidator validator : validators) {
			try {
				if (validator.requiresFunction()) {
					passedResults.add(validator.validate(group, function));
				} else {
					passedResults.add(validator.validate(group, null));
				}
			} catch (ValidationException e) {
				failedValidatorMessages.add(e.getMessage());
			}
		}
		if (passedResults.size() == 1) {
			return passedResults.get(0);
		} else if (passedResults.isEmpty()) {
			throw new ValidationException(join(failedValidatorMessages, "\n"));
		} else {
			throw new ValidationException("The group " + group.toString() + " was ambiguous.");
		}
	}

	@Override
	public boolean requiresFunction() {
		for (GroupValidator validator : validators) {
			if (validator.requiresFunction()) {
				return true;
			}
		}
		return false;
	}
}
