package logic.function;

import logic.Nameable;
import logic.model.Model;

import java.util.Map;

/**
 * A {@code Function} represents a function from one class to another.  It is of the form F(a, b, ...).
 * @author Steven Weston
 */
public interface Function<D extends Nameable, C> {

	C evaluate(Model<D, ?, ?> model) throws Exception;

	/**
	 * Replace all identity functions to variables which are keys in the given map with the function that is the
	 * corresponding value in the map.
	 * @param reductions
	 */
	void reduce(Map<String, Function<D, ?>> reductions);

	/**
	 * Create a deep copy of this function.
	 */
	Function<D, C> copy();
}
