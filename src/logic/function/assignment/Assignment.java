package logic.function.assignment;

import logic.Nameable;
import logic.function.Function;
import logic.model.universe.Universe;

import static java.text.MessageFormat.format;

/**
 * An assignment is a function that assigns a value to a variable within a scope.
 * @author Steven Weston
 */
class Assignment<D extends Nameable, C> implements Function<D, C> {

	public static final String WHERE = "where";
	public static final String IS = "is";
	private final Function<D, C> evaluee;
	private final String assignee;
	private final Function<D, ?> assingment;

	Assignment(Function<D, C> evaluee, String assignee, Function<D, ?> assingment) {
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
		return format("({0} {1} {2} {3} {4})", evaluee, WHERE, assignee, IS, assingment);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Assignment)) {
			return false;
		}
		Assignment other = (Assignment) o;
		return this.evaluee.equals(other.evaluee)
				&& this.assignee.equals(other.assignee)
				&& this.assingment.equals(other.assingment)
				&& (this.getClass().isInstance(other)
					|| other.getClass().isInstance(this));
	}

	@Override
	public int hashCode() {
		int result = evaluee != null ? evaluee.hashCode() : 0;
		result = 31 * result + (assignee != null ? assignee.hashCode() : 0);
		result = 31 * result + (assingment != null ? assingment.hashCode() : 0);
		return result;
	}
}
