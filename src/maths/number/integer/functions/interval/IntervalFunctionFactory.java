package maths.number.integer.functions.interval;

import logic.factory.FactoryException;
import logic.function.factory.binary.BinaryValidator;
import logic.function.factory.construction.Constructor;
import logic.function.factory.construction.FunctionConvertor;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.function.factory.validation.Validator;
import logic.function.factory.validation.results.StringResult;
import logic.function.factory.validation.results.ValidationResult;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.identity.IdentityFunction;
import logic.function.reflexive.identity.IdentityFunctionFactory;
import logic.function.set.SetFunctionFactory;
import maths.number.Number;
import maths.number.integer.sets.interval.IntervalFactory;

import java.util.List;

import static java.util.Arrays.asList;
import static maths.number.integer.sets.interval.IntervalBound.BoundType;
import static maths.number.integer.sets.interval.IntervalBound.BoundType.CLOSED;
import static maths.number.integer.sets.interval.IntervalBound.BoundType.OPEN;

/**
 * @author Steven Weston
 */
public class IntervalFunctionFactory<N extends Number> extends SetFunctionFactory<N, IntervalFunction<N>> {

	public IntervalFunctionFactory(IntervalFactory<N> factory) {
		super(getConstructors(factory));
	}

	private static <N extends Number> List<ValidatorAndConstructor<IntervalFunction<N>>> getConstructors(IntervalFactory<N> factory) {
		Validator validator = new BinaryValidator(
				asList("[", "[", "(", "("),
				ReflexiveFunction.class,
				null,
				ReflexiveFunction.class,
				asList("]", ")", "]", ")")
		);
		return asList(new ValidatorAndConstructor<>(
				validator,
				new IntervalFunctionConstructor<>(factory)
		));
	}

	private static class IntervalFunctionConstructor<N extends Number> implements Constructor<IntervalFunction<N>> {

		private final FunctionConvertor<IdentityFunction<N>, N> convertor;
		private final IntervalFactory<N> factory;

		public IntervalFunctionConstructor(IntervalFactory<N> factory) {
			this.factory = factory;
			this.convertor = new FunctionConvertor<>(new IdentityFunctionFactory<N>());
		}

		@Override
		public IntervalFunction<N> construct(List<ValidationResult> results) throws FactoryException {
			StringResult openingBracket = (StringResult) results.get(0);
			BoundType lowerType;
			if ("[".equals(openingBracket.getString())) {
				lowerType = CLOSED;
			} else {
				lowerType = OPEN;
			}
			ReflexiveFunction<N> lowerBound = convertor.convert(results.get(1));
			ReflexiveFunction<N> upperBound = convertor.convert(results.get(2));
			StringResult closingBracket = (StringResult) results.get(3);
			BoundType upperType;
			if ("]".equals(closingBracket.getString())) {
				upperType = CLOSED;
			} else {
				upperType = OPEN;
			}
			return new IntervalFunction<>(lowerType, lowerBound, upperBound, upperType, factory);
		}
	}
}
