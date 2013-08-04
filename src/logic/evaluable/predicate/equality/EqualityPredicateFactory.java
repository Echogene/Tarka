package logic.evaluable.predicate.equality;

import logic.Nameable;
import logic.evaluable.predicate.PredicateFactory;
import logic.function.Function;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.reflexive.identity.IdentityFunction;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * A {@code Factory} for creating {@code EqualityPredicate}s.
 * @author Steven Weston
 */
public class EqualityPredicateFactory<T extends Nameable> extends PredicateFactory<T> {
	/**
	 * @param equorString The string representing the equor of the equals.
	 * @param equandString The string representing the equand of the equals.
	 * @param <T> The class of the universe in which to evaluate the equals.
	 * @return An equality predicate of the form 'equorString = equandString'.
	 */
	public static <T extends Nameable> EqualityPredicate<T> createElement(String equorString, String equandString) {
		IdentityFunction<T> equorFunction = new IdentityFunction<>(equorString);
		IdentityFunction<T> equandFunction = new IdentityFunction<>(equandString);
		return new EqualityPredicate<>(equorFunction, equandFunction);
	}

	public EqualityPredicateFactory() {
		super(getConstructors());
	}

	private static <T extends Nameable> List<ValidatorAndConstructor<Function<T, Boolean>>> getConstructors() {
		throw new NotImplementedException();
	}
}
