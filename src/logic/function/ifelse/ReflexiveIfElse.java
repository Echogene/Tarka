package logic.function.ifelse;

import logic.Nameable;
import logic.function.evaluable.Evaluable;
import logic.function.reflexive.ReflexiveFunction;

/**
 * @author Steven Weston
 */
public class ReflexiveIfElse<T extends Nameable>
		extends AbstractIfElse<T, T, ReflexiveIfElse<T>, ReflexiveFunction<T, ?>>
		implements ReflexiveFunction<T, ReflexiveIfElse<T>> {

	public ReflexiveIfElse(
			ReflexiveFunction<T, ?> ifTrue,
			Evaluable<T, ?> condition,
			ReflexiveFunction<T, ?> ifFalse
	) {
		super(ifTrue, condition, ifFalse);
	}

	@Override
	public ReflexiveIfElse<T> copy() {
		return new ReflexiveIfElse<>(ifTrue.copy(), condition.copy(), ifFalse.copy());
	}
}
