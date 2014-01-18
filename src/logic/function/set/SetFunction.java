package logic.function.set;

import logic.Nameable;
import logic.function.Function;
import logic.set.Set;

/**
 * @author Steven Weston
 */
public interface SetFunction<T extends Nameable, F extends SetFunction<T, F>> extends Function<T, Set<T>, F> {
}
