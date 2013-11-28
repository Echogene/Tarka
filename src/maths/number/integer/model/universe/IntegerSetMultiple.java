package maths.number.integer.model.universe;

import maths.number.integer.Integer;

import java.math.BigInteger;

import static java.math.BigInteger.ZERO;

/**
 * @author Steven Weston
 */
public class IntegerSetMultiple extends IntegerSet {

	private final BigInteger multiple;

	public IntegerSetMultiple(String name) {
		super(name);
		String numberSubstring = name.substring(0, name.length() - 1);
		this.multiple = new BigInteger(numberSubstring).abs();
	}

	@Override
	public boolean contains(String string) {
		if (!super.contains(string)) {
			return false;
		}
		Integer integer = super.get(string);
		if (multiple.equals(ZERO)) {
			return integer.getValue().equals(ZERO);
		}
		return integer.getValue().mod(multiple).equals(ZERO);
	}

	@Override
	public boolean containsValue(Integer thing) {
		if (!super.containsValue(thing)) {
			return false;
		}
		if (multiple.equals(ZERO)) {
			return thing.getValue().equals(ZERO);
		}
		return thing.getValue().mod(multiple).equals(ZERO);
	}
}
