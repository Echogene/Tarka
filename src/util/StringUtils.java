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
}
