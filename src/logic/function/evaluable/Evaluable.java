package logic.function.evaluable;

import logic.Nameable;
import logic.function.Function;

/**
 * An evaluable is a map from a class to a boolean.
 * @author Steven Weston
 */
public interface Evaluable<T extends Nameable, F extends Evaluable<T, F>> extends Function<T, Boolean, F> {
}
