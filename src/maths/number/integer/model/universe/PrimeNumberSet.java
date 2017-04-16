package maths.number.integer.model.universe;

import maths.number.integer.Integer;
import maths.number.integer.prime.SimplePrimalityChecker;

/**
 * @author Steven Weston
 */
public class PrimeNumberSet extends IntegerSet {

	private static final SimplePrimalityChecker CHECKER = new SimplePrimalityChecker();

	public PrimeNumberSet(String name) {
		super(name);
	}

	@Override
	public boolean contains(String string) {
		// We probably shouldn't be assigning anything to the prime number set, so we don't need to check the hashMap.
		return super.contains(string) && CHECKER.isPrime(new Integer(string));
	}

	@Override
	public boolean containsValue(Integer thing) {
		return CHECKER.isPrime(thing);
	}
}
