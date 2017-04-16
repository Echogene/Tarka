package logic.function.identity;

import logic.Nameable;
import logic.function.set.SetFunction;
import logic.set.Set;
import util.FunctionUtils;

/**
 * @author Steven Weston
 */
public class SetIdentityFunction<T extends Nameable>
		extends AbstractIdentityFunction<T, Set<T>, SetIdentityFunction<T>, SetFunction<T, ?>>
		implements SetFunction<T, SetIdentityFunction<T>> {

	public SetIdentityFunction(String parameter) {
		super(parameter);
	}

	public SetIdentityFunction(SetFunction<T, ?> function) {
		super(function);
	}

	SetIdentityFunction(String parameter, SetFunction<T, ?> function) {
		super(parameter, function);
	}

	@Override
	public SetIdentityFunction<T> copy() {
		return new SetIdentityFunction<>(parameter, FunctionUtils.copy(function));
	}
}
