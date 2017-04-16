package logic.set;

import logic.Nameable;

/**
 * @author Steven Weston
 */
public interface Set<T> extends Nameable {

	/**
	 *
	 * @param thing The element to test whether it is contains within this {@code Set}.
	 * @return Whether this {@code Set} contains the given element.
	 */
	boolean containsValue(T thing);
}
