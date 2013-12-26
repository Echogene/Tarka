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

	public static String simpleNames(Collection<Class> classes) {
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

	public static <T> Set<T> createSet(T... elements) {
		HashSet<T> output = new HashSet<>();
		Collections.addAll(output, elements);
		return output;
	}

	/**
	 * Overlay onto a given map—whose values are sets—another such that:<ol>
	 *     <li>The first map will obtain all keys that it does not have already and values from the overlay</li>
	 *     <li>For each key contained in both maps, the first map's corresponding value set will be intersected with
	 *     value set of the overlay</li>
	 *     <li>The first map will retain all keys that the overlay does not contain</li>
	 * </ol>
	 *
	 * @param underlay
	 * @param overlay
	 * @param <K>
	 * @param <V>
	 */
	public static <K, V, S extends Set<? extends V>> void overlay(
			Map<K, S> underlay,
			Map<K, S> overlay
	) {
		for (Map.Entry<K, S> entry : overlay.entrySet()) {
			K key = entry.getKey();
			S value = entry.getValue();
			if (underlay.containsKey(key)) {
				underlay.get(key).retainAll(value);
			} else {
				underlay.put(key, value);
			}
		}
	}

	public static <K, V> void overlaySingleValues(Map<K, Set<V>> underlay, Map<K, V> overlay) {
		for (Map.Entry<K, V> entry : overlay.entrySet()) {
			K key = entry.getKey();
			V value = entry.getValue();
			Set<V> valueSet = Collections.singleton(value);
			if (underlay.containsKey(key)) {
				underlay.get(key).retainAll(valueSet);
			} else {
				underlay.put(key, valueSet);
			}
		}
	}

	public static <K, S extends Set<?>> Map<K, S> intersect(Collection<Map<K, S>> maps) {
		Iterator<Map<K, S>> iterator = maps.iterator();
		Map<K, S> output = new HashMap<>(iterator.next());
		while (iterator.hasNext()) {
			Map<K, S> map = iterator.next();
			output.keySet().retainAll(map.keySet());
		}
		for (Map.Entry<K, S> entry : output.entrySet()) {
			for (Map<K, S> map : maps) {
				entry.getValue().retainAll(map.get(entry.getKey()));
			}
		}
		return output;
	}
}
