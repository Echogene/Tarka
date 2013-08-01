package logic.function.factory.validation.group;

import logic.function.Function;
import logic.function.factory.ValidationException;

import java.util.ArrayList;
import java.util.List;

import static util.CollectionUtils.join;

/**
 * @author Steven Weston
 */
public class DisjunctiveValidator implements GroupValidator {

	List<GroupValidator> validators;

	@Override
	public GroupValidator validate(TokenGroup group, Function<?, ?> function) throws ValidationException {
		List<GroupValidator> passedValidators = new ArrayList<>();
		List<String> failedValidatorMessages = new ArrayList<>();
		for (GroupValidator validator : validators) {
			try {
				validator.validate(group, function);
				passedValidators.add(validator);
			} catch (ValidationException e) {
				failedValidatorMessages.add(e.getMessage());
			}
		}
		if (passedValidators.size() == 1) {
			return passedValidators.get(0);
		} else if (passedValidators.isEmpty()) {
			throw new ValidationException(join(failedValidatorMessages, "\n"));
		} else {
			throw new ValidationException("The group " + group.toString() + " was ambiguous.");
		}
	}
}
