package logic.function.reflexive.assignment;

import logic.Nameable;
import logic.function.Function;
import logic.function.reflexive.ReflexiveFunction;
import logic.model.universe.Universe;

/**
 * An assignment is a function that assigns a value to a variable.
 * @author Steven Weston
 */
public class Assignment<T extends Nameable> implements Function<T, Object> {
	private Function<T, ?> evaluee;
	private String assignee;
	private ReflexiveFunction<T> assingment;

	public Assignment(Function<T, ?> evaluee, String assignee, ReflexiveFunction<T> assingment) {
		this.evaluee    = evaluee;
		this.assignee   = assignee;
		this.assingment = assingment;
	}

	@Override
	public Object evaluate(Universe<T> universe) throws Exception {
		universe.assignVariable(assignee);
		universe.setVariable(assignee, assingment.evaluate(universe));
		Object result = evaluee.evaluate(universe);
		universe.unassignVariable(assignee);
		return result;
	}

	@Override
	public String toString() {
		return "(" + evaluee.toString() + " where " + assignee + " is " + assingment.toString() + ")";
	}

	@Override
	public String getName() {
		return toString();
	}
}
