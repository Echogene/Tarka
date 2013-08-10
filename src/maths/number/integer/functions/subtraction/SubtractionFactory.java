package maths.number.integer.functions.subtraction;

import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.binary.BinaryValidator;
import logic.function.factory.construction.Constructor;
import logic.function.factory.construction.FunctionConvertor;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.factory.validation.Validator;
import logic.function.factory.validation.results.ValidationResult;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.ReflexiveFunctionFactory;
import logic.function.reflexive.identity.IdentityFunctionFactory;
import maths.number.Number;
import maths.number.Subtractor;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static maths.number.integer.functions.addition.Addition.PLUS_SYMBOL;

/**
 * @author Steven Weston
 */
public class SubtractionFactory<N extends Number> extends ReflexiveFunctionFactory<N> {

	public SubtractionFactory(Subtractor<N> subtractor) {
		super(getConstructors(subtractor));
	}

	private static <N extends Number> List<ValidatorAndConstructor<Function<N, N>>> getConstructors(Subtractor<N> subtractor) {
		Validator binaryValidator = new BinaryValidator(asList(PLUS_SYMBOL));
		return Arrays.asList(
				new ValidatorAndConstructor<>(
						binaryValidator,
						new SubtractionConstructor<N>(subtractor)
				)
		);
	}

	private static class SubtractionConstructor<N extends Number> implements Constructor<Function<N, N>> {

		private final Subtractor<N> subtractor;
		private final FunctionConvertor<ReflexiveFunction<N>, N> convertor;

		public SubtractionConstructor(Subtractor<N> subtractor) {
			this.subtractor = subtractor;
			this.convertor = new FunctionConvertor<ReflexiveFunction<N>, N>(new IdentityFunctionFactory<N>());
		}

		@Override
		public Function<N, N> construct(List<ValidationResult> results) throws FactoryException {
			return new Subtraction<>(convertor.convert(results.get(1)), convertor.convert(results.get(3)), subtractor);
		}
	}
}
