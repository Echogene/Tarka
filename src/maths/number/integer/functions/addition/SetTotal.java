package maths.number.integer.functions.addition;

import logic.function.Function;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.set.SetFunction;
import logic.model.Model;
import logic.set.Set;
import logic.set.finite.FiniteSet;
import logic.set.undeterminable.NotCertainlyFiniteSetException;
import maths.number.Number;
import maths.number.Summor;

import java.text.MessageFormat;
import java.util.Map;

/**
 * @author Steven Weston
 */
public class SetTotal<N extends Number> implements ReflexiveFunction<N, SetTotal<N>> {

	private final SetFunction<N, ?> set;
	private final Summor<N> summor;

	public SetTotal(SetFunction<N, ?> set, Summor<N> summor) {
		this.set = set;
		this.summor = summor;
	}

	@Override
	public N evaluate(Model<N, ?, ?> model) throws Exception {
		Set<N> setToTotal = set.evaluate(model);
		if (setToTotal instanceof FiniteSet) {
			return summor.sum((FiniteSet<N>) setToTotal);
		} else {
			throw new NotCertainlyFiniteSetException("Cannot total a set that is not certainly finite.");
		}
	}

	@Override
	public void reduce(Map<String, Function<N, ?, ?>> reductions) {
		set.reduce(reductions);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SetTotal setTotal = (SetTotal) o;

		if (!set.equals(setTotal.set)) return false;

		return true;
	}

	@Override
	public String toString() {
		return MessageFormat.format("{0} / {1}", Addition.SUM_SYMBOL, set.toString());
	}

	@Override
	public int hashCode() {
		return set.hashCode();
	}

	@Override
	public SetTotal<N> copy() {
		return new SetTotal<>(set.copy(), summor);
	}
}
