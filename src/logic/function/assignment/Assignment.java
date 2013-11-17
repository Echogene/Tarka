package logic.function.assignment;

import logic.Nameable;
import logic.function.Function;
import logic.model.universe.Universe;

/**
 * An assignment is a function that assigns a value to a variable.
 * @author Steven Weston
 */
class Assignment<D extends Nameable, C> implements Function<D, C> {
	private Function<D, C> evaluee;
	private String assignee;
	private Function<D, ?> assingment;

	public Assignment(Function<D, C> evaluee, String assignee, Function<D, ?> assingment) {
		this.evaluee    = evaluee;
		this.assignee   = assignee;
		this.assingment = assingment;
	}

	@Override
	public C evaluate(Universe<D> universe) throws Exception {
		universe.assignVariable(assignee);
		universe.setVariable(assignee, assingment.evaluate(universe));
		C result = evaluee.evaluate(universe);
		universe.unassignVariable(assignee);
		return result;
	}

	@Override
	public String toString() {
		return "(" + evaluee.toString() + " where " + assignee + " is " + assingment.toString() + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Assignment)) {
			return false;
		}
		Assignment other = (Assignment) o;
		return this.evaluee.equals(other.evaluee)
				&& this.assignee.equals(other.assignee)
				&& this.assingment.equals(other.assingment);
	}
}
