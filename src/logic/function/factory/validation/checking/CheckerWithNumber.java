package logic.function.factory.validation.checking;

/**
 * @author Steven Weston
 */
public abstract class CheckerWithNumber implements TokenGroupChecker, FunctionChecker {

	protected final Number number;

	protected CheckerWithNumber(Number number) {
		this.number = number;
	}

	public Number getNumber() {
		return number;
	}

	public static enum Number {
		ONE, MANY
	}
}
