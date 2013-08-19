package logic.set.infinite;

import logic.Nameable;
import logic.set.AmbiguousMemberException;
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
	public T get(String string) {
		java.util.Set<T> outputSet = new HashSet<>();
		T result = finitePart.get(string);
		if (result != null) {
			outputSet.add(result);
		}
		for (InfiniteSet<T> set : infinitePart) {
			result = set.get(string);
			if (result != null) {
				outputSet.add(result);
			}
		}
		if (outputSet.size() == 1) {
			return outputSet.iterator().next();
		} else if (outputSet.isEmpty()) {
			return null;
		} else {
			throw new AmbiguousMemberException("The string " + string + " was resolved to more than one member.");
		}
	}

	@Override
	public boolean contains(String string) {
		if (finitePart != null && finitePart.contains(string)) {
			return true;
		}
		for (InfiniteSet<T> set : infinitePart) {
			if (set.contains(string)) {
				return true;
			}
		}
		return false;
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
