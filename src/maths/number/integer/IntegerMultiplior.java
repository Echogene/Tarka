package maths.number.integer;

import maths.number.Multiplior;

import java.math.BigInteger;

/**
 * @author Steven Weston
 */
public class IntegerMultiplior implements Multiplior<Integer> {

	@Override
	public Integer multiply(Integer multiplier, Integer multiplicand) {
		return new Integer(multiplier.getValue().multiply(multiplicand.getValue()));
	}

	@Override
	public Integer produce(Iterable<Integer> factors) {
		BigInteger product = BigInteger.ONE;
		for (Integer factor : factors) {
			product = product.multiply(factor.getValue());
		}
		return new Integer(product);
	}
}
