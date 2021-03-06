package logic.set.finite;

import logic.Nameable;
import logic.set.ImpoundSet;
import logic.set.NamedSet;
import logic.set.Set;
import logic.set.SkippyIterator;
import maths.number.integer.Integer;
import maths.number.integer.IntegerSummor;

import java.util.HashSet;
import java.util.Iterator;

import static maths.number.integer.Integer.ONE;

/**
 * @author Steven Weston
 */
public class FiniteImpoundSet<T extends Nameable> extends NamedSet<T> implements ImpoundSet<T>, FiniteSet<T> {

	private final java.util.Set<Set<T>> intersection;

	public FiniteImpoundSet(String name) {
		super(name);
		this.intersection = new HashSet<>();
	}

	FiniteImpoundSet(String name, java.util.Set<Set<T>> intersection) {
		super(name);
		this.intersection = intersection;
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
		return new SkippyIterator<>(smallestSet().iterator(), this::containsValue);
	}

	@Override
	public void intersectWith(Set<T> set) {
		intersection.add(set);
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
