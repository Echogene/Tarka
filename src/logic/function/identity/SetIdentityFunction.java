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

	SetIdentityFunction(String parameter, SetFunction<T> function) {
		super(parameter, function);
	}

	@Override
	public SetIdentityFunction<T> copy() {
		return new SetIdentityFunction<>(parameter, (SetFunction<T>) function.copy());
	}
}
