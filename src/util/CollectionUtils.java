package util;

import logic.type.map.Extractor;

import java.util.*;

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
		if (iterator == null || !iterator.hasNext()) {
			return null;
		} else {
			return iterator.next();
		}
	}

	/**
	 * Get rid of the first and last members of a list.
	 * @param list
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> stripFirstAndLast(final List<T> list) {
		return list.subList(1, list.size() - 1);
	}

	public static String simpleNames(List<Class> classes) {
		List<String> output = new ArrayList<>();
		for (Class clazz : classes) {
			output.add(clazz.getSimpleName());
		}
		return "[" + StringUtils.join(output, ", ") + "]";
	}

	public static <T> T first(List<T> list) {
		return list.get(0);
	}

	public static <T> T last(List<T> list) {
		return list.get(list.size() - 1);
	}

	public static <K, V> String mapToString(Map<K, V> map, Extractor<V, String> valuePrinter) {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		for (Map.Entry<K, V> entry : map.entrySet()) {
			try {
				sb.append("\t" + entry.getKey() + " → " + valuePrinter.extract(entry.getValue()) + "\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		sb.append("}");
		return sb.toString();
	}

	public static String arrayToString(Object[] objects, String afterEach) {
		StringBuilder sb = new StringBuilder();
		for (Object object : objects) {
			sb.append(object.toString());
			sb.append(afterEach);
		}
		return sb.toString();
	}

	public static <T, U> List<U> filterList(List<T> collection, Class<U> clazz) {
		List<U> output = new ArrayList<>();
		for (T t : collection) {
			if (clazz.isInstance(t)) {
				output.add(clazz.cast(t));
			}
		}
		return output;
	}

	public static <K, V> Map<K, V> createMap(List<K> keys, List<V> values) {
		Map<K, V> output = new HashMap<>();
		int index = 0;
		for (K key : keys) {
			output.put(key, values.get(index));
			index++;
		}
		return output;
	}

	public static <K, V> Map<K, V> createMap(List<K> keys, V value) {
		Map<K, V> output = new HashMap<>();
		for (K key : keys) {
			output.put(key, value);
		}
		return output;
	}
}
