package logic.set.filtered;

import logic.Nameable;
import logic.set.finite.Filter;
import logic.set.finite.FiniteSet;
import maths.number.integer.Integer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Iterator;

/**
 * @author Steven Weston
 */
public class FiniteFilteredSet<T extends Nameable> extends AbstractFilteredSet<T, FiniteSet<T>> implements FiniteSet<T> {

	public FiniteFilteredSet(String name, FiniteSet<T> set, Filter<T> filter) {
		super(name, set, filter);
	}

	@Override
	public Integer size() {
		throw new NotImplementedException();
	}

	@Override
	public Iterator<T> iterator() {
		throw new NotImplementedException();
	}
}
