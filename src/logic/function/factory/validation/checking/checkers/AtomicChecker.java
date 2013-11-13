package logic.function.factory.validation.checking.checkers;

import logic.function.factory.validation.checking.CheckerWithNumber;

/**
 * @author Steven Weston
 */
public abstract class AtomicChecker extends CheckerWithNumber {

	protected AtomicChecker() {
		super(Number.ONE);
	}
}
