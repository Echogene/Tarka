package logic.function.ifelse;

import logic.Nameable;
import logic.function.evaluable.Evaluable;

/**
 * @author Steven Weston
 */
public class EvaluableIfElse<T extends Nameable>
		extends AbstractIfElse<T, Boolean, EvaluableIfElse<T>, Evaluable<T, ?>>
		implements Evaluable<T, EvaluableIfElse<T>> {

	public EvaluableIfElse(
			Evaluable<T, ?> ifTrue,
			Evaluable<T, ?> condition,
			Evaluable<T, ?> ifFalse
	) {
		super(ifTrue, condition, ifFalse);
	}

	@Override
	public EvaluableIfElse<T> copy() {
		return new EvaluableIfElse<>(ifTrue.copy(), condition.copy(), ifFalse.copy());
	}
}
