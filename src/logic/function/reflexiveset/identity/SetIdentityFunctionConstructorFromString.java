package logic.function.reflexiveset.identity;

import logic.Nameable;
import logic.function.factory.ReflexiveFunctionConstructorFromString;

/**
 * @author Steven Weston
 */
public class SetIdentityFunctionConstructorFromString<T extends Nameable>
		implements ReflexiveFunctionConstructorFromString<SetIdentityFunction<T>> {
	@Override
	public SetIdentityFunction<T> construct(String parameterName) {
		return new SetIdentityFunction<>(parameterName);
	}
}
