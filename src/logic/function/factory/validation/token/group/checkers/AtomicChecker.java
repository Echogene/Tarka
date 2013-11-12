package logic.function.factory.validation.token.group.checkers;

import logic.function.factory.validation.token.group.TokenGroupCheckerWithNumber;

/**
 * @author Steven Weston
 */
public abstract class AtomicChecker extends TokenGroupCheckerWithNumber {

	protected AtomicChecker() {
		super(Number.ONE);
	}
}
