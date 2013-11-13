package logic.function.factory.validation.checking.checkers;

import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.TokenGroupChecker;
import logic.function.factory.validation.token.TokenValidationException;
import logic.function.factory.validation.token.group.TokenGroup;

/**
 * @author Steven Weston
 */
public class NumberedChecker extends CheckerWithNumber {

	private final TokenGroupChecker checker;

	public NumberedChecker(Number number, TokenGroupChecker checker) {
		super(number);
		this.checker = checker;
	}

	public NumberedChecker(TokenGroupChecker checker) {
		super(Number.ONE);
		this.checker = checker;
	}

	@Override
	public void check(TokenGroup tokenGroup) throws TokenValidationException {
		checker.check(tokenGroup);
	}
}
