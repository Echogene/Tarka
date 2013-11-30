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

	private final List<ReflexiveFunction<N>> summands;

	private final Summor<N> summor;

	Addition(List<ReflexiveFunction<N>> summands, Summor<N> summor) {
		this.summands = summands;
		this.summor = summor;
	}

	@Override
	public N evaluate(Universe<N> universe) throws Exception {
		List<N> numbers = new ArrayList<>();
		for(ReflexiveFunction<N> function : summands) {
			numbers.add(function.evaluate(universe));
		}
		return summor.sum(numbers);
	}

	@Override
	public String toString() {
		if (summands.size() == 2) {
			return "(" + summands.get(0).toString() + " " + PLUS_SYMBOL + " " + summands.get(1).toString() + ")";
		} else {
			String output = "(" + SUM_SYMBOL;
			for (ReflexiveFunction<N> function : summands) {
				output += " " + function.toString();
			}
			return output + ")";
		}
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Addition<?>)) {
			return false;
		}
		Addition<?> other = (Addition<?>) o;
		return summands.equals(other.summands);
	}
}
