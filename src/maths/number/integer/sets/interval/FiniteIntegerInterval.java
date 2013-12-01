package maths.number.integer.sets.interval;

import logic.set.NamedSet;
import logic.set.finite.FiniteSet;
import maths.number.Subtractor;
import maths.number.Summor;
import maths.number.integer.Integer;
import maths.number.integer.IntegerSubtractor;
import maths.number.integer.IntegerSummor;

import java.util.Iterator;

import static maths.number.integer.Integer.ONE;
import static maths.number.integer.sets.interval.IntervalBound.BoundType.CLOSED;

/**
 * @author Steven Weston
 */
public class FiniteIntegerInterval extends NamedSet<Integer> implements Interval<Integer>, FiniteSet<Integer> {

	private final Subtractor<Integer> subtractor = new IntegerSubtractor();
	private final Summor<Integer> summor = new IntegerSummor();

	private final IntervalBound<Integer> upperBound;
	private final IntervalBound<Integer> lowerBound;

	public FiniteIntegerInterval(String name, IntervalBound<Integer> upperBound, IntervalBound<Integer> lowerBound) {
		super(name);
		this.upperBound = upperBound;
		this.lowerBound = lowerBound;
	}

	@Override
	public Integer size() {
		Integer upperBound;
		if (CLOSED.equals(getUpperBound().getBoundType())) {
			upperBound = summor.add(getUpperBound().getBound(), ONE);
		} else {
			upperBound = getUpperBound().getBound();
		}
		Integer lowerBound;
		if (CLOSED.equals(getLowerBound().getBoundType())) {
			lowerBound = getLowerBound().getBound();
		} else {
			lowerBound = summor.add(getLowerBound().getBound(), ONE);
		}
		return subtractor.subtract(upperBound, lowerBound);
	}

	@Override
	public Iterator<Integer> iterator() {
		return new Iterator<Integer>() {
			Integer currentValue = lowerBound.getBoundType() == CLOSED ?
					subtractor.subtract(lowerBound.getBound(), ONE) :
					lowerBound.getBound();
			private final Integer maximum = upperBound.getBoundType() == CLOSED ?
					upperBound.getBound() :
					subtractor.subtract(upperBound.getBound(), ONE);

			@Override
			public boolean hasNext() {
				return maximum.compareTo(currentValue) > 0;
			}

			@Override
			public Integer next() {
				currentValue = summor.add(currentValue, ONE);
				return currentValue;
			}
		};
	}

	@Override
	public boolean containsValue(Integer thing) {
		int lowerComparison = lowerBound.getBound().compareTo(thing);
		int upperComparison = upperBound.getBound().compareTo(thing);
		return (lowerBound.getBoundType() == CLOSED ? lowerComparison <= 0 : lowerComparison < 0)
				&& (upperBound.getBoundType() == CLOSED ? upperComparison >= 0 : upperComparison > 0);
	}

	@Override
	public IntervalBound<Integer> getLowerBound() {
		return lowerBound;
	}

	@Override
	public IntervalBound<Integer> getUpperBound() {
		return upperBound;
	}
}
