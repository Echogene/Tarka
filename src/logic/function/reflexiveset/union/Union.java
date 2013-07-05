package logic.function.reflexiveset.union;

import logic.Nameable;
import logic.function.ParameterNotFoundException;
import logic.function.reflexiveset.AbstractReflexiveSetFunction;
import logic.function.reflexiveset.ReflexiveSetFunction;
import logic.set.AbstractSet;
import logic.set.NamedSet;
import logic.set.Set;

import java.util.Iterator;

/**
 *
 * @author Steven Weston
 */
public class Union<T extends Nameable> extends AbstractReflexiveSetFunction<T> {
	public final static String MULTARY_SYMBOL = "⋃";
	public final static String BINARY_SYMBOL = "∪";
	public final static String UNION_SYMBOLS = MULTARY_SYMBOL + BINARY_SYMBOL;

	protected java.util.Set<ReflexiveSetFunction<T>> parameters;

	public Union(java.util.Set<ReflexiveSetFunction<T>> parameters) {
		this.parameters = parameters;
	}

	@Override
	public Set<T> evaluate(Set<? extends Set<T>> variables) throws ParameterNotFoundException {
		Set<Set<T>> setsToUnion = new NamedSet<>(this.getName());
		for(ReflexiveSetFunction<T> function : parameters) {
			setsToUnion.put(function.evaluate(variables));
		}
		return AbstractSet.union(setsToUnion);
	}

	public java.util.Set<ReflexiveSetFunction<T>> getParameters() {
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
			Iterator<ReflexiveSetFunction<T>> iterator = parameters.iterator();
			return "(" + iterator.next().toString() + " ∪ " + iterator.next().toString() + ")";
		} else {
			String output = "(⋃";
			for (ReflexiveSetFunction<T> function : parameters) {
				output += " " + function.toString();
			}
			return output + ")";
		}
	}
}
