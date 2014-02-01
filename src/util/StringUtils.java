package util;

import java.util.Collection;

/**
 * @author Steven Weston
 */
public class StringUtils {

	public static String addCharacterAfterEveryNewline(String lines, char character) {
		return character + lines.replace("\n", "\n" + character);
	}

	/**
	 * Join a list of strings using another string.
	 * @param list
	 * @param joiner
	 * @return
	 */
	public static String join(Collection<String> list, String joiner) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (String string : list) {
			if (!first) {
				sb.append(joiner);
			}
			sb.append(string);
			first = false;
		}
		return sb.toString();
	}

	/**
	 * Count the number of times a character occurs in a string.
	 * @param character
	 * @param string
	 * @return
	 */
	public static int count(String character, String string) {
		return string.length() - string.replace(character, "").length();
	}

	public static String substring(String string, int startIndex, int endIndex) {
		if (endIndex == -1) {
			return string.substring(startIndex);
		} else {
			return string.substring(startIndex, endIndex);
		}
	}
}
