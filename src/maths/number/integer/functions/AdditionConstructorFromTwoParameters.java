package maths.number.integer.functions;

import logic.function.factory.binary.FunctionConstructorFromTwoParameters;
import logic.function.reflexive.ReflexiveFunction;
import maths.number.Number;
import maths.number.Summor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Steven Weston
 */
public class AdditionConstructorFromTwoParameters<N extends Number>
		implements FunctionConstructorFromTwoParameters<
			Addition<N>,
			ReflexiveFunction<N>,
			ReflexiveFunction<N>
		> {

	private final Summor<N> summor;

	public AdditionConstructorFromTwoParameters(Summor<N> summor) {
		this.summor = summor;
	}

	@Override
	public Addition<N> construct(ReflexiveFunction<N> parameter1, String operator, ReflexiveFunction<N> parameter2) {
		List<ReflexiveFunction<N>> parameters = new ArrayList<>();
		parameters.add(parameter1);
		parameters.add(parameter2);
		return new Addition<>(parameters, summor);
	}
}
