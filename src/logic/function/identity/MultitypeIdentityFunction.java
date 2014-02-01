package logic.function.identity;

import logic.Nameable;
import logic.function.Function;

/**
 * @author Steven Weston
 */
public class MultitypeIdentityFunction<T extends Nameable>
		extends AbstractIdentityFunction<T, Object, MultitypeIdentityFunction<T>, Function<T, Object, ?>> {

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
		return new MultitypeIdentityFunction<>(parameter, function.copy());
	}
}
