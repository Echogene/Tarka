package maths.number.integer.functions.addition;

import logic.function.reflexive.ReflexiveFunction;
import logic.model.universe.Universe;
import maths.number.Number;
import maths.number.Summor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Steven Weston
 */
public class Addition<N extends Number> implements ReflexiveFunction<N> {
	public static final String PLUS_SYMBOL = "+";
	public static final String SUM_SYMBOL = "Σ";
	public static final String ADDITION_SYMBOLS = PLUS_SYMBOL + SUM_SYMBOL;
	List<ReflexiveFunction<N>> parameters;

	Summor<N> summor;

	Addition(List<ReflexiveFunction<N>> parameters, Summor<N> summor) {
		this.parameters = parameters;
		this.summor = summor;
	}

	@Override
	public N evaluate(Universe<N> universe) throws Exception {
		List<N> numbers = new ArrayList<>();
		for(ReflexiveFunction<N> function : parameters) {
			numbers.add(function.evaluate(universe));
		}
		return summor.sum(numbers);
	}

	@Override
	public String toString() {
		if (parameters.size() == 2) {
			return "(" + parameters.get(0).toString() + " " + PLUS_SYMBOL + " " + parameters.get(1).toString() + ")";
		} else {
			String output = "(" + SUM_SYMBOL;
			for (ReflexiveFunction<N> function : parameters) {
				output += " " + function.toString();
			}
			return output + ")";
		}
	}

	@Override
	public String getName() {
		return toString();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Addition<?>)) {
			return false;
		}
		Addition<?> other = (Addition<?>) o;
		return parameters.equals(other.parameters);
	}
}
