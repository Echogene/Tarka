package maths.number.integer;

import maths.number.Subtractor;

/**
 * @author Steven Weston
 */
public class IntegerSubtractor implements Subtractor<Integer> {
	@Override
	public Integer subtract(Integer minuend, Integer subtrahend) {
		return new Integer(minuend.value.subtract(subtrahend.value));
	}
}
