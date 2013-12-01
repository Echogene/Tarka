package logic.set;

import logic.type.map.Testor;

import java.util.Iterator;

/**
 * @author Steven Weston
 */
public class SkippyIterator<T> implements Iterator<T> {

	private final Iterator<T> iterator;
	private final Testor<T> testor;
	private T currentElement;

	/**
	 * Create an iterator that skips over all the elements of a given iterator that don't fulfill the given testor.
	 * @param iterator
	 * @param testor
	 */
	public SkippyIterator(Iterator<T> iterator, Testor<T> testor) {
		this.iterator = iterator;
		this.testor = testor;
	}

	@Override
	public boolean hasNext() {
		if (!iterator.hasNext()) {
			return false;
		}
		currentElement = iterator.next();
		while (!testor.test(currentElement) && iterator.hasNext()) {
			currentElement = iterator.next();
		}
		return testor.test(currentElement);
	}

	@Override
	public T next() {
		if (currentElement == null) {
			currentElement = iterator.next();
		}
		return currentElement;
	}

}
