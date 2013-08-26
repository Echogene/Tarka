package maths.number.integer.sets.interval;

import logic.set.UnmodifiableSet;
import maths.number.Number;

/**
 * @author Steven Weston
 */
public interface Interval<N extends Number> extends UnmodifiableSet<N> {

	IntervalBound<N> getLowerBound();
	IntervalBound<N> getUpperBound();
}
