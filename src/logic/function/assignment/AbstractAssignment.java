package logic.function.assignment;

import logic.Nameable;
import logic.function.Function;
import logic.model.Model;
import util.FunctionUtils;

import java.util.Map;

import static java.text.MessageFormat.format;

/**
 * An assignment is a function that assigns a value to a variable within a scope.
 * @author Steven Weston
 */
abstract class AbstractAssignment<D extends Nameable, C, F extends AbstractAssignment<D, C, F, G>, G extends Function<D, C, ?>>
		implements Function<D, C, F> {

	public static final String WHERE = "where";
	public static final String IS = "is";
	final G evaluee;
	final String assignee;
	final Function<D, ?, ?> assignment;

	AbstractAssignment(G evaluee, String assignee, Function<D, ?, ?> assignment) {
		this.evaluee    = evaluee;
		this.assignee   = assignee;
		this.assignment = assignment;
	}

	@Override
	public C evaluate(Model<D, ?, ?> model) throws Exception {
		FunctionUtils.reduce(evaluee, assignee, assignment);
		return evaluee.evaluate(model);
	}

	@Override
	public void reduce(Map<String, Function<D, ?, ?>> reductions) {
		evaluee.reduce(reductions);
		assignment.reduce(reductions);
	}

	@Override
	public String toString() {
		return format("({0} {1} {2} {3} {4})", evaluee, WHERE, assignee, IS, assignment);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof AbstractAssignment)) {
			return false;
		}
		AbstractAssignment other = (AbstractAssignment) o;
		return this.evaluee.equals(other.evaluee)
				&& this.assignee.equals(other.assignee)
				&& this.assignment.equals(other.assignment)
				&& (this.getClass().isInstance(other)
					|| other.getClass().isInstance(this));
	}

	@Override
	public int hashCode() {
		int result = evaluee != null ? evaluee.hashCode() : 0;
		result = 31 * result + (assignee != null ? assignee.hashCode() : 0);
		result = 31 * result + (assignment != null ? assignment.hashCode() : 0);
		return result;
	}
}
