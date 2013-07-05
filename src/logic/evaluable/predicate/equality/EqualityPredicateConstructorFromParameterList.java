package logic.evaluable.predicate.equality;

import logic.Nameable;
import logic.function.factory.FunctionConstructorFromParameterList;
import logic.function.reflexive.ReflexiveFunction;

import java.util.List;

/**
 * @author Steven Weston
 */
public class EqualityPredicateConstructorFromParameterList<T extends Nameable>
		implements FunctionConstructorFromParameterList<ReflexiveFunction<T>, EqualityPredicate<T>> {
	@Override
	public EqualityPredicate<T> construct(List<ReflexiveFunction<T>> parameters) {
		assert parameters.size() == 2;
		return new EqualityPredicate<>(parameters.get(0), parameters.get(1));
	}
}
