package logic.evaluable.predicate.membership;

import logic.Nameable;
import logic.function.factory.binary.FunctionConstructorFromTwoParameters;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.set.SetFunction;

/**
 * @author Steven Weston
 */
public class MembershipPredicateConstructorFromTwoParameters<T extends Nameable>
		implements FunctionConstructorFromTwoParameters<
			MembershipPredicate<T>,
			ReflexiveFunction<T>,
		SetFunction<T>
		> {
	@Override
	public MembershipPredicate<T> construct(ReflexiveFunction<T> parameter1, String operator, SetFunction<T> parameter2) {
		return new MembershipPredicate<>(parameter1, parameter2);
	}
}
