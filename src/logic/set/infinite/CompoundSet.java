package logic.set.infinite;

import logic.Nameable;
import logic.set.Set;
import logic.set.finite.StandardSet;

import java.util.Collections;
import java.util.HashSet;

/**
 * @author Steven Weston
 */
public class CompoundSet<T extends Nameable> extends InfiniteSet<T> {

	private StandardSet<T> finitePart;
	private java.util.Set<InfiniteSet<T>> infinitePart;

	public CompoundSet(String name) {
		this(name, new StandardSet<>(null), new HashSet<>());
	}

	public CompoundSet(String name, StandardSet<T> finitePart, InfiniteSet<T> infiniteSet) {
		this(name, finitePart, Collections.singleton(infiniteSet));
	}

	public CompoundSet(String name, StandardSet<T> finitePart, java.util.Set<InfiniteSet<T>> infinitePart) {
		super(name);
		this.finitePart = finitePart;
		this.infinitePart = infinitePart;
	}

	@Override
	public boolean containsValue(T thing) {
		if (finitePart.containsValue(thing)) {
			return true;
		}
		for (InfiniteSet<T> set : infinitePart) {
			if (set.containsValue(thing)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void uniteWith(Set<T> s) {
		if (s instanceof StandardSet<?>) {
			finitePart.uniteWith(s);
		} else if (s instanceof CompoundSet<?>) {
			CompoundSet<T> compoundSet = (CompoundSet <T>) s;
			finitePart.uniteWith(compoundSet.finitePart);
			infinitePart.addAll(compoundSet.infinitePart);
		} else if (s instanceof InfiniteSet<?>) {
			infinitePart.add((InfiniteSet<T>) s);
		}
	}
}
