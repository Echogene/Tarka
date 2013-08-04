package logic.function.set.union;

import logic.Nameable;
import logic.function.Function;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.set.SetFunction;
import logic.function.set.SetFunctionFactory;
import logic.function.set.identity.SetIdentityFunction;
import logic.set.Set;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashSet;
import java.util.List;

/**
 * @author Steven Weston
 */
public class UnionFactory<T extends Nameable> extends SetFunctionFactory<T> {

	public UnionFactory() {
		super(getConstructors());
	}

	private static <T extends Nameable> List<ValidatorAndConstructor<Function<T, Set<T>>>> getConstructors() {
		throw new NotImplementedException();
	}

	public static <T extends Nameable> Union<T> createElement(String... parameters) {
		java.util.Set<SetFunction<T>> functions = new HashSet<>();
		for (String parameter : parameters) {
			SetFunction<T> function = new SetIdentityFunction<>(parameter);
			functions.add(function);
		}
		return new Union<>(functions);
	}
}
