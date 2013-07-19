package maths.number.integer.functions.subtraction;

import logic.factory.FactoryException;
import logic.function.factory.binary.FunctionConstructorFromTwoParameters;
import logic.function.reflexive.ReflexiveFunction;
import maths.number.Number;
import maths.number.Subtractor;

/**
 * @author Steven Weston
 */
public class SubtractionConstructorFromTwoParameters<N extends Number>
		implements FunctionConstructorFromTwoParameters<
			Subtraction<N>,
			ReflexiveFunction<N>,
			ReflexiveFunction<N>
		> {
	private final Subtractor<N> subtractor;

	public SubtractionConstructorFromTwoParameters(Subtractor<N> subtractor) {
		this.subtractor = subtractor;
	}

	@Override
	public Subtraction<N> construct(ReflexiveFunction<N> parameter1, String operator, ReflexiveFunction<N> parameter2) throws FactoryException {
		return new Subtraction<>(parameter1, parameter2, subtractor);
	}
}
