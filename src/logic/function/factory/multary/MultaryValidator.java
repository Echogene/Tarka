package logic.function.factory.multary;

import logic.function.factory.validation.Validator;
import logic.function.factory.validation.group.validators.FunctionAtom;
import logic.function.factory.validation.group.validators.OperatorAtom;

import java.util.List;

import static java.util.Arrays.asList;
import static logic.function.factory.validation.GroupValidatorWithNumber.Number.MANY;
import static logic.function.factory.validation.GroupValidatorWithNumber.Number.ONE;

/**
 * @author Steven Weston
 */
public class MultaryValidator extends Validator {
	public MultaryValidator(List<String> acceptedOpeningBrackets, List<String> acceptedOperatorSymbols, Class parameterClass, List<String> acceptedClosingBrackets) {
		super(acceptedOpeningBrackets, acceptedClosingBrackets);
		addValidator(new OperatorAtom(acceptedOperatorSymbols), ONE);
		addValidator(new FunctionAtom(parameterClass), MANY);
	}

	public MultaryValidator(List<String> acceptedOperatorSymbols, Class parameterClass) {
		this(asList("("), acceptedOperatorSymbols, parameterClass, asList(")"));
	}
}
