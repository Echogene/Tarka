package logic.function.set.simple;

import logic.Nameable;
import logic.function.Function;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.identity.IdentityFunction;
import logic.function.set.SetFunctionFactory;
import logic.set.Set;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.HashSet;
import java.util.List;

/**
 * @author Steven Weston
 */
public class SimpleSetFactory<T extends Nameable> extends SetFunctionFactory<T> {

	public SimpleSetFactory() {
		super(getConstructors());
	}

	private static <T extends Nameable> List<ValidatorAndConstructor<Function<T, Set<T>>>> getConstructors() {
		throw new NotImplementedException();
	}

	public static <T extends Nameable> SimpleSet<T> createElement(String... memberStrings) {
		java.util.Set<ReflexiveFunction<T>> members = new HashSet<>();
		for (String memberString : memberStrings) {
			members.add(new IdentityFunction<>(memberString));
		}
		return new SimpleSet<>(members);
	}
}
