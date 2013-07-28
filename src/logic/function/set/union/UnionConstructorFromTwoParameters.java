package logic.function.set.union;

import logic.Nameable;
import logic.function.factory.binary.FunctionConstructorFromTwoParameters;
import logic.function.set.SetFunction;

import java.util.HashSet;

/**
 * @author Steven Weston
 */
public class UnionConstructorFromTwoParameters<T extends Nameable>
		implements FunctionConstructorFromTwoParameters<Union<T>, SetFunction<T>, SetFunction<T>> {
	@Override
	public Union<T> construct(SetFunction<T> parameter1, String operator, SetFunction<T> parameter2) {
		HashSet<SetFunction<T>> parameters = new HashSet<>();
		parameters.add(parameter1);
		parameters.add(parameter2);
		return new Union<>(parameters);
	}
}
