package logic.function.reflexiveset.union;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.factory.multary.FunctionConstructorFromParameterList;
import logic.function.reflexiveset.ReflexiveSetFunction;

import java.util.HashSet;
import java.util.List;

/**
 * @author Steven Weston
 */
public class UnionConstructorFromParameterList<T extends Nameable>
		implements FunctionConstructorFromParameterList<Union<T>, ReflexiveSetFunction<T>> {
	@Override
	public Union<T> construct(String operator, List<ReflexiveSetFunction<T>> parameterList) throws FactoryException {
		return new Union<>(new HashSet<>(parameterList));
	}
}
