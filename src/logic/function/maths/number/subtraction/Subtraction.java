package logic.function.maths.number.subtraction;

import logic.function.Function;
import logic.function.reflexive.ReflexiveFunction;
import logic.model.Model;
import maths.number.Number;
import maths.number.Subtractor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Steven Weston
 */
public class Subtraction<N extends Number> implements ReflexiveFunction<N, Subtraction<N>> {

	public static final String MINUS = "−";
	public static final String HYPHEN = "-";

	public static final List<String> SYMBOL_LIST = Arrays.asList(MINUS, HYPHEN);

	private final ReflexiveFunction<N, ?> minuend;
	private final ReflexiveFunction<N, ?> subtrahend;

	private final Subtractor<N> subtractor;

	public Subtraction(ReflexiveFunction<N, ?> minuend, ReflexiveFunction<N, ?> subtrahend, Subtractor<N> subtractor) {
		this.minuend    = minuend;
		this.subtrahend = subtrahend;
		this.subtractor = subtractor;
	}

	@Override
	public N evaluate(Model<N, ?, ?> model) throws Exception {
		return subtractor.subtract(minuend.evaluate(model), subtrahend.evaluate(model));
	}

	@Override
	public void reduce(Map<String, Function<N, ?, ?>> reductions) {
		minuend.reduce(reductions);
		subtrahend.reduce(reductions);
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

	@Override
	public Subtraction<N> copy() {
		return new Subtraction<>(minuend.copy(), subtrahend.copy(), subtractor);
	}
}
