package logic.function.ifelse;

import logic.Nameable;
import logic.function.evaluable.Evaluable;

/**
 * @author Steven Weston
 */
public class EvaluableIfElse<T extends Nameable>
		extends AbstractIfElse<T, Boolean, EvaluableIfElse<T>, Evaluable<T, ?>>
		implements Evaluable<T, EvaluableIfElse<T>> {

	public EvaluableIfElse(Evaluable<T, ?> condition, Evaluable<T, ?> ifTrue, Evaluable<T, ?> ifFalse) {
		super(condition, ifTrue, ifFalse);
	}

	@Override
	public EvaluableIfElse<T> copy() {
		return new EvaluableIfElse<>(condition.copy(), ifTrue.copy(), ifFalse.copy());
	}
}
