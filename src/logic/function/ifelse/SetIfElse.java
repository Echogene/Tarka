package logic.function.ifelse;

import logic.Nameable;
import logic.function.evaluable.Evaluable;
import logic.function.set.SetFunction;
import logic.set.Set;

/**
 * @author Steven Weston
 */
public class SetIfElse<T extends Nameable>
		extends AbstractIfElse<T, Set<T>, SetIfElse<T>>
		implements SetFunction<T, SetIfElse<T>> {

	public SetIfElse(Evaluable<T, ?> condition, SetFunction<T, ?> ifTrue, SetFunction<T, ?> ifFalse) {
		super(condition, ifTrue, ifFalse);
	}

	@Override
	public SetIfElse<T> copy() {
		return new SetIfElse<>(
				condition.copy(),
				(SetFunction<T, ?>) ifTrue.copy(),
				(SetFunction<T, ?>) ifFalse.copy()
		);
	}
}
