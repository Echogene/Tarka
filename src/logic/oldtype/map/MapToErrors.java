package logic.oldtype.map;

import javafx.util.Pair;
import util.StringUtils;

import java.util.*;

/**
 * @author Steven Weston
 */
public class MapToErrors<K> implements ErrorMap {

	private final Set<K> passedKeys;
	private final Map<K, String> failedValues;

	public MapToErrors(final Collection<K> keys, final Checkor<K> checkor) {
		passedKeys = new HashSet<>();
		failedValues = new HashMap<>();
		fillFrom(keys, checkor);
	}

	private void fillFrom(final Collection<K> keys, final Checkor<K> checkor) {
		for (K key : keys) {
			try {
				checkor.check(key);
				passedKeys.add(key);
			} catch (CheckorException e) {
				failedValues.put(key, e.getMessage());
			}
		}
	}

	@SafeVarargs
	public MapToErrors(final Collection<K> keys, final Pair<Testor<K>, Checkor<K>>... pairs) {
		passedKeys = new HashSet<>();
		failedValues = new HashMap<>();
		fillFrom(keys, pairs);
	}

	@SafeVarargs
	private final void fillFrom(final Collection<K> keys, final Pair<Testor<K>, Checkor<K>>... pairs) {
		for (K key : keys) {
			for (Pair<Testor<K>, Checkor<K>> pair : pairs) {
				Testor<K> testor = pair.getKey();
				if (testor.test(key)) {
					Checkor<K> checkor = pair.getValue();
					try {
						checkor.check(key);
						passedKeys.add(key);
					} catch (CheckorException e) {
						failedValues.put(key, e.getMessage());
					}
				}
			}
		}
	}

	@Override
	public boolean allFailed() {
		return passedKeys.isEmpty();
	}

	@Override
	public boolean allPassed() {
		return failedValues.isEmpty();
	}

	@Override
	public String concatenateErrorMessages() {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<K, String> entry : failedValues.entrySet()) {
			sb.append(entry.getKey());
			sb.append(" → \n");
			sb.append(StringUtils.addCharacterAfterEveryNewline(entry.getValue(), '\t'));
			sb.append("\n");
		}
		return sb.toString();
	}

	@Override
	public Collection<String> getErrorMessages() {
		return failedValues.values();
	}

	public Set<K> getPassedKeys() {
		return passedKeys;
	}
}
