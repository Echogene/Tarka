package logic.function.reflexiveset;

import logic.Nameable;
import logic.function.Function;
import logic.set.Set;

/**
 * @author Steven Weston
 */
public interface ReflexiveSetFunction<T extends Nameable> extends Nameable, Function<T, Set<T>> {
}
