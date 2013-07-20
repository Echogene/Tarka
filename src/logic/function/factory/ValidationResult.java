package logic.function.factory;

import java.util.Iterator;
import java.util.List;

/**
 * @author Steven Weston
 */
public class ValidationResult implements Iterable<ValidationResult.ValidationType> {
	private List<ValidationType> types;
	private String message;

	public ValidationResult(List<ValidationType> types) {
		this.types = types;
	}

	private ValidationResult(String message) {
		this.types   = null;
		this.message = message;
	}

	public boolean isValid() {
		return types != null;
	}

	public ValidationType get(int index) {
		return types.get(index);
	}

	public int size() {
		return types.size();
	}

	@Override
	public Iterator<ValidationType> iterator() {
		return types.iterator();
	}

	public static ValidationResult invalid(String message) {
		return new ValidationResult(message);
	}

	public enum ValidationType {
		TOKEN, FUNCTION
	}
}
