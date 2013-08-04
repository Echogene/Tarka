package util;

import java.util.Iterator;
import java.util.List;

/**
 * @author Steven Weston
 */
public class CollectionUtils {
	/**
	 * Avoid throwing an error when accessing an out-of-bounds index in a list by returning null in that case.
	 * @param list The list whose item we are getting.
	 * @param index The index of the element we want.
	 * @param <T> The type of the elements in the list.
	 * @return The indexth element of the list or null if the index is out-of-bounds.
	 */
	public static <T> T safeGet(List<T> list, int index) {
		if (list == null || index >= list.size() || index < 0) {
			return null;
		}
		return list.get(index);
	}

	public static <T> T safeNext(Iterator<T> iterator) {
		if (iterator.hasNext()) {
			return iterator.next();
		} else {
			return null;
		}
	}

	/**
	 * Get rid of the first and last members of a list.
	 * @param list
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> stripFirstAndLast(List<T> list) {
		return list.subList(1, list.size() - 1);
	}

	/**
	 * Join a list of strings using another string.
	 * @param list
	 * @param joiner
	 * @return
	 */
	public static String join(List<String> list, String joiner) {
		String output = "";
		boolean first = true;
		for (String string : list) {
			if (!first) {
				output += joiner;
			}
			output += string;
			first = false;
		}
		return output;
	}
}
