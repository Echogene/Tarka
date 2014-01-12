package logic.function.set.intersection;

import logic.Nameable;
import logic.function.Function;
import logic.function.set.SetFunction;
import logic.model.Model;
import logic.set.Set;
import logic.set.operators.Intersector;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import static java.text.MessageFormat.format;

/**
 * @author Steven Weston
 */
public class Intersection<T extends Nameable> implements SetFunction<T> {

	public final static String MULTARY_SYMBOL = "⋂";
	public final static String BINARY_SYMBOL = "∩";
	public final static String INTERSECTION_SYMBOLS = MULTARY_SYMBOL + BINARY_SYMBOL;

	private final java.util.Set<SetFunction<T>> parameters;

	public Intersection(java.util.Set<SetFunction<T>> parameters) {
		this.parameters = parameters;
	}

	@Override
	public Set<T> evaluate(Model<T, ?, ?> model) throws Exception {
		java.util.Set<Set<T>> setsToIntersect = new HashSet<>();
		for(SetFunction<T> function : parameters) {
			setsToIntersect.add(function.evaluate(model));
		}
		return Intersector.intersect(setsToIntersect);
	}

	@Override
	public void reduce(Map<String, Function<T, ?>> reductions) {
		for (SetFunction<T> parameter : parameters) {
			parameter.reduce(reductions);
		}
	}


	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Intersection<?>)) {
			return false;
		}
		Intersection<?> other = (Intersection) o;
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
			return format("({0} {1} {2})", iterator.next().toString(), BINARY_SYMBOL, iterator.next().toString());
		} else {
			String output = "(" + MULTARY_SYMBOL;
			for (SetFunction<T> function : parameters) {
				output += " " + function.toString();
			}
			return output + ")";
		}
	}

	@Override
	public Intersection<T> copy() {
		java.util.Set<SetFunction<T>> newParameters = new HashSet<>();
		for (SetFunction<T> parameter : parameters) {
			newParameters.add(parameter.copy());
		}
		return new Intersection<>(newParameters);
	}
}
