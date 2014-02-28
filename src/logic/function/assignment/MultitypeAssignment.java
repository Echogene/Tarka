package logic.function.assignment;

import logic.Nameable;
import logic.function.Function;
import logic.function.MultitypeFunction;

/**
 * @author Steven Weston
 */
public class MultitypeAssignment<T extends Nameable>
		extends AbstractAssignment<T, Object, MultitypeAssignment<T>, Function<T, Object, ?>>
		implements MultitypeFunction<T, MultitypeAssignment<T>> {

	MultitypeAssignment(Function<T, Object, ?> evaluee, String assignee, Function<T, ?, ?> assignment) {
		super(evaluee, assignee, assignment);
	}

	@Override
	public MultitypeAssignment<T> copy() {
		return new MultitypeAssignment<>(evaluee.copy(), assignee, assignment.copy());
	}
}
