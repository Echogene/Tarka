package logic.function.factory.validation.group.validators;

import logic.function.factory.validation.VariableAtom;
import logic.function.factory.validation.group.GroupValidator;

import java.util.Arrays;
import java.util.List;

/**
 * @author Steven Weston
 */
public class FunctionOrVariableValidator extends DisjunctiveValidator {
	public FunctionOrVariableValidator(
			List<String> acceptedOpeningBrackets,
			List<Class> acceptedFunctionClasses,
			List<String> acceptedClosingBrackets
	) {
		super(getValidators(acceptedOpeningBrackets, acceptedFunctionClasses, acceptedClosingBrackets));
	}

	public FunctionOrVariableValidator(Class acceptedFunctionClass) {
		this(Arrays.<Class>asList(acceptedFunctionClass));
	}

	public FunctionOrVariableValidator(List<Class> acceptedFunctionClasses) {
		this(null, acceptedFunctionClasses, null);
	}

	public static List<GroupValidator> getValidators(
			List<String> acceptedOpeningBrackets,
			List<Class> acceptedFunctionClasses,
			List<String> acceptedClosingBrackets
	) {
		return Arrays.<GroupValidator>asList(
				new FunctionAtom(acceptedOpeningBrackets, acceptedFunctionClasses, acceptedClosingBrackets),
				new VariableAtom()
		);
	}
}