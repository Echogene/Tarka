package logic.evaluable.predicate.membership;

import logic.Nameable;
import logic.evaluable.predicate.PredicateFactory;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.BinaryValidator;
import logic.function.factory.ValidationResult;
import logic.function.reflexive.IdentityFunction;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexiveset.ReflexiveSetFunction;
import logic.function.reflexiveset.identity.SetIdentityFunction;
import reading.lexing.Token;

import java.util.Collections;
import java.util.List;

import static logic.function.factory.ValidationResult.ValidationType.FUNCTION;
import static logic.function.factory.ValidationResult.ValidationType.TOKEN;

/**
 * @author Steven Weston
 */
public class MembershipPredicateFactory<T extends Nameable> implements PredicateFactory<T> {
	private BinaryValidator validator;

	public MembershipPredicateFactory() {
		validator = new BinaryValidator(
				ReflexiveFunction.class,
				Collections.singletonList(MembershipPredicate.MEMBERSHIP_STRING),
				ReflexiveSetFunction.class);
	}

	@Override
	public Function<T, Boolean> createElement(List<Token> tokens) throws FactoryException {
		return createElement(tokens, null);
	}

	@Override
	public Function<T, Boolean> createElement(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		ValidationResult result = validator.validate(tokens, functions);
		if (result.isValid()) {
			ReflexiveFunction<T> member = null;
			if (result.get(0).equals(TOKEN)) {
				member = new IdentityFunction<>(tokens.get(0).getValue());
			} else if (result.get(0).equals(FUNCTION)) {
				member = (ReflexiveFunction<T>) functions.get(0);
			}
			ReflexiveSetFunction<T> set = null;
			if (result.get(1).equals(TOKEN)) {
				set = new SetIdentityFunction<>(tokens.get(result.get(0).equals(TOKEN) ? 2 : 3).getValue());
			} else if (result.get(1).equals(FUNCTION)) {
				set = (ReflexiveSetFunction<T>) functions.get(1);
			}
			return new MembershipPredicate<>(member, set);
		}
		throw new FactoryException("Could not create MembershipPredicate");
	}
}
