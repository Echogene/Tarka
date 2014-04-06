package util;

import com.sun.istack.internal.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static util.NumberUtils.isOdd;

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

	public static <T> String identify(
			Collection<T> collection,
			Function<T, Boolean> identifier,
			Function<T, String> presentationFunction
	) {

		Map<T, String> map = new HashMap<>();
		for (T t : collection) {
			map.put(t, presentationFunction.apply(t));
		}
		StringBuilder sb = new StringBuilder();
		for (T t : collection) {
			sb.append(map.get(t));
		}
		sb.append("\n");
		for (T t : collection) {
			int length = map.get(t).length();
			if (identifier.apply(t)) {
				if (length == 1) {
					sb.append("│");
				} else if (length == 2) {
					sb.append("├╯");
				} else if (isOdd(length)) {
					sb.append("╰");
					repeatedlyAppend(length / 2 - 1, sb, "─");
					sb.append("┬");
					repeatedlyAppend(length / 2 - 1, sb, "─");
					sb.append("╯");
				} else {
					sb.append("╰");
					repeatedlyAppend(length / 2 - 2, sb, "─");
					sb.append("┬");
					repeatedlyAppend(length / 2 - 1, sb, "─");
					sb.append("╯");
				}
			} else {
				repeatedlyAppend(length, sb, " ");
			}
		}
		return sb.toString();
	}

	static void repeatedlyAppend(int times, StringBuilder sb, String toAppend) {
		for (int i = 0; i < times; i++) {
			sb.append(toAppend);
		}
	}

	public static String safeToString(@Nullable Object o) {
		if (o == null) {
			return "null";
		} else {
			return o.toString();
		}
	}
}
