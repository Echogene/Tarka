package logic.function.set.union;

import logic.Nameable;
import logic.function.set.SetFunction;
import logic.model.universe.Universe;
import logic.set.AbstractSet;
import logic.set.Set;
import logic.set.finite.FiniteSet;

import java.util.Iterator;

/**
 *
 * @author Steven Weston
 */
public class Union<T extends Nameable> implements SetFunction<T> {
	public final static String MULTARY_SYMBOL = "⋃";
	public final static String BINARY_SYMBOL = "∪";
	public final static String UNION_SYMBOLS = MULTARY_SYMBOL + BINARY_SYMBOL;

	protected java.util.Set<SetFunction<T>> parameters;

	public Union(java.util.Set<SetFunction<T>> parameters) {
		this.parameters = parameters;
	}

	@Override
	public Set<T> evaluate(Universe<T> universe) throws Exception {
		FiniteSet<Set<T>> setsToUnion = new FiniteSet<>(this.getName());
		for(SetFunction<T> function : parameters) {
			setsToUnion.put(function.evaluate(universe));
		}
		return AbstractSet.union(setsToUnion);
	}

	public java.util.Set<SetFunction<T>> getParameters() {
		return parameters;
	}

	@Override
	public String getName() {
		return parameters.size() == 2 ? BINARY_SYMBOL : MULTARY_SYMBOL;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Union<?>)) {
			return false;
		}
		Union<?> other = (Union) o;
		return this.parameters.equals(other.parameters);
	}

	@Override
	public String toString() {
		if (parameters.size() == 2) {
			Iterator<SetFunction<T>> iterator = parameters.iterator();
			return "(" + iterator.next().toString() + " ∪ " + iterator.next().toString() + ")";
		} else {
			String output = "(⋃";
			for (SetFunction<T> function : parameters) {
				output += " " + function.toString();
			}
			return output + ")";
		}
	}
}
