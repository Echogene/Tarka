package logic.function.factory.validation.token.group.checkers;

import logic.function.factory.validation.token.TokenValidationException;
import logic.function.factory.validation.token.group.TokenGroup;

import java.util.Arrays;
import java.util.List;

/**
 * @author Steven Weston
 */
public class OperatorChecker extends AtomicChecker {

	private final List<String> acceptedOperators;

	public OperatorChecker(List<String> acceptedOperators) {
		this.acceptedOperators = acceptedOperators;
	}

	public OperatorChecker(String acceptedOperator) {
		this(Arrays.asList(acceptedOperator));
	}

	@Override
	public void check(TokenGroup tokenGroup) throws TokenValidationException {
		if (!tokenGroup.representsOperator()) {
			throw new TokenValidationException(tokenGroup.toString() + " did not represent an operator.");
		}
		if (!acceptedOperators.contains(tokenGroup.getValue())) {
			throw new TokenValidationException(tokenGroup.toString() + " was not in " + acceptedOperators.toString() + ".");
		}
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " where in " + acceptedOperators;
	}
}
