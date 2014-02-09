package logic.function.reference;

import logic.Nameable;
import logic.function.reflexive.ReflexiveFunction;

/**
 * @author Steven Weston
 */
public class ReflexiveFunctionReference<T extends Nameable>
		extends AbstractFunctionReference<T, T, ReflexiveFunctionReference<T>, ReflexiveFunction<T, ?>>
		implements ReflexiveFunction<T, ReflexiveFunctionReference<T>> {

	public ReflexiveFunctionReference() {super();}

	public ReflexiveFunctionReference(ReflexiveFunction<T, ?> referee) {
		super(referee);
	}

	@Override
	public ReflexiveFunctionReference<T> copy() {
		return new ReflexiveFunctionReference<>(referee);
	}
}
