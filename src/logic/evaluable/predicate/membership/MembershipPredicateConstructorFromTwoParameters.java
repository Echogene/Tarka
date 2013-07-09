package logic.evaluable.predicate.membership;

import logic.Nameable;
import logic.function.factory.FunctionConstructorFromTwoParameters;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexiveset.ReflexiveSetFunction;

/**
 * @author Steven Weston
 */
public class MembershipPredicateConstructorFromTwoParameters<T extends Nameable>
		implements FunctionConstructorFromTwoParameters<
			MembershipPredicate<T>,
			ReflexiveFunction<T>,
			ReflexiveSetFunction<T>
		> {
	@Override
	public MembershipPredicate<T> construct(ReflexiveFunction<T> parameter1, String operator, ReflexiveSetFunction<T> parameter2) {
		return new MembershipPredicate<>(parameter1, parameter2);
	}
}
