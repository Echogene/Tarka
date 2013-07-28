package logic.function.set.union;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.factory.multary.FunctionConstructorFromParameterList;
import logic.function.set.SetFunction;

import java.util.HashSet;
import java.util.List;

/**
 * @author Steven Weston
 */
public class UnionConstructorFromParameterList<T extends Nameable>
		implements FunctionConstructorFromParameterList<Union<T>, SetFunction<T>> {
	@Override
	public Union<T> construct(String operator, List<SetFunction<T>> parameterList) throws FactoryException {
		return new Union<>(new HashSet<>(parameterList));
	}
}
