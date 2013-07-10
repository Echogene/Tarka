package logic.evaluable.predicate.equality;

import logic.Nameable;
import logic.function.factory.binary.FunctionConstructorFromTwoParameters;
import logic.function.reflexive.ReflexiveFunction;

/**
 * @author Steven Weston
 */
public class EqualityPredicateConstructorFromTwoParameters<T extends Nameable>
		implements FunctionConstructorFromTwoParameters<EqualityPredicate<T>, ReflexiveFunction<T>, ReflexiveFunction<T>> {
	@Override
	public EqualityPredicate<T> construct(ReflexiveFunction<T> parameter1, String operator, ReflexiveFunction<T> parameter2) {
		return new EqualityPredicate<>(parameter1, parameter2);
	}
}
