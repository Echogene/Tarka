package maths.number.integer.prime;

import maths.number.integer.Integer;
import org.junit.Test;

import java.math.BigInteger;

import static maths.number.integer.prime.SimplePrimalityChecker.floorSquareRoot;
import static org.junit.Assert.*;

/**
 * @author Steven Weston
 */
public class SimplePrimalityCheckerTest {

	private final SimplePrimalityChecker checker;

	public SimplePrimalityCheckerTest() {
		checker = new SimplePrimalityChecker();
	}

	@Test
	public void testPrimalityCheck() throws Exception {
		checkNotPrime(1);
		checkPrime(2);
		checkPrime(3);
		checkNotPrime(4);
		checkPrime(5);
		checkNotPrime(6);
		checkPrime(7);
		checkNotPrime(8);
		checkNotPrime(9);
		checkNotPrime(10);
		checkPrime(11);
		checkNotPrime(12);
		checkPrime(13);

		// Test some largish Carol primes.
		assertTrue(checker.isPrime(new Integer("68718952447")));
		assertTrue(checker.isPrime(new Integer("274876858367")));
		assertTrue(checker.isPrime(new Integer("4398042316799")));
		assertTrue(checker.isPrime(new Integer("1125899839733759")));
	}

	private void checkNotPrime(int value) {
		assertFalse(checker.isPrime(new Integer(value)));
	}

	private void checkPrime(int value) {
		assertTrue(checker.isPrime(new Integer(value)));
	}

	@Test
	public void testSquareRoot() throws Exception {
		for (int i = 1; i < 10000; i++) {
			checkPair((int) Math.floor(Math.sqrt(i)), i);
		}
		checkPair("10000000000000000000000000000000", "100000000000000000000000000000000000000000000000000000000000000");
	}

	private void checkPair(int root, int square) {
		checkPair(java.lang.Integer.toString(root), java.lang.Integer.toString(square));
	}

	private void checkPair(String root, String square) {
		assertEquals(new BigInteger(root), floorSquareRoot(new BigInteger(square)));
	}
}
