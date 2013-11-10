package logic.function.factory.oldvalidation.group.validators;

import logic.function.factory.oldvalidation.VariableAtom;
import logic.function.factory.oldvalidation.group.GroupValidator;

import java.util.Arrays;
import java.util.List;

/**
 * @author Steven Weston
 * @deprecated
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
