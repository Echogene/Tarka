package logic.function;

import logic.Nameable;
import logic.model.Model;

/**
 * A {@code Function} represents a function from one class to another.  It is of the form F(a, b, ...).
 * @author Steven Weston
 */
public interface Function<D extends Nameable, C> {
	public C evaluate(Model<D, ?, ?> model) throws Exception;
}
