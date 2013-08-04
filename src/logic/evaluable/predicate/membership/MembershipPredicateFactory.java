package logic.evaluable.predicate.membership;

import logic.Nameable;
import logic.evaluable.predicate.PredicateFactory;
import logic.function.Function;
import logic.function.factory.binary.BinaryValidator;
import logic.function.factory.construction.Constructor;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.factory.validation.Validator;
import logic.function.factory.validation.results.FunctionResult;
import logic.function.factory.validation.results.StringResult;
import logic.function.factory.validation.results.ValidationResult;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.identity.IdentityFunction;
import logic.function.set.SetFunction;
import logic.function.set.identity.SetIdentityFunction;

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
				new ValidatorAndConstructor<Function<T, Boolean>>(
						validator,
						new MembershipPredicateConstructor<T>()
				)
		);
	}

	private static class MembershipPredicateConstructor<T extends Nameable> implements Constructor<Function<T, Boolean>> {
		@Override
		public Function<T, Boolean> construct(List<ValidationResult> results) {
			ReflexiveFunction<T> firstFunction;
			SetFunction<T> secondFunction;
			ValidationResult firstResult = results.get(1);
			if (firstResult instanceof StringResult) {
				firstFunction = new IdentityFunction<>(((StringResult) firstResult).getString());
			} else {
				firstFunction = (ReflexiveFunction<T>) ((FunctionResult) firstResult).getFunction();
			}
			ValidationResult secondResult = results.get(3);
			if (secondResult instanceof StringResult) {
				secondFunction = new SetIdentityFunction<>(((StringResult) secondResult).getString());
			} else {
				secondFunction = (SetFunction<T>) ((FunctionResult) secondResult).getFunction();
			}
			return new MembershipPredicate<>(firstFunction, secondFunction);
		}
	}
}
