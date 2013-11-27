package logic.function.set.union;

import logic.Nameable;
import logic.function.set.SetFunction;
import logic.model.universe.Universe;
import logic.set.Set;
import logic.set.Uniter;

import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author Steven Weston
 */
public class Union<T extends Nameable> implements SetFunction<T> {

	public final static String MULTARY_SYMBOL = "⋃";
	public final static String BINARY_SYMBOL = "∪";
	public final static String UNION_SYMBOLS = MULTARY_SYMBOL + BINARY_SYMBOL;

	private final java.util.Set<SetFunction<T>> parameters;

	public Union(java.util.Set<SetFunction<T>> parameters) {
		this.parameters = parameters;
	}

	@Override
	public Set<T> evaluate(Universe<T> universe) throws Exception {
		java.util.Set<Set<T>> setsToUnion = new HashSet<>();
		for(SetFunction<T> function : parameters) {
			setsToUnion.add(function.evaluate(universe));
		}
		return Uniter.unite(setsToUnion);
	}

	public java.util.Set<SetFunction<T>> getParameters() {
		return parameters;
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
	public int hashCode() {
		return parameters != null ? parameters.hashCode() : 0;
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
