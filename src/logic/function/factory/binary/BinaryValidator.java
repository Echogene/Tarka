package logic.function.factory.binary;

import logic.function.factory.oldvalidation.SimpleLogicValidator;
import logic.function.factory.oldvalidation.group.validators.FunctionOrVariableValidator;
import logic.function.factory.oldvalidation.group.validators.OperatorAtom;
import logic.function.reflexive.ReflexiveFunction;

import java.util.List;

import static java.util.Arrays.asList;
import static logic.function.factory.oldvalidation.GroupValidatorWithNumber.Number.ONE;

/**
 * @author Steven Weston
 */
public class BinaryValidator extends SimpleLogicValidator {
	public BinaryValidator(
			List<String> acceptedOpeningBrackets,
			Class firstClass,
			List<String> acceptedOperatorSymbols,
			Class secondClass,
			List<String> acceptedClosingBrackets) {
		super(acceptedOpeningBrackets, acceptedClosingBrackets);
		addValidator(ONE, new FunctionOrVariableValidator(firstClass));
		if (acceptedOperatorSymbols != null) {
			addValidator(ONE, new OperatorAtom(acceptedOperatorSymbols));
		}
		addValidator(ONE, new FunctionOrVariableValidator(secondClass));
	}

	public BinaryValidator(Class firstClass, List<String> acceptedOperatorSymbols, Class secondClass) {
		this(asList("("), firstClass, acceptedOperatorSymbols, secondClass, asList(")"));
	}

	public BinaryValidator(List<String> acceptedOperatorSymbols) {
		this(ReflexiveFunction.class, acceptedOperatorSymbols, ReflexiveFunction.class);
	}
}
