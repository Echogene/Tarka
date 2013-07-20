package maths.number.integer.functions.subtraction;

import logic.function.reflexive.ReflexiveFunction;
import logic.model.universe.Universe;
import maths.number.Number;
import maths.number.Subtractor;

import java.util.Arrays;
import java.util.List;

/**
 * @author Steven Weston
 */
public class Subtraction<N extends Number> implements ReflexiveFunction<N> {
	public static final String MINUS = "−";
	public static final String HYPHEN = "-";

	public static final List<String> SYMBOL_LIST = Arrays.asList(MINUS, HYPHEN);

	private ReflexiveFunction<N> minuend;
	private ReflexiveFunction<N> subtrahend;

	Subtractor<N> subtractor;

	public Subtraction(ReflexiveFunction<N> minuend, ReflexiveFunction<N> subtrahend, Subtractor<N> subtractor) {
		this.minuend    = minuend;
		this.subtrahend = subtrahend;
		this.subtractor = subtractor;
	}

	@Override
	public N evaluate(Universe<N> universe) throws Exception {
		return subtractor.subtract(minuend.evaluate(universe), subtrahend.evaluate(universe));
	}

	@Override
	public String getName() {
		return toString();
	}

	@Override
	public String toString() {
		return "(" + minuend.toString() + " " + MINUS + " " + subtrahend.toString() + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Subtraction)) {
			return false;
		}
		Subtraction other = (Subtraction) o;
		return minuend.equals(other.minuend)
				&& subtrahend.equals(other.subtrahend);
	}
}
