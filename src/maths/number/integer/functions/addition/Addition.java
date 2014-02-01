package maths.number.integer.functions.addition;

import logic.function.Function;
import logic.function.reflexive.ReflexiveFunction;
import logic.model.Model;
import maths.number.Number;
import maths.number.Summor;
import util.FunctionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Steven Weston
 */
public class Addition<N extends Number> implements ReflexiveFunction<N, Addition<N>> {

	public static final String PLUS_SYMBOL = "+";
	public static final String SUM_SYMBOL = "Σ";
	public static final String ADDITION_SYMBOLS = PLUS_SYMBOL + SUM_SYMBOL;

	private final List<ReflexiveFunction<N, ?>> summands;

	private final Summor<N> summor;

	public Addition(List<ReflexiveFunction<N, ?>> summands, Summor<N> summor) {
		this.summands = summands;
		this.summor = summor;
	}

	@Override
	public N evaluate(Model<N, ?, ?> model) throws Exception {
		List<N> numbers = new ArrayList<>();
		for(ReflexiveFunction<N, ?> summand : summands) {
			numbers.add(summand.evaluate(model));
		}
		return summor.sum(numbers);
	}

	@Override
	public void reduce(Map<String, Function<N, ?, ?>> reductions) {
		for (ReflexiveFunction<N, ?> summand : summands) {
			summand.reduce(reductions);
		}
	}

	@Override
	public String toString() {
		if (summands.size() == 2) {
			return "(" + summands.get(0).toString() + " " + PLUS_SYMBOL + " " + summands.get(1).toString() + ")";
		} else {
			String output = "(" + SUM_SYMBOL;
			for (ReflexiveFunction<N, ?> summand : summands) {
				output += " " + summand.toString();
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

	@Override
	public Addition<N> copy() {
		return new Addition<>(FunctionUtils.copy(summands), summor);
	}
}
