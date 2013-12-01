package logic.set.undeterminable;

import logic.Nameable;
import logic.set.ImpoundSet;
import logic.set.NamedSet;
import logic.set.Set;
import logic.set.infinite.InfiniteSet;

import java.util.HashSet;

/**
 * @author Steven Weston
 */
public class UndeterminableImpoundSet<T extends Nameable> extends NamedSet<T> implements ImpoundSet<T>, UndeterminableSet<T> {

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
