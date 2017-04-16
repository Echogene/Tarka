package logic.function.factory.validation.checking;

import logic.function.Function;
import logic.function.factory.validation.function.FunctionValidationException;

/**
 * @author Steven Weston
 */
public interface FunctionChecker {

	void check(Function<?, ?, ?> function) throws FunctionValidationException;
}
