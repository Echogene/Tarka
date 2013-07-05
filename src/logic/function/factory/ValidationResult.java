package logic.function.factory;

import java.util.Iterator;
import java.util.List;

/**
 * @author Steven Weston
 */
public class ValidationResult implements Iterable<ValidationResult.ValidationType> {
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

	public int size() {
		return types.size();
	}

	@Override
	public Iterator<ValidationType> iterator() {
		return types.iterator();
	}

	public enum ValidationType {
		TOKEN, FUNCTION
	}
}
