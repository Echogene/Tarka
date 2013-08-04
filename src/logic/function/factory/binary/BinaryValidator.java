package logic.function.factory.binary;

import logic.function.factory.validation.Validator;
import logic.function.factory.validation.group.validators.FunctionAtom;
import logic.function.factory.validation.group.validators.OperatorAtom;
import logic.function.reflexive.ReflexiveFunction;

import java.util.List;

import static java.util.Arrays.asList;
import static logic.function.factory.validation.GroupValidatorWithNumber.Number.ONE;

/**
 * @author Steven Weston
 */
public class BinaryValidator extends Validator {
	public BinaryValidator(
			List<String> acceptedOpeningBrackets,
			Class firstClass,
			List<String> acceptedOperatorSymbols,
			Class secondClass,
			List<String> acceptedClosingBrackets) {
		super(acceptedOpeningBrackets, acceptedClosingBrackets);
		addValidator(new FunctionAtom(firstClass), ONE);
		addValidator(new OperatorAtom(acceptedOperatorSymbols), ONE);
		addValidator(new FunctionAtom(secondClass), ONE);
	}

	public BinaryValidator(Class firstClass, List<String> acceptedOperatorSymbols, Class secondClass) {
		this(asList("("), firstClass, acceptedOperatorSymbols, secondClass, asList(")"));
	}

	public BinaryValidator(List<String> acceptedOperatorSymbols) {
		this(ReflexiveFunction.class, acceptedOperatorSymbols, ReflexiveFunction.class);
	}
}
