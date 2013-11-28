package logic.set;

import logic.Nameable;

/**
 * Some stupid name for a set that is an intersection.
 * @author Steven Weston
 */
public interface ImpoundSet<T extends Nameable> extends UnmodifiableSet<T> {

	/**
	 * Intersect another {@code Set} with this {@code Set}.
	 * @param s The {@code Set} to unite with this.
	 */
	void intersectWith(Set<T> s);
}
