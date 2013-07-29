package logic.function.set.simple;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.factory.multary.FunctionConstructorFromParameterList;
import logic.function.reflexive.ReflexiveFunction;

import java.util.HashSet;
import java.util.List;

/**
 * @author Steven Weston
 */
public class SimpleSetConstructorFromParameterList<T extends Nameable>
		implements FunctionConstructorFromParameterList<SimpleSet<T>, ReflexiveFunction<T>> {
	@Override
	public SimpleSet<T> construct(String operator, List<ReflexiveFunction<T>> parameterList) throws FactoryException {
		java.util.Set<ReflexiveFunction<T>> parameterSet = new HashSet<>(parameterList);
		return new SimpleSet<>(parameterSet);
	}
}
