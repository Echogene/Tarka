package logic.set;

import logic.Nameable;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author Steven Weston
 */
public class InfiniteSet<T extends Nameable> extends NamedSet<T> {
	public InfiniteSet(String name) {
		super(name);
	}

	@Override
	public Iterator<T> iterator() {
		throw new InfiniteSetException("Cannot iterate over infinite set.");
	}

	@Override
	public int size() {
		throw new InfiniteSetException("Size is infinite.");
	}

	@Override
	public Collection<T> values() {
		throw new InfiniteSetException("Cannot get a finite set of values.");
	}
}
