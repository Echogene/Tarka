package logic.set;

/**
 * @author Steven Weston
 */
public interface CompoundSet<T> extends UnmodifiableSet<T> {

	/**
	 * Adds all elements of another {@code Set} to this {@code Set}.
	 * @param s The {@code Set} to unite with this.
	 */
	void uniteWith(Set<T> s);
}
