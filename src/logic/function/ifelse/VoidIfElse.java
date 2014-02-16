package logic.function.ifelse;

import logic.Nameable;
import logic.function.evaluable.Evaluable;
import logic.function.voidfunction.VoidFunction;

/**
 * @author Steven Weston
 */
public class VoidIfElse<T extends Nameable>
		extends AbstractIfElse<T, Void, VoidIfElse<T>, VoidFunction<T, ?>>
		implements VoidFunction<T, VoidIfElse<T>> {

	public VoidIfElse(
			VoidFunction<T, ?> ifTrue,
			Evaluable<T, ?> condition,
			VoidFunction<T, ?> ifFalse
	) {
		super(ifTrue, condition, ifFalse);
	}

	@Override
	public VoidIfElse<T> copy() {
		return new VoidIfElse<>(ifTrue.copy(), condition.copy(), ifFalse.copy());
	}
}
