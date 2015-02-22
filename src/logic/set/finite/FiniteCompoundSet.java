package logic.set.finite;

import logic.Nameable;
import logic.set.CompoundSet;
import logic.set.NamedSet;
import logic.set.Set;
import logic.set.infinite.InfiniteSet;
import maths.number.integer.Integer;
import exceptions.NotImplementedYetException;

import java.util.HashSet;
import java.util.Iterator;

/**
 * @author Steven Weston
 */
public class FiniteCompoundSet<T extends Nameable> extends NamedSet<T> implements CompoundSet<T>, FiniteSet<T> {

	private final java.util.Set<FiniteSet<T>> components;

	public FiniteCompoundSet(String name) {
		super(name);
		components = new HashSet<>();
	}

	public FiniteCompoundSet(StandardSet<T> finitePart) {
		this((String) null);
		components.add(finitePart);
	}

	@Override
	public Integer size() {
		throw new NotImplementedYetException();
	}

	@Override
	public Iterator<T> iterator() {
		return new FiniteCompoundSetIterator();
	}

	private class FiniteCompoundSetIterator implements Iterator<T> {
		private final Iterator<FiniteSet<T>> setIterator;
		private Iterator<T> currentSetIterator;

		public FiniteCompoundSetIterator() {
			setIterator = components.iterator();
			currentSetIterator = new DoesNotHaveNextIterator();
			while (!currentSetIterator.hasNext() && setIterator.hasNext()) {
				currentSetIterator = setIterator.next().iterator();
			}
		}

		public boolean hasNext() {
			if (currentSetIterator.hasNext()) {
				return true;
			} else {
				while (!currentSetIterator.hasNext() && setIterator.hasNext()) {
					currentSetIterator = setIterator.next().iterator();
				}
				return currentSetIterator.hasNext();
			}
		}

		@Override
		public T next() {
			while (!currentSetIterator.hasNext() && setIterator.hasNext()) {
				currentSetIterator = setIterator.next().iterator();
			}
			return currentSetIterator.next();
		}
	}

	private class DoesNotHaveNextIterator implements Iterator<T> {

		@Override
		public boolean hasNext() {
			return false;
		}

		@Override
		public T next() {
			throw new NotImplementedYetException();
		}
	}

	@Override
	public boolean containsValue(T thing) {
		for (FiniteSet<T> set : components) {
			if (set.containsValue(thing)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void uniteWith(Set<T> s) {
		if (s instanceof InfiniteSet<?>) {
			throw new IllegalArgumentException("Cannot unite a finite set with an infinite set.");
		} else if (s instanceof FiniteCompoundSet<?>) {
			components.addAll(((FiniteCompoundSet) s).components);
		} else if (s instanceof FiniteSet<?>) {
			if (s instanceof StandardSet<?>) {
				components.add(new StandardSet<>((StandardSet<T>) s, s.getName()));
			} else {
				components.add((FiniteSet<T>) s);
			}
		} else {
			throw new NotImplementedYetException();
		}
	}
}
