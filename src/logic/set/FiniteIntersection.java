package logic.set;

import logic.Nameable;
import maths.number.integer.Integer;
import maths.number.integer.IntegerSummor;

import java.util.Iterator;

import static maths.number.integer.Integer.ONE;

/**
 * @author Steven Weston
 */
public class FiniteIntersection<T extends Nameable> extends FiniteSet<T> {

	private final java.util.Set<? extends Set<T>> intersection;

	FiniteIntersection(String name, java.util.Set<? extends Set<T>> intersection) {
		super(name);
		this.intersection = intersection;
	}

	FiniteIntersection(java.util.Set<? extends Set<T>> intersection) {
		this(null, intersection);
	}

	@Override
	public maths.number.integer.Integer size() {
		Integer elementCount = Integer.ZERO;
		IntegerSummor summor = new IntegerSummor();
		for (T t : this) {
			elementCount = summor.add(elementCount, ONE);
		}
		return elementCount;
	}

	@Override
	public Iterator<T> iterator() {
		return new FiniteInteresectionIterator();
	}

	private class FiniteInteresectionIterator implements Iterator<T> {

		private Iterator<T> setIterator;
		private T currentElement;

		public FiniteInteresectionIterator() {
			setIterator = smallestSet().iterator();
		}

		@Override
		public boolean hasNext() {
			if (!setIterator.hasNext()) {
				return false;
			}
			currentElement = setIterator.next();
			while (!containsValue(currentElement) && setIterator.hasNext()) {
				currentElement = setIterator.next();
			}
			return containsValue(currentElement);
		}

		@Override
		public T next() {
			if (currentElement == null) {
				currentElement = setIterator.next();
			}
			return currentElement;
		}
	}

	FiniteSet<T> smallestSet() {
		FiniteSet<T> smallestSet = null;
		for (Set<T> set : intersection) {
			if (set instanceof FiniteSet<?>) {
				FiniteSet<T> finiteSet = (FiniteSet<T>) set;
				Integer size = finiteSet.size();
				if (smallestSet == null || size.compareTo(smallestSet.size()) < 0) {
					smallestSet = finiteSet;
				}
			}
		}
		return smallestSet;
	}

	@Override
	public boolean containsValue(T thing) {
		for (Set<T> set : intersection) {
			if (!set.containsValue(thing)) {
				return false;
			}
		}
		return true;
	}
}
