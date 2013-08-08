package logic.function.factory.construction;

import logic.Nameable;
import logic.function.Function;
import logic.function.factory.ConstructorFromString;
import logic.function.factory.validation.results.FunctionResult;
import logic.function.factory.validation.results.StringResult;
import logic.function.factory.validation.results.ValidationResult;

/**
 * @author Steven Weston
 */
public class FunctionConstructorFromFunctionOrVariable<F extends Function<T, ?>, T extends Nameable> {

	private final ConstructorFromString<F> constructor;

	public FunctionConstructorFromFunctionOrVariable(ConstructorFromString<F> constructor) {
		this.constructor = constructor;
	}

	public F construct(ValidationResult result) {
		if (result instanceof StringResult) {
			return constructor.construct(((StringResult) result).getString());
		} else {
			return (F) ((FunctionResult) result).getFunction();
		}
	}
}
