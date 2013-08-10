package logic.function.set.simple;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.construction.Constructor;
import logic.function.factory.construction.FunctionConvertor;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.factory.multary.MultaryValidator;
import logic.function.factory.validation.Validator;
import logic.function.factory.validation.results.ValidationResult;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.identity.IdentityFunction;
import logic.function.reflexive.identity.IdentityFunctionFactory;
import logic.function.set.SetFunctionFactory;
import logic.set.Set;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author Steven Weston
 */
public class SimpleSetFactory<T extends Nameable> extends SetFunctionFactory<T> {

	public SimpleSetFactory() {
		super(getConstructors());
	}

	private static <T extends Nameable> List<ValidatorAndConstructor<Function<T, Set<T>>>> getConstructors() {
		Validator validator = new MultaryValidator(asList("{"), null, ReflexiveFunction.class, asList("}"));
		return Arrays.asList(
				new ValidatorAndConstructor<>(
						validator,
						new SimpleSetConstructor<T>()
				)
		);
	}

	public static <T extends Nameable> SimpleSet<T> createElement(String... memberStrings) {
		java.util.Set<ReflexiveFunction<T>> members = new HashSet<>();
		for (String memberString : memberStrings) {
			members.add(new IdentityFunction<>(memberString));
		}
		return new SimpleSet<>(members);
	}

	private static class SimpleSetConstructor<T extends Nameable> implements Constructor<Function<T, Set<T>>> {

		private final FunctionConvertor<IdentityFunction<T>, T> convertor;

		public SimpleSetConstructor() {
			this.convertor = new FunctionConvertor<>(new IdentityFunctionFactory<T>());
		}

		@Override
		public Function<T, Set<T>> construct(List<ValidationResult> results) throws FactoryException {
			java.util.Set<ReflexiveFunction<T>> parameters = new HashSet<>();
			for (int i = 1; i < results.size() - 1; i++) {
				parameters.add(convertor.convert(results.get(i)));
			}
			return new SimpleSet<>(parameters);
		}
	}
}
