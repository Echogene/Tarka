package logic.function.assignment;

import logic.Nameable;
import logic.function.Function;
import logic.function.set.SetFunction;
import logic.set.Set;

/**
 * @author Steven Weston
 */
public class SetAssignment<T extends Nameable>
		extends AbstractAssignment<T, Set<T>, SetAssignment<T>, SetFunction<T, ?>>
		implements SetFunction<T, SetAssignment<T>> {

	public SetAssignment(SetFunction<T, ?> evaluee, String assignee, Function<T, ?, ?> assingment) {
		super(evaluee, assignee, assingment);
	}

	@Override
	public SetAssignment<T> copy() {
		return new SetAssignment<>(evaluee.copy(), assignee, assignment.copy());
	}
}
