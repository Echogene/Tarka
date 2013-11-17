package logic.function.assignment;

import logic.Nameable;
import logic.function.Function;
import logic.function.evaluable.Evaluable;

/**
 * @author Steven Weston
 */
public class EvaluableAssignment<T extends Nameable> extends Assignment<T, Boolean> implements Evaluable<T> {

	public EvaluableAssignment(Evaluable<T> evaluee, String assignee, Function<T, ?> assingment) {
		super(evaluee, assignee, assingment);
	}
}
