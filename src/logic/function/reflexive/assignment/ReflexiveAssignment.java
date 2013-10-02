package logic.function.reflexive.assignment;

import logic.Nameable;
import logic.function.reflexive.ReflexiveFunction;
import logic.model.universe.Universe;

/**
 * An assignment is a function that assigns a value to a variable.
 * @author Steven Weston
 */
public class ReflexiveAssignment<T extends Nameable> implements ReflexiveFunction<T> {
	private ReflexiveFunction<T> evaluee;
	private String assignee;
	private ReflexiveFunction<T> assingment;

	public ReflexiveAssignment(ReflexiveFunction<T> evaluee, String assignee, ReflexiveFunction<T> assingment) {
		this.evaluee    = evaluee;
		this.assignee   = assignee;
		this.assingment = assingment;
	}

	@Override
	public T evaluate(Universe<T> universe) throws Exception {
		universe.assignVariable(assignee);
		universe.setVariable(assignee, assingment.evaluate(universe));
		T result = evaluee.evaluate(universe);
		universe.unassignVariable(assignee);
		return result;
	}

	@Override
	public String toString() {
		return "(" + evaluee.toString() + " where " + assignee + " is " + assingment.toString() + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof ReflexiveAssignment)) {
			return false;
		}
		ReflexiveAssignment other = (ReflexiveAssignment) o;
		return this.evaluee.equals(other.evaluee)
				&& this.assignee.equals(other.assignee)
				&& this.assingment.equals(other.assingment);
	}
}
