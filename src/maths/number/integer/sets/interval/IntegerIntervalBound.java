package maths.number.integer.sets.interval;

import maths.number.integer.Integer;

import java.math.BigInteger;

import static java.math.BigInteger.ONE;

/**
 * @author Steven Weston
 */
public class IntegerIntervalBound extends IntervalBound<Integer> {

	public IntegerIntervalBound(Integer bound, BoundType boundType) {
		super(bound, boundType);
	}

	public boolean equals(IntegerIntervalBound other, boolean lower) {
		if (this.getBoundType().equals(other.getBoundType())) {
			return this.getBound().equals(other.getBound());
		} else {
			BigInteger otherValue = other.getBound().getValue();
			BigInteger thisValue = this.getBound().getValue();
			if (this.getBoundType().equals(BoundType.CLOSED)) {
				if (lower) {
					// [1 = (0
					return thisValue.subtract(ONE).equals(otherValue);
				} else {
					// 1] = 2)
					return otherValue.subtract(ONE).equals(thisValue);
				}
			} else {
				if (lower) {
					// (0 = [1
					return otherValue.subtract(ONE).equals(thisValue);
				} else {
					// 2) = 1]
					return thisValue.subtract(ONE).equals(otherValue);
				}
			}
		}
	}
}
