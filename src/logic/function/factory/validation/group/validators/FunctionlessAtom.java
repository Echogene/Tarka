package logic.function.factory.validation.group.validators;

/**
 * @author Steven Weston
 */
public abstract class FunctionlessAtom implements AtomicValidator {
	@Override
	public boolean requiresFunction() {
		return false;
	}
}
