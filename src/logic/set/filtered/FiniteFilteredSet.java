package logic.set.filtered;

import logic.Nameable;
import logic.set.SkippyIterator;
import logic.set.finite.Filter;
import logic.set.finite.FiniteSet;
import maths.number.integer.Integer;
import maths.number.integer.IntegerSummor;

import java.util.Iterator;

import static maths.number.integer.Integer.ONE;
import static maths.number.integer.Integer.ZERO;

/**
 * @author Steven Weston
 */
public class FiniteFilteredSet<T extends Nameable> extends AbstractFilteredSet<T, FiniteSet<T>> implements FiniteSet<T> {

	public FiniteFilteredSet(String name, FiniteSet<T> set, Filter<T> filter) {
		super(name, set, filter);
	}

	@Override
	public Integer size() {
		Integer elementCount = ZERO;
		IntegerSummor summor = new IntegerSummor();
		for (T t : this) {
			elementCount = summor.add(elementCount, ONE);
		}
		return elementCount;
	}

	@Override
	public Iterator<T> iterator() {
		return new SkippyIterator<>(set.iterator(), t -> {
			try {
				return filter.passesThrough(t);
			} catch (Exception e) {
				throw new RuntimeException("Dammit, this shouldn't happen.", e);
			}
		});
	}
}
