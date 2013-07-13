package logic.evaluable.predicate.membership;

import logic.Nameable;
import logic.evaluable.predicate.PredicateFactory;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.ValidationResult;
import logic.function.factory.binary.BinaryConstructor;
import logic.function.factory.binary.BinaryValidator;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.identity.IdentityFunctionConstructorFromString;
import logic.function.reflexiveset.ReflexiveSetFunction;
import logic.function.reflexiveset.identity.SetIdentityFunctionConstructorFromString;
import reading.lexing.Token;

import java.util.Collections;
import java.util.List;

/**
 * @author Steven Weston
 */
public class MembershipPredicateFactory<T extends Nameable> extends PredicateFactory<T> {
	private BinaryValidator validator;
	private BinaryConstructor<MembershipPredicate<T>, ReflexiveFunction<T>, ReflexiveSetFunction<T>> constructor;

	public MembershipPredicateFactory() {
		validator = new BinaryValidator(
				ReflexiveFunction.class,
				Collections.singletonList(MembershipPredicate.MEMBERSHIP_STRING),
				ReflexiveSetFunction.class);
		constructor = new BinaryConstructor<>(
				new MembershipPredicateConstructorFromTwoParameters<>(),
				new IdentityFunctionConstructorFromString<T>(),
				new SetIdentityFunctionConstructorFromString<T>()
		);
	}

	@Override
	public Function<T, Boolean> createElement(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		ValidationResult result = validator.validate(tokens, functions);
		if (result.isValid()) {
			return constructor.construct(result, tokens, functions);
		}
		throw new FactoryException("Could not create MembershipPredicate");
	}
}
