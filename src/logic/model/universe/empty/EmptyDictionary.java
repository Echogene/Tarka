package logic.model.universe.empty;

import logic.set.Dictionary;

/**
 * @author Steven Weston
 */
public class EmptyDictionary<T> extends EmptySet<T> implements Dictionary<T> {

	@Override
	public T get(String string) {
		return null;
	}

	@Override
	public boolean contains(String string) {
		return false;
	}
}
