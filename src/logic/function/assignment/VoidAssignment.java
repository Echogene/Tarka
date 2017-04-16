package logic.function.assignment;

import logic.Nameable;
import logic.function.Function;
import logic.function.voidfunction.VoidFunction;

/**
 * @author Steven Weston
 */
public class VoidAssignment<T extends Nameable>
		extends AbstractAssignment<T, Void, VoidAssignment<T>, VoidFunction<T, ?>>
		implements VoidFunction<T, VoidAssignment<T>> {

	public VoidAssignment(VoidFunction<T, ?> evaluee, String assignee, Function<T, ?, ?> assingment) {
		super(evaluee, assignee, assingment);
	}

	@Override
	public VoidAssignment<T> copy() {
		return new VoidAssignment<>(evaluee.copy(), assignee, assignment.copy());
	}
}
