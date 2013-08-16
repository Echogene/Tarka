package logic.set.finite.multiset;

import logic.Nameable;
import logic.set.finite.StandardSet;

/**
 * @author Steven Weston
 */
public class NamedMultiSet<T extends Nameable> extends StandardSet<MultiSetEntry<T>> {
	public NamedMultiSet(String name) {
		super(name);
	}

	@Override
	public boolean contains(String string) {
		MultiSetEntry<T> entry = get(string);
		return (entry != null) && (entry.getNumber() > 0);
	}

	public boolean contains(T thing) {
		MultiSetEntry<T> entry = get(thing.getName());
		return (entry != null) && (entry.getNumber() > 0);
	}

	public void add(T thing) {
		if (hashMap.containsKey(thing.getName())) {
			get(thing.getName()).addAnother();
		} else {
			put(new MultiSetEntry<>(thing));
		}
	}

	public void remove(T thing) {
		MultiSetEntry<T> entry = get(thing.getName());
		if (entry != null) {
			entry.removeOne();
		}
	}
}
