package logic.function.factory.oldvalidation.group.validators;

import logic.function.Function;
import logic.function.factory.ValidationException;
import logic.function.factory.oldvalidation.group.TokenGroup;
import logic.function.factory.oldvalidation.results.StringResult;
import logic.function.factory.oldvalidation.results.ValidationResult;

import java.util.Arrays;
import java.util.List;

/**
 * @author Steven Weston
 * @deprecated
 */
public class OperatorAtom extends FunctionlessAtom {
	private final List<String> acceptedOperatorSymbols;

	public OperatorAtom(List<String> acceptedOperatorSymbols) {
		this.acceptedOperatorSymbols = acceptedOperatorSymbols;
	}

	public OperatorAtom(String acceptedOperatorSymbol) {
		this(Arrays.asList(acceptedOperatorSymbol));
	}

	@Override
	public ValidationResult validate(TokenGroup group, Function<?, ?> function) throws ValidationException {
		if (function != null) {
			throw new ValidationException("The function—" + function.toString() + "—was not null.");
		}
		if (!group.representsOperator()) {
			throw new ValidationException("The group—" + group.toString() + "—did not represent an operator.");
		}
		if (!acceptedOperatorSymbols.contains(group.getValue())) {
			throw new ValidationException("The operator—" + group.toString() + "—was not in " + acceptedOperatorSymbols +".");
		}
		return new StringResult(group.getValue());
	}
}
