package logic.function.identity;

import logic.Nameable;
import logic.function.Function;

/**
 * @author Steven Weston
 */
public class MultitypeIdentityFunction<D extends Nameable> extends AbstractIdentityFunction<D, Object, MultitypeIdentityFunction<D>> {

	public MultitypeIdentityFunction(String parameter) {
		super(parameter);
	}

	public MultitypeIdentityFunction(Function<D, Object, ?> function) {
		super(function);
	}

	private MultitypeIdentityFunction(String parameter, Function<D, Object, ?> function) {
		super(parameter, function);
	}

	@Override
	public MultitypeIdentityFunction<D> copy() {
		return new MultitypeIdentityFunction<>(parameter, function);
	}
}
