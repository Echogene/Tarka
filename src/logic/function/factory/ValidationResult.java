package logic.function.factory;

import java.util.List;

/**
 * @author Steven Weston
 */
public class ValidationResult {
	public static final ValidationResult INVALID = new ValidationResult(null);
	private List<ValidationType> types;

	public ValidationResult(List<ValidationType> types) {
		this.types = types;
	}

	public boolean isValid() {
		return types != null;
	}

	public ValidationType get(int index) {
		return types.get(index);
	}

	public enum ValidationType {
		TOKEN, FUNCTION
	}
}
