package logic.function.factory.validation.results;

import logic.function.Function;

/**
 * @author Steven Weston
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
