package logic.function.reflexive;

import logic.Nameable;
import logic.function.Function;
import logic.function.ParameterNotFoundException;
import logic.model.universe.Universe;

/**
 * A function is a map from a class to itself.
 * @author Steven Weston
 */
public interface ReflexiveFunction<T extends Nameable> extends Nameable, Function<T, T> {
	public T evaluate(Universe<T> universe) throws ParameterNotFoundException;
}
