package logic.function.identity;

import logic.Nameable;
import logic.function.Function;
import logic.function.MultitypeFunction;
import util.FunctionUtils;

/**
 * @author Steven Weston
 */
public class MultitypeIdentityFunction<T extends Nameable>
		extends AbstractIdentityFunction<T, Object, MultitypeIdentityFunction<T>, Function<T, Object, ?>>
		implements MultitypeFunction<T, MultitypeIdentityFunction<T>> {

	public MultitypeIdentityFunction(String parameter) {
		super(parameter);
	}

	public MultitypeIdentityFunction(Function<T, Object, ?> function) {
		super(function);
	}

	private MultitypeIdentityFunction(String parameter, Function<T, Object, ?> function) {
		super(parameter, function);
	}

	@Override
	public MultitypeIdentityFunction<T> copy() {
		return new MultitypeIdentityFunction<>(parameter, FunctionUtils.copy(function));
	}
}
