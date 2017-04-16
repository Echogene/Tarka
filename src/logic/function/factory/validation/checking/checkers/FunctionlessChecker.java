package logic.function.factory.validation.checking.checkers;

import logic.function.Function;
import logic.function.factory.validation.function.FunctionValidationException;

import java.text.MessageFormat;

/**
 * @author Steven Weston
 */
public abstract class FunctionlessChecker extends AtomicChecker {

	@Override
	public void check(Function<?, ?, ?> function) throws FunctionValidationException {
		throw new FunctionValidationException(MessageFormat.format("The function {0} wasn''t expected", function.toString()));
	}
}
