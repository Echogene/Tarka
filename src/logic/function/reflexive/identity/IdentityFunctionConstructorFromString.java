package logic.function.reflexive.identity;

import logic.Nameable;
import logic.function.factory.ReflexiveFunctionConstructorFromString;

/**
 * @author Steven Weston
 */
public class IdentityFunctionConstructorFromString<T extends Nameable>
		implements ReflexiveFunctionConstructorFromString<IdentityFunction<T>> {
	@Override
	public IdentityFunction<T> construct(String parameterName) {
		return new IdentityFunction<>(parameterName);
	}
}
