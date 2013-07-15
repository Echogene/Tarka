package maths.number.integer.functions.addition;

import logic.factory.FactoryException;
import logic.function.factory.multary.FunctionConstructorFromParameterList;
import logic.function.reflexive.ReflexiveFunction;
import maths.number.Number;
import maths.number.Summor;

import java.util.List;

/**
 * @author Steven Weston
 */
public class AdditionConstructorFromParameterList<N extends Number>
		implements FunctionConstructorFromParameterList<Addition<N>, ReflexiveFunction<N>> {
	private final Summor summor;

	public AdditionConstructorFromParameterList(Summor summor) {
		this.summor = summor;
	}

	@Override
	public Addition<N> construct(String operator, List<ReflexiveFunction<N>> parameterList) throws FactoryException {
		return new Addition<>(parameterList, summor);
	}
}
