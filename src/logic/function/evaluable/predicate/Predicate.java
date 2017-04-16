package logic.function.evaluable.predicate;

import logic.Nameable;
import logic.function.evaluable.Evaluable;

/**
 * @author Steven Weston
 */
public abstract class Predicate<T extends Nameable, F extends Predicate<T, F>> implements Evaluable<T, F> {
}
