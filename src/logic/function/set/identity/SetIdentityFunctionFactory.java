package logic.function.set.identity;

import logic.Nameable;
import logic.function.Function;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.set.SetFunctionFactory;
import logic.set.Set;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * @author Steven Weston
 */
public class SetIdentityFunctionFactory<T extends Nameable> extends SetFunctionFactory<T> {
	public SetIdentityFunctionFactory() {
		super(getConstructors());
	}

	private static <T extends Nameable> List<ValidatorAndConstructor<Function<T, Set<T>>>> getConstructors() {
		throw new NotImplementedException();
	}
}