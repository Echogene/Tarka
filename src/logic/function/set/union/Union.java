package logic.function.set.union;

import logic.Nameable;
import logic.function.Function;
import logic.function.set.SetFunction;
import logic.model.Model;
import logic.set.Set;
import logic.set.operators.Uniter;
import util.FunctionUtils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import static java.text.MessageFormat.format;

/**
 *
 * @author Steven Weston
 */
public class Union<T extends Nameable> implements SetFunction<T, Union<T>> {

	public final static String MULTARY_SYMBOL = "⋃";
	public final static String BINARY_SYMBOL = "∪";
	public final static String UNION_SYMBOLS = MULTARY_SYMBOL + BINARY_SYMBOL;

	private final java.util.Set<SetFunction<T, ?>> parameters;

	public Union(java.util.Set<SetFunction<T, ?>> parameters) {
		this.parameters = parameters;
	}

	@Override
	public Set<T> evaluate(Model<T, ?, ?> model) throws Exception {
		java.util.Set<Set<T>> setsToUnion = new HashSet<>();
		for(SetFunction<T, ?> function : parameters) {
			setsToUnion.add(function.evaluate(model));
		}
		return Uniter.unite(setsToUnion);
	}

	@Override
	public void reduce(Map<String, Function<T, ?, ?>> reductions) {
		for (SetFunction<T, ?> parameter : parameters) {
			parameter.reduce(reductions);
		}
	}

	public java.util.Set<SetFunction<T, ?>> getParameters() {
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
			Iterator<SetFunction<T, ?>> iterator = parameters.iterator();
			return format("({0} {1} {2})", iterator.next().toString(), BINARY_SYMBOL, iterator.next().toString());
		} else {
			String output = "(" + MULTARY_SYMBOL;
			for (SetFunction<T, ?> function : parameters) {
				output += " " + function.toString();
			}
			return output + ")";
		}
	}

	@Override
	public Union<T> copy() {
		return new Union<>(FunctionUtils.copy(parameters));
	}
}
