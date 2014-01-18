package logic.function.voidfunction;

import logic.Nameable;
import logic.function.Function;

/**
 * @author Steven Weston
 */
public interface VoidFunction<T extends Nameable, F extends VoidFunction<T, F>> extends Function<T, Void, F> {
}
