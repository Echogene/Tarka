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
public class FunctionConvertor<F extends Function<T, ?>, T extends Nameable> {

	private final ConstructorFromString<? extends F> constructor;

	public FunctionConvertor(ConstructorFromString<? extends F> constructor) {
		this.constructor = constructor;
	}

	public F convert(ValidationResult result) {
		if (result instanceof StringResult) {
			return constructor.construct(((StringResult) result).getString());
		} else {
			return (F) ((FunctionResult) result).getFunction();
		}
	}
}
