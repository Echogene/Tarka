package logic.set;

import logic.Nameable;

/**
 * @author Steven Weston
 */
public interface Set<T> extends Iterable<T>, Nameable {
	T get(String string);

	/**
	 * @param string The name to test whether it represents an element in this {@code Set}.
	 * @return Whether this {@code Set} contains an element represented by the given {@code String}.
	 */
	boolean contains(String string);
	boolean containsValue(T thing);

	/**
	 * Adds all elements of another {@code Set} to this {@code Set}.
	 * @param s The {@code Set} to unite with this.
	 */
	void uniteWith(Set<T> s);
}
