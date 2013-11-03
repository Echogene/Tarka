package logic.model.universe.empty;

import logic.set.Set;

/**
 * @author Steven Weston
 */
public class EmptySet<T> implements Set<T> {

	@Override
	public boolean containsValue(Object thing) {
		return false;
	}

	@Override
	public String getName() {
		return "∅";
	}
}
