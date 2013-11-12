package logic.function.factory.validation.token.group;

/**
 * @author Steven Weston
 */
public abstract class TokenGroupCheckerWithNumber implements TokenGroupChecker {

	protected final Number number;

	protected TokenGroupCheckerWithNumber(Number number) {
		this.number = number;
	}

	public Number getNumber() {
		return number;
	}

	public static enum Number {
		ONE, MANY
	}
}
