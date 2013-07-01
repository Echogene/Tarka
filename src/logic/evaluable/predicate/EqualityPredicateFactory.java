package logic.evaluable.predicate;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.BinaryValidator;
import logic.function.factory.ValidationResult;
import logic.function.reflexive.IdentityFunction;
import logic.function.reflexive.ReflexiveFunction;
import reading.lexing.Token;

import java.util.Arrays;
import java.util.List;

import static logic.function.factory.ValidationResult.ValidationType.FUNCTION;
import static logic.function.factory.ValidationResult.ValidationType.TOKEN;

/**
 * A {@code Factory} for creating {@code EqualityPredicate}s.
 * @author Steven Weston
 */
public class EqualityPredicateFactory<T extends Nameable> implements PredicateFactory<T> {
	final BinaryValidator binaryValidator;

	public EqualityPredicateFactory() {
		this.binaryValidator = new BinaryValidator(Arrays.asList(EqualityPredicate.EQUALITY_SYMBOL));
	}

	@Override
	public Function<T, Boolean> createElement(List<Token> tokens) throws FactoryException {
		return createElement(tokens, null);
	}

	@Override
	public Function<T, Boolean> createElement(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		ValidationResult result = binaryValidator.validate(tokens, functions);
		if (result.isValid()) {
			ReflexiveFunction<T> parameter1 = null;
			if (result.get(0).equals(TOKEN)) {
				parameter1 = new IdentityFunction<>(tokens.get(0).getValue());
			} else if (result.get(0).equals(FUNCTION)) {
				parameter1 = (ReflexiveFunction<T>) functions.get(0);
			}
			ReflexiveFunction<T> parameter2 = null;
			if (result.get(1).equals(TOKEN)) {
				parameter2 = new IdentityFunction<>(tokens.get(result.get(0).equals(TOKEN) ? 2 : 3).getValue());
			} else if (result.get(1).equals(FUNCTION)) {
				parameter2 = (ReflexiveFunction<T>) functions.get(1);
			}
			return new EqualityPredicate<>(parameter1, parameter2);
		}
		throw new FactoryException("Could not create EqualityPredicate");
	}
}
