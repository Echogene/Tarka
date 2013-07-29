package util;

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

	public static <T> List<T> stripFirstAndLast(List<T> list) {
		return list.subList(1, list.size() - 1);
	}
}
