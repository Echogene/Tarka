package logic.function.factory.validation.group.checkers;

import logic.function.factory.oldvalidation.group.TokenGroup;
import logic.function.factory.validation.TokenValidationException;

import java.util.List;

/**
 * @author Steven Weston
 */
public class QuantifierChecker extends AtomicChecker {

	private final List<String> acceptedQuantifiers;

	public QuantifierChecker(List<String> acceptedQuantifiers) {
		this.acceptedQuantifiers = acceptedQuantifiers;
	}

	@Override
	public void check(TokenGroup tokenGroup) throws TokenValidationException {
		if (!tokenGroup.representsQuantifier()) {
			throw new TokenValidationException(tokenGroup.toString() + " did not represent a quantifier.");
		}
		if (!acceptedQuantifiers.contains(tokenGroup.getValue())) {
			throw new TokenValidationException(tokenGroup.toString() + " was not in " + acceptedQuantifiers.toString() + ".");
		}
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " where in " + acceptedQuantifiers;
	}
}
