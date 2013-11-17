package logic.function.assignment;

import logic.Nameable;
import logic.function.Function;
import logic.function.reflexive.ReflexiveFunction;

/**
 * @author Steven Weston
 */
public class ReflexiveAssignment<T extends Nameable> extends Assignment<T, T> implements ReflexiveFunction<T> {

	public ReflexiveAssignment(ReflexiveFunction<T> evaluee, String assignee, Function<T, ?> assingment) {
		super(evaluee, assignee, assingment);
	}
}
