package logic.evaluable.predicate.membership;

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
import logic.function.set.SetFunction;
import logic.function.set.identity.SetIdentityFunction;
import logic.function.set.identity.SetIdentityFunctionFactory;

import java.util.Arrays;
import java.util.List;

/**
 * @author Steven Weston
 */
public class MembershipPredicateFactory<T extends Nameable> extends PredicateFactory<T> {

	public MembershipPredicateFactory() {
		super(getConstructors());
	}

	private static <T extends Nameable> List<ValidatorAndConstructor<Function<T, Boolean>>> getConstructors() {
		Validator validator = new BinaryValidator(
				ReflexiveFunction.class,
				Arrays.asList(MembershipPredicate.MEMBERSHIP_SYMBOL),
				SetFunction.class
		);
		return Arrays.asList(
				new ValidatorAndConstructor<>(
						validator,
						new MembershipPredicateConstructor<T>()
				)
		);
	}

	private static class MembershipPredicateConstructor<T extends Nameable> implements Constructor<Function<T, Boolean>> {

		private final FunctionConvertor<IdentityFunction<T>, T> identityFunctionConstructor;
		private final FunctionConvertor<SetIdentityFunction<T>, T> setIdentityFunctionConstructor;

		private MembershipPredicateConstructor() {
			this.identityFunctionConstructor = new FunctionConvertor<>(new IdentityFunctionFactory<>());
			this.setIdentityFunctionConstructor = new FunctionConvertor<>(new SetIdentityFunctionFactory<>());
		}

		@Override
		public Function<T, Boolean> construct(List<ValidationResult> results) {
			ReflexiveFunction<T> firstFunction = identityFunctionConstructor.construct(results.get(1));
			SetFunction<T> secondFunction = setIdentityFunctionConstructor.construct(results.get(3));
			return new MembershipPredicate<>(firstFunction, secondFunction);
		}
	}
}
