package logic.function.factory.oldvalidation.group.validators;

/**
 * @author Steven Weston
 */
public abstract class FunctionlessAtom implements AtomicValidator {
	@Override
	public boolean requiresFunction() {
		return false;
	}
}
