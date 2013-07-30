package logic.set;

import logic.Nameable;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collections;
import java.util.HashSet;

/**
 * @author Steven Weston
 */
public class CompoundSet<T extends Nameable> extends InfiniteSet<T> {

	private FiniteSet<T> finitePart;
	private java.util.Set<InfiniteSet<T>> infinitePart;

	private CompoundSet(String name) {
		super(name);
	}

	public CompoundSet(String name, FiniteSet<T> finitePart, InfiniteSet<T> infiniteSet) {
		this(name, finitePart, Collections.singleton(infiniteSet));
	}

	public CompoundSet(String name, FiniteSet<T> finitePart, java.util.Set<InfiniteSet<T>> infinitePart) {
		this(name);
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
		if (finitePart.contains(string)) {
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
		throw new NotImplementedException();
	}

	@Override
	public Set<T> copy(String name) {
		throw new NotImplementedException();
	}
}
