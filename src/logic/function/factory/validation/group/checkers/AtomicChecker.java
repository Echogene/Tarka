package logic.function.factory.validation.group.checkers;

import logic.function.factory.validation.group.TokenGroupCheckerWithNumber;

/**
 * @author Steven Weston
 */
public abstract class AtomicChecker extends TokenGroupCheckerWithNumber {

	protected AtomicChecker() {
		super(Number.ONE);
	}
}
