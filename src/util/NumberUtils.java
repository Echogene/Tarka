package util;

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

	public static boolean isEven(int integer) {
		return 0 == (integer % 2);
	}

	public static boolean isOdd(int integer) {
		return 1 == (integer % 2);
	}
}
