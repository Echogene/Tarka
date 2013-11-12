package logic.function.factory.validation.token.group;

import logic.function.factory.validation.token.TokenValidationException;

/**
 * @author Steven Weston
 */
public class NumberedTokenGroupChecker extends TokenGroupCheckerWithNumber {

	private final TokenGroupChecker checker;

	public NumberedTokenGroupChecker(Number number, TokenGroupChecker checker) {
		super(number);
		this.checker = checker;
	}

	public NumberedTokenGroupChecker(TokenGroupChecker checker) {
		super(Number.ONE);
		this.checker = checker;
	}

	@Override
	public void check(TokenGroup tokenGroup) throws TokenValidationException {
		checker.check(tokenGroup);
	}
}
