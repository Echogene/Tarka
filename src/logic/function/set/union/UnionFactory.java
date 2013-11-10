package logic.function.set.union;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.factory.binary.BinaryValidator;
import logic.function.factory.construction.Constructor;
import logic.function.factory.construction.FunctionConvertor;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.factory.multary.MultaryValidator;
import logic.function.factory.oldvalidation.SimpleLogicValidator;
import logic.function.factory.oldvalidation.results.ValidationResult;
import logic.function.set.SetFunction;
import logic.function.set.SetFunctionFactory;
import logic.function.set.identity.SetIdentityFunction;
import logic.function.set.identity.SetIdentityFunctionFactory;

import java.util.HashSet;
import java.util.List;

import static java.util.Arrays.asList;
import static logic.function.set.union.Union.BINARY_SYMBOL;
import static logic.function.set.union.Union.MULTARY_SYMBOL;

/**
 * @author Steven Weston
 */
public class UnionFactory<T extends Nameable> extends SetFunctionFactory<T, Union<T>> {

	public UnionFactory() {
		super(getConstructors());
	}

	private static <T extends Nameable> List<ValidatorAndConstructor<Union<T>>> getConstructors() {
		SimpleLogicValidator binaryValidator = new BinaryValidator(SetFunction.class, asList(BINARY_SYMBOL), SetFunction.class);
		SimpleLogicValidator multaryValidator = new MultaryValidator(asList(MULTARY_SYMBOL), SetFunction.class);
		return asList(
				new ValidatorAndConstructor<>(
						binaryValidator,
						new BinaryUnionConstructor<T>()
				),
				new ValidatorAndConstructor<>(
						multaryValidator,
						new MultaryUnionConstructor<T>()
				)
		);
	}

	public static <T extends Nameable> Union<T> createElement(String... parameters) {
		java.util.Set<SetFunction<T>> functions = new HashSet<>();
		for (String parameter : parameters) {
			SetFunction<T> function = new SetIdentityFunction<>(parameter);
			functions.add(function);
		}
		return new Union<>(functions);
	}

	private static class BinaryUnionConstructor<T extends Nameable> implements Constructor<Union<T>> {

		private final FunctionConvertor<SetFunction<T>, T> convertor;

		private BinaryUnionConstructor() {
			convertor = new FunctionConvertor<SetFunction<T>, T>(new SetIdentityFunctionFactory <T>());
		}

		@Override
		public Union<T> construct(List<ValidationResult> results) throws FactoryException {
			SetFunction<T> firstFunction = convertor.convert(results.get(1));
			SetFunction<T> secondFunction = convertor.convert(results.get(3));
			java.util.Set<SetFunction<T>> parameters = new HashSet<>();
			parameters.add(firstFunction);
			parameters.add(secondFunction);
			return new Union<>(parameters);
		}
	}

	private static class MultaryUnionConstructor<T extends Nameable> implements Constructor<Union<T>> {

		private final FunctionConvertor<SetFunction<T>, T> convertor;

		private MultaryUnionConstructor() {
			convertor = new FunctionConvertor<SetFunction<T>, T>(new SetIdentityFunctionFactory <T>());
		}

		@Override
		public Union<T> construct(List<ValidationResult> results) throws FactoryException {
			java.util.Set<SetFunction<T>> parameters = new HashSet<>();
			for (int i = 2; i < results.size() - 1; i++) {
				parameters.add(convertor.convert(results.get(i)));
			}
			return new Union<>(parameters);
		}
	}
}
