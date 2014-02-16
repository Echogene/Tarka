package util;

import logic.type.map.Extractor;

import java.util.*;

/**
 * @author Steven Weston
 */
public class MapUtils {

	public static <K, V> String mapToString(Map<K, V> map, Extractor<V, String> valuePrinter) {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		for (Map.Entry<K, V> entry : map.entrySet()) {
			try {
				sb.append("\t")
						.append(entry.getKey())
						.append(" → ")
						.append(valuePrinter.extract(entry.getValue()))
						.append("\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		sb.append("}");
		return sb.toString();
	}

	public static <K, V> Map<K, V> createMap(K key, V value) {
		Map<K, V> output = new HashMap<>();
		output.put(key, value);
		return output;
	}

	public static <K, V> LinkedHashMap<K, V> createLinkedHashMap(K key, V value) {
		LinkedHashMap<K, V> output = new LinkedHashMap<>();
		output.put(key, value);
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
