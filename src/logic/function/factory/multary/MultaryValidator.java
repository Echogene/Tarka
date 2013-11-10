package logic.function.factory.multary;

import logic.function.factory.validation.SimpleLogicValidator;
import logic.function.factory.validation.group.validators.FunctionOrVariableValidator;
import logic.function.factory.validation.group.validators.OperatorAtom;
import logic.function.reflexive.ReflexiveFunction;

import java.util.List;

import static java.util.Arrays.asList;
import static logic.function.factory.validation.GroupValidatorWithNumber.Number.MANY;
import static logic.function.factory.validation.GroupValidatorWithNumber.Number.ONE;

/**
 * @author Steven Weston
 */
public class MultaryValidator extends SimpleLogicValidator {
	public MultaryValidator(List<String> acceptedOpeningBrackets, List<String> acceptedOperatorSymbols, Class parameterClass, List<String> acceptedClosingBrackets) {
		super(acceptedOpeningBrackets, acceptedClosingBrackets);
		if (acceptedOperatorSymbols != null) {
			addValidator(ONE, new OperatorAtom(acceptedOperatorSymbols));
		}
		addValidator(MANY, new FunctionOrVariableValidator(parameterClass));
	}

	public MultaryValidator(List<String> acceptedOperatorSymbols, Class parameterClass) {
		this(asList("("), acceptedOperatorSymbols, parameterClass, asList(")"));
	}

	public MultaryValidator(List<String> acceptedOperatorSymbols) {
		this(acceptedOperatorSymbols, ReflexiveFunction.class);
	}
}
