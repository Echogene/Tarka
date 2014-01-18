package logic.function.assignment;

import logic.Nameable;
import logic.function.Function;
import logic.function.evaluable.Evaluable;

/**
 * @author Steven Weston
 */
public class EvaluableAssignment<T extends Nameable>
		extends AbstractAssignment<T, Boolean, EvaluableAssignment<T>>
		implements Evaluable<T, EvaluableAssignment<T>> {

	public EvaluableAssignment(Evaluable<T, ?> evaluee, String assignee, Function<T, ?, ?> assignment) {
		super(evaluee, assignee, assignment);
	}

	@Override
	public EvaluableAssignment<T> copy() {
		return new EvaluableAssignment<>((Evaluable<T, ?>) evaluee.copy(), assignee, assignment.copy());
	}
}
