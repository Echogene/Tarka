package logic.evaluable.predicate.equality;

import logic.Nameable;
import logic.evaluable.predicate.PredicateFactory;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.BinaryConstructor;
import logic.function.factory.BinaryValidator;
import logic.function.factory.ValidationResult;
import logic.function.reflexive.IdentityFunction;
import logic.function.reflexive.IdentityFunctionConstructorFromString;
import logic.function.reflexive.ReflexiveFunction;
import reading.lexing.Token;

import java.util.Arrays;
import java.util.List;

/**
 * A {@code Factory} for creating {@code EqualityPredicate}s.
 * @author Steven Weston
 */
public class EqualityPredicateFactory<T extends Nameable> implements PredicateFactory<T> {
	final BinaryValidator binaryValidator;
	private BinaryConstructor<EqualityPredicate<T>, ReflexiveFunction<T>, ReflexiveFunction<T>> binaryConstructor;

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
		this.binaryValidator = new BinaryValidator(Arrays.asList(EqualityPredicate.EQUALITY_SYMBOL));
		this.binaryConstructor = new BinaryConstructor<>(
				new EqualityPredicateConstructorFromTwoParameters<>(),
				new IdentityFunctionConstructorFromString<T>(),
				new IdentityFunctionConstructorFromString<T>()
		);
	}

	@Override
	public Function<T, Boolean> createElement(List<Token> tokens) throws FactoryException {
		return createElement(tokens, null);
	}

	@Override
	public Function<T, Boolean> createElement(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		ValidationResult result = binaryValidator.validate(tokens, functions);
		if (result.isValid()) {
			return binaryConstructor.construct(result, tokens, functions);
		}
		throw new FactoryException("Could not create EqualityPredicate");
	}
}
