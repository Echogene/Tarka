package util;

import java.util.Iterator;

/**
 * @author Steven Weston
 */
public class CurrentIterator<T> implements Iterator<T> {

	private final Iterator<T> iterator;
	private T current;

	public CurrentIterator(Iterator<T> iterator) {
		this.iterator = iterator;
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public T next() {
		if (!iterator.hasNext()) {
			current = null;
		} else {
			current = iterator.next();
		}
		return current;
	}

	public T current() {
		if (current == null) {
			if (iterator.hasNext()) {
				current = iterator.next();
			}
		}
		return current;
	}

	@Override
	public String toString() {
		return "Current: " + current;
	}
}
