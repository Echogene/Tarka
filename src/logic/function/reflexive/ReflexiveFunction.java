package logic.function.reflexive;

import logic.Nameable;
import logic.function.Function;

/**
 * A function is a map from a class to itself.
 * @author Steven Weston
 */
public interface ReflexiveFunction<T extends Nameable, F extends ReflexiveFunction<T, F>> extends Function<T, T, F> {
}
