package logic.function.factory.oldvalidation.results;

import logic.function.Function;

/**
 * @author Steven Weston
 * @deprecated
 */
public class FunctionResult implements ValidationResult {
	private final Function<?, ?> function;

	public FunctionResult(Function<?, ?> function) {
		this.function = function;
	}

	public Function<?, ?> getFunction() {
		return function;
	}
}
