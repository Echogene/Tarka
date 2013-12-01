package logic.set.filtered;

import logic.Nameable;
import logic.set.NamedSet;
import logic.set.Set;
import logic.set.UnmodifiableSet;
import logic.set.finite.Filter;

/**
 * @author Steven Weston
 */
abstract class AbstractFilteredSet<T extends Nameable, S extends Set<T>> extends NamedSet<T> implements UnmodifiableSet<T> {

	protected final S set;
	protected final Filter<T> filter;

	AbstractFilteredSet(String name, S set, Filter<T> filter) {
		super(name);
		this.set = set;
		this.filter = filter;
	}

	@Override
	public boolean containsValue(T thing) {
		try {
			return set.containsValue(thing) && filter.passesThrough(thing);
		} catch (Exception e) {
			throw new RuntimeException("Filter failed unexpectedly.", e);
		}
	}
}
