package logic.identity;

import logic.Nameable;
import logic.function.reflexive.ReflexiveFunction;

/**
 * @author Steven Weston
 */
public class MemberIdentityFunction<T extends Nameable> extends IdentityFunction<T, T> implements ReflexiveFunction<T> {

	public MemberIdentityFunction(String parameter) {
		super(parameter);
	}

	public MemberIdentityFunction(ReflexiveFunction<T> function) {
		super(function);
	}
}
