package logic.function.reflexiveset.union;

import logic.Nameable;
import logic.function.factory.FunctionConstructorFromParameterList;
import logic.function.reflexiveset.ReflexiveSetFunction;

import java.util.HashSet;
import java.util.List;

/**
 * @author Steven Weston
 */
public class UnionConstructorFromParameterList<T extends Nameable>
		implements FunctionConstructorFromParameterList<ReflexiveSetFunction<T>, Union<T>> {
	@Override
	public Union<T> construct(List<ReflexiveSetFunction<T>> parameters) {
		return new Union<>(new HashSet<>(parameters));
	}
}
