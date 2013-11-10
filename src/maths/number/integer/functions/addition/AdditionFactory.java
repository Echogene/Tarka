package maths.number.integer.functions.addition;

import logic.factory.FactoryException;
import logic.function.factory.binary.BinaryValidator;
import logic.function.factory.construction.Constructor;
import logic.function.factory.construction.FunctionConvertor;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.factory.multary.MultaryValidator;
import logic.function.factory.validation.SimpleLogicValidator;
import logic.function.factory.validation.results.ValidationResult;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.ReflexiveFunctionFactory;
import logic.function.reflexive.identity.IdentityFunction;
import logic.function.reflexive.identity.IdentityFunctionFactory;
import maths.number.Number;
import maths.number.Summor;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static maths.number.integer.functions.addition.Addition.PLUS_SYMBOL;
import static maths.number.integer.functions.addition.Addition.SUM_SYMBOL;

/**
 * @author Steven Weston
 */
public class AdditionFactory<N extends Number> extends ReflexiveFunctionFactory<N, Addition<N>> {

	public AdditionFactory(Summor<N> summor) {
		super(getConstructors(summor));
	}

	private static <N extends Number> List<ValidatorAndConstructor<Addition<N>>> getConstructors(Summor<N> summor) {
		SimpleLogicValidator binaryValidator = new BinaryValidator(asList(PLUS_SYMBOL));
		SimpleLogicValidator multaryValidator = new MultaryValidator(asList(SUM_SYMBOL));
		return asList(
				new ValidatorAndConstructor<>(
						binaryValidator,
						new BinaryAdditionConstructor<N>(summor)
				),
				new ValidatorAndConstructor<>(
						multaryValidator,
						new MultaryAdditionConstructor<N>(summor)
				)
		);
	}

	private static class BinaryAdditionConstructor<N extends Number> implements Constructor<Addition<N>> {

		private final Summor<N> summor;
		private final FunctionConvertor<IdentityFunction<N>, N> convertor;

		public BinaryAdditionConstructor(Summor<N> summor) {
			this.summor = summor;
			this.convertor = new FunctionConvertor<>(new IdentityFunctionFactory<N>());
		}

		@Override
		public Addition<N> construct(List<ValidationResult> results) throws FactoryException {
			ReflexiveFunction<N> firstFunction = convertor.convert(results.get(1));
			ReflexiveFunction<N> secondFunction = convertor.convert(results.get(3));
			return new Addition<>(asList(firstFunction, secondFunction), summor);
		}
	}

	private static class MultaryAdditionConstructor<N extends Number> implements Constructor<Addition<N>> {

		private final Summor<N> summor;
		private final FunctionConvertor<IdentityFunction<N>, N> convertor;

		public MultaryAdditionConstructor(Summor<N> summor) {
			this.summor = summor;
			this.convertor = new FunctionConvertor<>(new IdentityFunctionFactory<N>());
		}

		@Override
		public Addition<N> construct(List<ValidationResult> results) throws FactoryException {
			List<ReflexiveFunction<N>> parameters = new ArrayList<>();
			for (int i = 2; i < results.size() - 1; i++) {
				parameters.add(convertor.convert(results.get(i)));
			}
			return new Addition<N>(parameters, summor);
		}
	}
}
