package util;

import logic.Nameable;
import logic.function.Function;
import logic.function.object.ObjectFunction;

import java.util.Collections;

/**
 * @author Steven Weston
 */
public class FunctionUtils {

	public static <D extends Nameable, C> void reduce(Function<D, C, ?> function, String variable, Function<D, ?, ?> assignment) {
		function.reduce(Collections.singletonMap(variable, assignment));
	}

	public static <D extends Nameable, C> void reduce(Function<D, C, ?> function, String variable, D assignment) {
		reduce(function, variable, new ObjectFunction<>(assignment));
	}
}
