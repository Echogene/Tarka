package logic.function.ifelse;

import logic.Nameable;
import logic.function.evaluable.Evaluable;
import logic.function.voidfunction.VoidFunction;

/**
 * @author Steven Weston
 */
public class VoidIfElse<T extends Nameable>
		extends AbstractIfElse<T, Void, VoidIfElse<T>>
		implements VoidFunction<T, VoidIfElse<T>> {

	public VoidIfElse(Evaluable<T, ?> condition, VoidFunction<T, ?> ifTrue, VoidFunction<T, ?> ifFalse) {
		super(condition, ifTrue, ifFalse);
	}

	@Override
	public VoidIfElse<T> copy() {
		return new VoidIfElse<>(condition.copy(), (VoidFunction<T, ?>) ifTrue, (VoidFunction<T, ?>) ifFalse);
	}
}
