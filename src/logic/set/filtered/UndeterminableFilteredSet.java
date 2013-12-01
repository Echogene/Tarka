package logic.set.filtered;

import logic.Nameable;
import logic.set.Set;
import logic.set.finite.Filter;
import logic.set.undeterminable.UndeterminableSet;

/**
 * @author Steven Weston
 */
public class UndeterminableFilteredSet<T extends Nameable> extends AbstractFilteredSet<T, Set<T>> implements UndeterminableSet<T> {

	public UndeterminableFilteredSet(String name, Set<T> set, Filter<T> filter) {
		super(name, set, filter);
	}
}
