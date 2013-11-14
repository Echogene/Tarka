package logic.function.factory.validation.checking.checkers;

import logic.function.Function;
import logic.function.factory.validation.function.FunctionValidationException;

/**
 * @author Steven Weston
 */
public abstract class FunctionlessChecker extends AtomicChecker {

	@Override
	public void check(Function<?, ?> function) throws FunctionValidationException {
		throw new FunctionValidationException("The function " + function.toString() + " wasn't expected");
	}
}
