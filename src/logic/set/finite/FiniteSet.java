package logic.set.finite;

import logic.set.Set;
import maths.number.integer.Integer;

/**
 * @author Steven Weston
 */
public interface FiniteSet<T> extends Iterable<T>, Set<T> {

	public abstract Integer size();
}
