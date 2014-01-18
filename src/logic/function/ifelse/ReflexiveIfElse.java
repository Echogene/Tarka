package logic.function.ifelse;

import logic.Nameable;
import logic.function.evaluable.Evaluable;
import logic.function.reflexive.ReflexiveFunction;

/**
 * @author Steven Weston
 */
public class ReflexiveIfElse<T extends Nameable>
		extends AbstractIfElse<T, T, ReflexiveIfElse<T>>
		implements ReflexiveFunction<T, ReflexiveIfElse<T>> {

	public ReflexiveIfElse(Evaluable<T, ?> condition, ReflexiveFunction<T, ?> ifTrue, ReflexiveFunction<T, ?> ifFalse) {
		super(condition, ifTrue, ifFalse);
	}

	@Override
	public ReflexiveIfElse<T> copy() {
		return new ReflexiveIfElse<>(
				condition.copy(),
				(ReflexiveFunction<T, ?>) ifTrue.copy(),
				(ReflexiveFunction<T, ?>) ifFalse.copy()
		);
	}
}
