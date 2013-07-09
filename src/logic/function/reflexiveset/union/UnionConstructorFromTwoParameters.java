package logic.function.reflexiveset.union;

import logic.Nameable;
import logic.function.factory.FunctionConstructorFromTwoParameters;
import logic.function.reflexiveset.ReflexiveSetFunction;

import java.util.HashSet;

/**
 * @author Steven Weston
 */
public class UnionConstructorFromTwoParameters<T extends Nameable>
		implements FunctionConstructorFromTwoParameters<Union<T>, ReflexiveSetFunction<T>, ReflexiveSetFunction<T>> {
	@Override
	public Union<T> construct(ReflexiveSetFunction<T> parameter1, String operator, ReflexiveSetFunction<T> parameter2) {
		HashSet<ReflexiveSetFunction<T>> parameters = new HashSet<>();
		parameters.add(parameter1);
		parameters.add(parameter2);
		return new Union<>(parameters);
	}
}
