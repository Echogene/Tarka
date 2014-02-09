package logic.function.identity;

import logic.Nameable;
import logic.function.reflexive.ReflexiveFunction;
import util.FunctionUtils;

/**
 * @author Steven Weston
 */
public class MemberIdentityFunction<T extends Nameable>
		extends AbstractIdentityFunction<T, T, MemberIdentityFunction<T>, ReflexiveFunction<T, ?>>
		implements ReflexiveFunction<T, MemberIdentityFunction<T>> {

	public MemberIdentityFunction(String parameter) {
		super(parameter);
	}

	public MemberIdentityFunction(ReflexiveFunction<T, ?> function) {
		super(function);
	}

	MemberIdentityFunction(String parameter, ReflexiveFunction<T, ?> function) {
		super(parameter, function);
	}

	@Override
	public MemberIdentityFunction<T> copy() {
		return new MemberIdentityFunction<>(parameter, FunctionUtils.copy(function));
	}
}
