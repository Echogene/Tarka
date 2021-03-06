package util;

import logic.Nameable;
import logic.function.Function;
import logic.function.object.ObjectFunction;

import java.util.*;

/**
 * @author Steven Weston
 */
public class FunctionUtils {

	public static <D extends Nameable, C> void reduce(Function<D, C, ?> function, String variable, Function<D, ?, ?> assignment) {
		function.reduce(Collections.singletonMap(variable, assignment));
	}

	public static <D extends Nameable, C> void reduce(Function<D, C, ?> function, String variable, D assignment) {
		function.reduce(Collections.singletonMap(variable, (Function<D, ?, ?>) new ObjectFunction<>(assignment)));
	}

	public static <F extends Function<?, ?, ? extends F>> Set<F> copy(Set<F> set) {
		Set<F> copy = new HashSet<>();
		for (F function : set) {
			copy.add(function.copy());
		}
		return copy;
	}

	public static <F extends Function<?, ?, ? extends F>> List<F> copy(List<F> set) {
		List<F> copy = new ArrayList<>();
		for (F function : set) {
			copy.add(function.copy());
		}
		return copy;
	}

	public static <F extends Function<?, ?, ? extends F>> F copy(F function) {
		if (function == null) {
			return null;
		} else {
			return function.copy();
		}
	}

	public static <K, F extends Function<?, ?, ? extends F>> Map<K, F> copy(Map<K, F> map) {
		Map<K, F> copy = new HashMap<>();
		for (Map.Entry<K, F> entry : map.entrySet()) {
			copy.put(entry.getKey(), entry.getValue().copy());
		}
		return copy;
	}
}
