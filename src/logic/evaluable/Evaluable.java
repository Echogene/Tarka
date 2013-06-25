package logic.evaluable;

import logic.Nameable;
import logic.function.Function;
import logic.model.universe.Universe;

/**
 * An evaluable is a map from a class to a boolean.
 * @author Steven Weston
 */
public interface Evaluable<T extends Nameable> extends Function<T, Boolean>, Nameable {
	public Boolean evaluate(Universe<T> universe) throws Exception;
}
