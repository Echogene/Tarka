package logic.function.assignment;

import logic.Nameable;
import logic.function.reflexive.ReflexiveFunction;

/**
 * @author Steven Weston
 */
public class ReflexiveAssignment<T extends Nameable> extends Assignment<T, T> implements ReflexiveFunction<T> {

	public ReflexiveAssignment(ReflexiveFunction<T> evaluee, String assignee, ReflexiveFunction<T> assingment) {
		super(evaluee, assignee, assingment);
	}
}
