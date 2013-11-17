package logic.function.evaluable.predicate.membership;

import logic.Nameable;
import logic.function.evaluable.predicate.PredicateFactory;
import logic.function.factory.binary.BinaryValidator;
import logic.function.factory.construction.Constructor;
import logic.function.factory.construction.FunctionConvertor;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.factory.oldvalidation.SimpleLogicValidator;
import logic.function.factory.oldvalidation.results.ValidationResult;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.identity.IdentityFunction;
import logic.function.reflexive.identity.IdentityFunctionFactory;
import logic.function.set.SetFunction;
import logic.function.set.identity.SetIdentityFunction;

import java.util.Arrays;
import java.util.List;

/**
 * @author Steven Weston
 */
public class MembershipPredicateFactory<T extends Nameable> extends PredicateFactory<T, MembershipPredicate<T>> {

	public MembershipPredicateFactory() {
		super(getConstructors());
	}

	private static <T extends Nameable> List<ValidatorAndConstructor<MembershipPredicate<T>>> getConstructors() {
		SimpleLogicValidator validator = new BinaryValidator(
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

	private static class MembershipPredicateConstructor<T extends Nameable> implements Constructor<MembershipPredicate<T>> {

		private final FunctionConvertor<IdentityFunction<T>, T> identityFunctionConstructor;
		private final FunctionConvertor<SetIdentityFunction<T>, T> setIdentityFunctionConstructor;

		private MembershipPredicateConstructor() {
			this.identityFunctionConstructor = new FunctionConvertor<>(new IdentityFunctionFactory<T>());
			this.setIdentityFunctionConstructor = new FunctionConvertor<>(new SetIdentityFunctionFactory<T>());
		}

		@Override
		public MembershipPredicate<T> construct(List<ValidationResult> results) {
			ReflexiveFunction<T> firstFunction = identityFunctionConstructor.convert(results.get(1));
			SetFunction<T> secondFunction = setIdentityFunctionConstructor.convert(results.get(3));
			return new MembershipPredicate<>(firstFunction, secondFunction);
		}
	}
}
