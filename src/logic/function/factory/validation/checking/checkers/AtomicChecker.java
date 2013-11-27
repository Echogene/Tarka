package logic.function.factory.validation.checking.checkers;

import logic.function.factory.validation.checking.CheckerWithNumber;

/**
 * @author Steven Weston
 */
abstract class AtomicChecker extends CheckerWithNumber {

	AtomicChecker() {
		super(Number.ONE);
	}
}
