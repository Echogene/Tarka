package maths.number.integer.prime;

import maths.number.integer.Integer;

import java.math.BigInteger;

import static java.math.BigInteger.*;

/**
 * @author Steven Weston
 */
public class SimplePrimalityChecker implements PrimalityChecker {

	private static final BigInteger TWO = valueOf(2L);
	private static final BigInteger THREE = valueOf(3L);
	private static final BigInteger SIX = valueOf(6L);

	@Override
	public boolean isPrime(Integer integer) {
		BigInteger v = integer.getValue();
		if (v.compareTo(ONE) <= 0) {
			return false;
		}
		BigInteger root = floorSquareRoot(v);
		BigInteger i;
		if ((TWO.compareTo(root) <= 0 && v.mod(TWO).equals(ZERO))
				|| (THREE.compareTo(root) <= 0 && v.mod(THREE).equals(ZERO))) {
			return false;
		}
		// All prime numbers > 3 are of the form 6k±1, so we only need to test those.
		for (i = SIX; i.compareTo(root) <= 0; i = i.add(SIX)) {
			if (v.mod(i.subtract(ONE)).equals(ZERO)
					|| v.mod(i.add(ONE)).equals(ZERO)) {
				return false;
			}
		}
		return true;
	}

	static BigInteger floorSquareRoot(BigInteger x) throws IllegalArgumentException {
		if (x.compareTo(ZERO) < 0) {
			throw new IllegalArgumentException("Negative argument.");
		}
		if (x.equals(ZERO) || x.equals(ONE)) {
			return x;
		}
		BigInteger y;
		//noinspection StatementWithEmptyBody
		for (y = x.divide(TWO);
		     y.compareTo(x.divide(y)) > 0;
		     y = ((x.divide(y)).add(y)).divide(TWO));
		return y;
	}
}
