package maths.number.integer;

import java.math.BigInteger;

/**
 * @author Steven Weston
 */
public class Integer implements maths.number.Number {

	public static final Integer ONE = new Integer(1);
	BigInteger value;

	public Integer(int value) {
		this.value = new BigInteger(java.lang.Integer.toString(value));
	}

	public Integer(String value) {
		this.value = new BigInteger(value.replace("−", "-"));
	}

	Integer(BigInteger value) {
		this.value = value;
	}

	@Override
	public String getName() {
		return toString();
	}

	@Override
	public String toString() {
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

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	public BigInteger getValue() {
		return value;
	}

	@Override
	public int compareTo(maths.number.Number o) {
		if (!(o instanceof Integer)) {
			throw new UnsupportedOperationException("Cannot compare an integer to an arbitrary Number.");
		}
		Integer other = (Integer) o;
		return this.value.compareTo(other.value);
	}
}
