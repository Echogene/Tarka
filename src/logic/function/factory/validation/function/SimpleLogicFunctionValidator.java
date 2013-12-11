package logic.function.factory.validation.function;

import logic.Nameable;
import logic.function.Function;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.type.map.MapToErrors;
import util.CurrentIterator;

import java.util.List;

import static logic.function.factory.validation.checking.CheckerWithNumber.Number.ONE;

/**
 * @author Steven Weston
 */
public class SimpleLogicFunctionValidator<T extends Nameable> implements FunctionValidator<T> {

	private final List<CheckerWithNumber> checkers;
	private CurrentIterator<CheckerWithNumber> currentChecker;

	public SimpleLogicFunctionValidator(List<CheckerWithNumber> checkers) {
		this.checkers = checkers;
	}

	@Override
	public synchronized MapToErrors<Function<T, ?>> validateFunctions(List<Function<T, ?>> functions) throws FunctionValidationException {
		resetIterator();
		return new MapToErrors<>(functions, this::checkFunction);
	}

	private synchronized void checkFunction(Function<?, ?> function) throws FunctionValidationException {
		while (true) {
			CheckerWithNumber current = currentChecker.current();
			if (current == null) {
				throw new FunctionValidationException("There weren't enough checkers.");
			}
			try {
				current.check(function);
				if (ONE == current.getNumber()) {
					currentChecker.next();
				}
				return;
			} catch (FunctionValidationException e) {
				if (ONE == current.getNumber()) {
					currentChecker.next();
					throw e;
				} else {
					currentChecker.next();
				}
			}
		}
	}

	private void resetIterator() {
		currentChecker = new CurrentIterator<>(checkers.iterator());
	}
}
