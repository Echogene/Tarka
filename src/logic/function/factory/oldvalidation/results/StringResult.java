package logic.function.factory.oldvalidation.results;

/**
 * @author Steven Weston
 */
public class StringResult implements ValidationResult {
	private final String string;

	public StringResult(String string) {
		this.string = string;
	}

	public String getString() {
		return string;
	}
}
