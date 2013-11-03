package util;

/**
 * @author Steven Weston
 */
public class StringUtils {

	public static String addCharacterAfterEveryNewline(String lines, char character) {
		return character + lines.replace("\n", "\n" + character);
	}
}
