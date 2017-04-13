package logic.set.infinite;

import logic.Nameable;
import logic.set.CompoundSet;
import logic.set.NamedSet;
import logic.set.Set;
import logic.set.finite.FiniteCompoundSet;
import logic.set.finite.FiniteSet;
import logic.set.finite.StandardSet;
import ophelia.exceptions.NotImplementedYetException;

import java.util.Collections;
import java.util.HashSet;

/**
 * @author Steven Weston
 */
public class InfiniteCompoundSet<T extends Nameable> extends NamedSet<T> implements CompoundSet<T>, InfiniteSet<T> {

	private final FiniteCompoundSet<T> finitePart;
	private final java.util.Set<InfiniteSet<T>> infinitePart;

	public InfiniteCompoundSet(String name) {
		this(name, new FiniteCompoundSet<T>((String) null), new HashSet<InfiniteSet<T>>());
	}

	public InfiniteCompoundSet(String name, StandardSet<T> finitePart, InfiniteSet<T> infiniteSet) {
		this(name, new FiniteCompoundSet<>(finitePart), Collections.singleton(infiniteSet));
	}

	public InfiniteCompoundSet(String name, FiniteCompoundSet<T> finitePart, java.util.Set<InfiniteSet<T>> infinitePart) {
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
		if (s instanceof FiniteSet<?>) {
			finitePart.uniteWith(s);
		} else if (s instanceof InfiniteCompoundSet<?>) {
			InfiniteCompoundSet<T> compoundSet = (InfiniteCompoundSet <T>) s;
			finitePart.uniteWith(compoundSet.finitePart);
			infinitePart.addAll(compoundSet.infinitePart);
		} else if (s instanceof InfiniteSet<?>) {
			infinitePart.add((InfiniteSet<T>) s);
		} else {
			throw new NotImplementedYetException();
		}
	}
}
