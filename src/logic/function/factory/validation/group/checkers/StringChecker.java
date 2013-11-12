package logic.function.factory.validation.group.checkers;

import logic.function.factory.oldvalidation.group.TokenGroup;
import logic.function.factory.validation.TokenValidationException;

/**
 * @author Steven Weston
 */
public class StringChecker extends AtomicChecker {

	private final String acceptedString;

	public StringChecker(String acceptedString) {
		this.acceptedString = acceptedString;
	}

	@Override
	public void check(TokenGroup tokenGroup) throws TokenValidationException {
		if (!tokenGroup.representsName()) {
			throw new TokenValidationException(tokenGroup + " did not represent a string.");
		}
		if (!tokenGroup.getValue().equals(acceptedString)) {
			throw new TokenValidationException(tokenGroup.toString() + " did not match " + acceptedString);
		}
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " where equal to " + acceptedString;
	}
}
