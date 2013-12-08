package maths.number.integer.functions.multiplication;

import logic.function.reflexive.ReflexiveFunction;
import logic.model.Model;
import maths.number.Multiplior;
import maths.number.Number;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Steven Weston
 */
public class Multiplication<N extends Number> implements ReflexiveFunction<N> {

	public static final String TIMES_SYMBOL = "×";
	public static final String PRODUCT_SYMBOL = "Π";
	public static final String MULTIPLICATION_SYMBOLS = TIMES_SYMBOL + PRODUCT_SYMBOL;

	private final List<ReflexiveFunction<N>> factors;

	private final Multiplior<N> multiplior;

	public Multiplication(List<ReflexiveFunction<N>> factors, Multiplior<N> multiplior) {
		this.factors = factors;
		this.multiplior = multiplior;
	}

	@Override
	public N evaluate(Model<N, ?, ?> model) throws Exception {
		List<N> numbers = new ArrayList<>();
		for(ReflexiveFunction<N> function : factors) {
			numbers.add(function.evaluate(model));
		}
		return multiplior.produce(numbers);
	}

	@Override
	public String toString() {
		if (factors.size() == 2) {
			return "(" + factors.get(0).toString() + " " + TIMES_SYMBOL + " " + factors.get(1).toString() + ")";
		} else {
			String output = "(" + PRODUCT_SYMBOL;
			for (ReflexiveFunction<N> function : factors) {
				output += " " + function.toString();
			}
			return output + ")";
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Multiplication that = (Multiplication) o;

		if (!factors.equals(that.factors)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return factors.hashCode();
	}
}
