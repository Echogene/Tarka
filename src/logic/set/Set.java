package logic.set;

import logic.Nameable;

import java.util.Collection;

/**
 * @author Steven Weston
 */
public interface Set<T> extends Iterable<T>, Nameable {
	T get(String string);
	void put(T thing);
	T put(String string, T thing);

	/**
	 * @param string The name to test whether it represents an element in this {@code Set}.
	 * @return Whether this {@code Set} contains an element represented by the given {@code String}.
	 */
	boolean contains(String string);
	boolean containsValue(T thing);

	/**
	 * @return The values of this {@code Set} as a {@code Collection}.
	 */
	Collection<T> values();

	/**
	 * Adds all elements of another {@code Set} to this {@code Set}.
	 * @param s The {@code Set} to unite with this.
	 */
	void uniteWith(Set<T> s);

	/**
	 * Copies this {@code Set} and gives it a new name.
	 * @param name the name to give the new {@code Set}.
	 * @return A copy of this with a new name.
	 */
	Set<T> copy(String name);

	/**
	 * @return The number of elements contained in this {@code Set}.
	 */
	int size();

	void setName(String name);

	T remove(String name);
}
