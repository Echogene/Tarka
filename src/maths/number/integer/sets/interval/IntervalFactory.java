package maths.number.integer.sets.interval;

import maths.number.Number;

import static maths.number.integer.sets.interval.IntervalBound.BoundType;

/**
 * @author Steven Weston
 */
public interface IntervalFactory<N extends Number> {
	Interval<N> createElement(BoundType lowerType, N lowerBound, N upperBound, BoundType upperType);
}
