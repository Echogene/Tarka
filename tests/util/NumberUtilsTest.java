package util;

import junit.framework.TestCase;
import org.junit.Test;

import static util.NumberUtils.ordinal;

/**
 * @author Steven Weston
 */
public class NumberUtilsTest extends TestCase {

	@Test
	public void testOrdinal() {
		assertEquals("0th",   ordinal(0));
		assertEquals("1st",   ordinal(1));
		assertEquals("2nd",   ordinal(2));
		assertEquals("3rd",   ordinal(3));
		assertEquals("4th",   ordinal(4));
		assertEquals("11th",  ordinal(11));
		assertEquals("12th",  ordinal(12));
		assertEquals("13th",  ordinal(13));
		assertEquals("100th", ordinal(100));
		assertEquals("101st", ordinal(101));
		assertEquals("102nd", ordinal(102));
		assertEquals("103rd", ordinal(103));
		assertEquals("104th", ordinal(104));
		assertEquals("111th", ordinal(111));
		assertEquals("112th", ordinal(112));
		assertEquals("113th", ordinal(113));
	}
}
