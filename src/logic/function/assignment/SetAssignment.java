package logic.function.assignment;

import logic.Nameable;
import logic.function.Function;
import logic.function.set.SetFunction;
import logic.set.Set;

/**
 * @author Steven Weston
 */
public class SetAssignment<T extends Nameable> extends Assignment<T, Set<T>> implements SetFunction<T> {

	public SetAssignment(SetFunction<T> evaluee, String assignee, Function<T, ?> assingment) {
		super(evaluee, assignee, assingment);
	}
}
