package logic.function.factory.validation.group.checkers;

import logic.function.factory.oldvalidation.group.TokenGroup;
import logic.function.factory.validation.TokenValidationException;

/**
 * @author Steven Weston
 */
public class VariableChecker extends AtomicChecker {

	@Override
	public void check(TokenGroup tokenGroup) throws TokenValidationException {
		if (!tokenGroup.representsName()) {
			throw new TokenValidationException(tokenGroup + " did not represent a variable.");
		}
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
