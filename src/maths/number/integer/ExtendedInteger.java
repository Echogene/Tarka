package maths.number.integer;

import maths.number.Number;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author Steven Weston
 */
public class ExtendedInteger implements Number {

	private static final ExtendedInteger POSITIVE_INFINITY = new ExtendedInteger(1);
	private static final ExtendedInteger NEGATIVE_INFINITY = new ExtendedInteger(-1);

	private static final int POSITIVE = 1;
	private static final int FINITE = 0;
	private static final int NEGATIVE = -1;

	private final Integer integer;
	private final int infinitySign;

	public ExtendedInteger(Integer integer) {
		this.integer = integer;
		this.infinitySign = FINITE;
	}

	private ExtendedInteger(int infinitySign) {
		this.integer = null;
		this.infinitySign = infinitySign;
	}

	@Override
	public String getName() {
		if (infinitySign == POSITIVE) {
			return "∞";
		} else if (infinitySign == NEGATIVE) {
			return "−∞";
		} else {
			return integer.getName();
		}
	}

	@Override
	public int compareTo(Number o) {
		throw new NotImplementedException();
	}
}
