package logic.function.set.identity;

import logic.Nameable;
import logic.function.factory.ConstructorFromString;

/**
 * @author Steven Weston
 */
public class SetIdentityFunctionConstructorFromString<T extends Nameable>
		implements ConstructorFromString<SetIdentityFunction<T>> {
	@Override
	public SetIdentityFunction<T> construct(String parameterName) {
		return new SetIdentityFunction<>(parameterName);
	}
}
