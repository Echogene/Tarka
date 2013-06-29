package maths.number.integer;

import java.math.BigInteger;

/**
 * @author Steven Weston
 */
public class Integer implements maths.number.Number {
	BigInteger value;

	public Integer(int value) {
		this.value = new BigInteger(java.lang.Integer.toString(value));
	}

	public Integer(String value) {
		this.value = new BigInteger(value);
	}

	Integer(BigInteger value) {
		this.value = value;
	}

	@Override
	public String getName() {
		return value.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Integer)) {
			return false;
		}
		Integer other = (Integer) o;
		return value.equals(other.value);
	}
}
