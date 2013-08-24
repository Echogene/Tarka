package maths.number.integer.sets.interval;

import logic.set.Set;
import maths.number.Number;

/**
 * @author Steven Weston
 */
public interface Interval<N extends Number> extends Set<N> {

	IntervalBound<N> getLowerBound();
	IntervalBound<N> getUpperBound();
}
