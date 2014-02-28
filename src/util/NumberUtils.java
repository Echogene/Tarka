package util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class NumberUtils {

	public static String ordinal(int integer) {
		int modOneHundred = integer % 100;
		int tens = modOneHundred / 10;
		int units = modOneHundred % 10;
		if (tens == 1) {
			return integer + "th";
		} else {
			switch (units) {
				case 1:
					return integer + "st";
				case 2:
					return integer + "nd";
				case 3:
					return integer + "rd";
				default:
					return integer + "th";
			}
		}
	}

	@Test
	public void testOrdinal() {
		assertEquals("0th", ordinal(0));
		assertEquals("1st", ordinal(1));
		assertEquals("2nd", ordinal(2));
		assertEquals("3rd", ordinal(3));
		assertEquals("4th", ordinal(4));
		assertEquals("11th", ordinal(11));
		assertEquals("12th", ordinal(12));
		assertEquals("13th", ordinal(13));
		assertEquals("100th", ordinal(100));
		assertEquals("101st", ordinal(101));
		assertEquals("102nd", ordinal(102));
		assertEquals("103rd", ordinal(103));
		assertEquals("104th", ordinal(104));
		assertEquals("111th", ordinal(111));
		assertEquals("112th", ordinal(112));
		assertEquals("113th", ordinal(113));
	}

	public static boolean isEven(int integer) {
		return 0 == (integer % 2);
	}

	public static boolean isOdd(int integer) {
		return 1 == (integer % 2);
	}
}
