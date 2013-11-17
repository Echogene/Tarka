package logic.function.assignment;

import logic.Nameable;
import logic.function.set.SetFunction;
import logic.set.Set;

/**
 * @author Steven Weston
 */
public class SetAssignment<T extends Nameable> extends Assignment<T, Set<T>> {

	public SetAssignment(SetFunction<T> evaluee, String assignee, SetFunction<T> assingment) {
		super(evaluee, assignee, assingment);
	}
}
