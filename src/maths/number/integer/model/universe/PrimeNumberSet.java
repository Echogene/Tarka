package maths.number.integer.model.universe;

import maths.number.integer.Integer;
import maths.number.integer.prime.PrimalityChecker;

/**
 * @author Steven Weston
 */
public class PrimeNumberSet extends IntegerSet {

	private PrimalityChecker checker;

	public PrimeNumberSet(String name, PrimalityChecker checker) {
		super(name);
		this.checker = checker;
	}

	@Override
	public boolean contains(String string) {
		// We probably shouldn't be assigning anything to the prime number set, so we don't need to check the hashMap.
		return super.contains(string) && checker.isPrime(new Integer(string));
	}

	@Override
	public boolean containsValue(Integer thing) {
		return checker.isPrime(thing);
	}
}
