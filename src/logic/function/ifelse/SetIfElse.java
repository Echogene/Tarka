package logic.function.ifelse;

import logic.Nameable;
import logic.function.evaluable.Evaluable;
import logic.function.set.SetFunction;
import logic.set.Set;

/**
 * @author Steven Weston
 */
public class SetIfElse<T extends Nameable>
		extends AbstractIfElse<T, Set<T>, SetIfElse<T>, SetFunction<T, ?>>
		implements SetFunction<T, SetIfElse<T>> {

	public SetIfElse(
			SetFunction<T, ?> ifTrue,
			Evaluable<T, ?> condition,
			SetFunction<T, ?> ifFalse
	) {
		super(ifTrue, condition, ifFalse);
	}

	@Override
	public SetIfElse<T> copy() {
		return new SetIfElse<>(ifTrue.copy(), condition.copy(), ifFalse.copy());
	}
}
