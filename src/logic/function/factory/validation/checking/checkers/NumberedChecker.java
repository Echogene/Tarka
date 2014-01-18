package logic.function.factory.validation.checking.checkers;

import logic.function.Function;
import logic.function.factory.validation.checking.Checker;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.function.FunctionValidationException;
import logic.function.factory.validation.token.TokenValidationException;
import logic.function.factory.validation.token.group.TokenGroup;

/**
 * @author Steven Weston
 */
public class NumberedChecker extends CheckerWithNumber {

	private final Checker checker;

	public NumberedChecker(Number number, Checker checker) {
		super(number);
		this.checker = checker;
	}

	public NumberedChecker(Checker checker) {
		super(Number.ONE);
		this.checker = checker;
	}

	@Override
	public void check(TokenGroup tokenGroup) throws TokenValidationException {
		checker.check(tokenGroup);
	}

	@Override
	public void check(Function<?, ?, ?> function) throws FunctionValidationException {
		checker.check(function);
	}

	public Checker getChecker() {
		return checker;
	}
}
