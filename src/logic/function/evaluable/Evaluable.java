package logic.function.evaluable;

import logic.Nameable;
import logic.function.Function;
import logic.model.Model;

/**
 * An evaluable is a map from a class to a boolean.
 * @author Steven Weston
 */
public interface Evaluable<T extends Nameable> extends Function<T, Boolean> {
	public Boolean evaluate(Model<T, ?, ?> model) throws Exception;
}
