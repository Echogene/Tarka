package logic.set.infinite;

import logic.Nameable;
import logic.set.ImpoundSet;
import logic.set.Set;
import logic.set.undeterminable.UndeterminableSet;

import java.util.HashSet;

/**
 * @author Steven Weston
 */
public class UndeterminableImpoundSet<T extends Nameable> extends UndeterminableSet<T> implements ImpoundSet<T> {

	private final java.util.Set<InfiniteSet<T>> sets;

	public UndeterminableImpoundSet(String name) {
		super(name);
		sets = new HashSet<>();
	}

	@Override
	public void intersectWith(Set<T> s) {
		sets.add((InfiniteSet<T>) s);
	}

	@Override
	public boolean containsValue(T thing) {
		for (InfiniteSet<T> set : sets) {
			if (!set.containsValue(thing)) {
				return false;
			}
		}
		return true;
	}
}
