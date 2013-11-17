package logic.function.assignment;

import logic.Nameable;
import logic.function.evaluable.Evaluable;

/**
 * @author Steven Weston
 */
public class EvaluableAssignment<T extends Nameable> extends Assignment<T, Boolean> implements Evaluable<T> {

	public EvaluableAssignment(Evaluable<T> evaluee, String assignee, Evaluable<T> assingment) {
		super(evaluee, assignee, assingment);
	}
}
