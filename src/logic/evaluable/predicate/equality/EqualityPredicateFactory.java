package logic.evaluable.predicate.equality;

import logic.Nameable;
import logic.evaluable.predicate.PredicateFactory;
import logic.function.Function;
import logic.function.factory.binary.BinaryValidator;
import logic.function.factory.construction.Constructor;
import logic.function.factory.construction.FunctionConvertor;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.factory.validation.Validator;
import logic.function.factory.validation.results.ValidationResult;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.identity.IdentityFunction;
import logic.function.reflexive.identity.IdentityFunctionFactory;

import java.util.Arrays;
import java.util.List;

import static logic.evaluable.predicate.equality.EqualityPredicate.EQUALITY_SYMBOL;

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
		Validator validator = new BinaryValidator(Arrays.asList(EQUALITY_SYMBOL));
		return Arrays.asList(
				new ValidatorAndConstructor<>(
						validator,
						new EqualityPredicateConstructor<T>()
				)
		);
	}

	private static class EqualityPredicateConstructor<T extends Nameable> implements Constructor<Function<T, Boolean>> {

		private final FunctionConvertor<IdentityFunction<T>, T> convertor;

		private EqualityPredicateConstructor() {
			this.convertor = new FunctionConvertor<>(new IdentityFunctionFactory<T>());
		}

		@Override
		public Function<T, Boolean> construct(List<ValidationResult> results) {
			ReflexiveFunction<T> firstFunction = convertor.convert(results.get(1));
			ReflexiveFunction<T> secondFunction = convertor.convert(results.get(3));
			return new EqualityPredicate<>(firstFunction, secondFunction);
		}
	}
}
