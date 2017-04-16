package maths.number.integer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class IntegerMultipliorTest {

	private final IntegerMultiplior multiplior = new IntegerMultiplior();

	@Test
	public void testMultiply() throws Exception {
		assertEquals(new Integer("6"),      multiplior.multiply(new Integer("2"),   new Integer("3")));
		assertEquals(new Integer("72"),     multiplior.multiply(new Integer("8"),   new Integer("9")));
		assertEquals(new Integer("121401"), multiplior.multiply(new Integer("123"), new Integer("987")));
	}
}
