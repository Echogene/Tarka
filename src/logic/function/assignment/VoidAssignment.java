package logic.function.assignment;

import logic.Nameable;
import logic.function.Function;
import logic.function.voidfunction.VoidFunction;

/**
 * @author Steven Weston
 */
public class VoidAssignment<T extends Nameable> extends Assignment<T, Void> implements VoidFunction<T> {

	public VoidAssignment(VoidFunction<T> evaluee, String assignee, Function<T, ?> assingment) {
		super(evaluee, assignee, assingment);
	}
}
