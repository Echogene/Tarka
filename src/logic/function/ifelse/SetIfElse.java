package logic.function.ifelse;

import logic.Nameable;
import logic.function.evaluable.Evaluable;
import logic.function.set.SetFunction;
import logic.set.Set;

/**
 * @author Steven Weston
 */
public class SetIfElse<T extends Nameable> extends IfElse<T, Set<T>> implements SetFunction<T> {

	public SetIfElse(Evaluable<T> condition, SetFunction<T> ifTrue, SetFunction<T> ifFalse) {
		super(condition, ifTrue, ifFalse);
	}

	@Override
	public SetIfElse<T> copy() {
		return new SetIfElse<>(condition.copy(), (SetFunction<T>) ifTrue, (SetFunction<T>) ifFalse);
	}
}
