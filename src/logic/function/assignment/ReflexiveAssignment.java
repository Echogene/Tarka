package logic.function.assignment;

import logic.Nameable;
import logic.function.Function;
import logic.function.reflexive.ReflexiveFunction;

/**
 * @author Steven Weston
 */
public class ReflexiveAssignment<T extends Nameable>
		extends AbstractAssignment<T, T, ReflexiveAssignment<T>>
		implements ReflexiveFunction<T, ReflexiveAssignment<T>> {

	public ReflexiveAssignment(ReflexiveFunction<T, ?> evaluee, String assignee, Function<T, ?, ?> assingment) {
		super(evaluee, assignee, assingment);
	}

	@Override
	public ReflexiveAssignment<T> copy() {
		return new ReflexiveAssignment<>((ReflexiveFunction<T, ?>) evaluee.copy(), assignee, assignment.copy());
	}
}
