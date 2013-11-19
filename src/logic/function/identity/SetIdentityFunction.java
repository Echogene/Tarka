package logic.function.identity;

import logic.Nameable;
import logic.function.set.SetFunction;
import logic.set.Set;

/**
 * @author Steven Weston
 */
public class SetIdentityFunction<T extends Nameable> extends IdentityFunction<T, Set<T>> implements SetFunction<T> {

	public SetIdentityFunction(String parameter) {
		super(parameter);
	}

	public SetIdentityFunction(SetFunction<T> function) {
		super(function);
	}
}
