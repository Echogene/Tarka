package maths.number.integer.model.universe;

import maths.number.integer.Integer;

/**
 * @author Steven Weston
 */
public class UnmodifiableIntegerSet extends IntegerSet {
	public UnmodifiableIntegerSet(String name) {
		super(name);
	}

	@Override
	public void put(maths.number.integer.Integer thing) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Integer put(String string, Integer thing) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Integer remove(String name) {
		throw new UnsupportedOperationException();
	}
}
