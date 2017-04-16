package logic.set;

/**
 * @author Steven Weston
 */
public interface Dictionary<T> extends Set<T> {

	/**
	 * @param string The name of an element to get from the {@code Dictionary}.
	 * @return The element that is represented by the given {@code String}.
	 */
	T get(String string);

	/**
	 * @param string The name to test whether it represents an element in this {@code Dictionary}.
	 * @return Whether this {@code Dictionary} contains an element represented by the given {@code String}.
	 */
	boolean contains(String string);
}
