package logic.type.map;

import javafx.util.Pair;
import util.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Steven Weston
 */
public class MapWithErrors<K, V> {

	private final Map<K, V> passedValues;

	private final Map<K, String> failedValues;

	public MapWithErrors(final Collection<K> keys, final Extractor<K, V> extractor) {
		passedValues = new HashMap<>();
		failedValues = new HashMap<>();
		fillFrom(keys, extractor);
	}

	private void fillFrom(final Collection<K> keys, final Extractor<K, V> extractor) {
		for (K key : keys) {
			try {
				passedValues.put(key, extractor.extract(key));
			} catch (ExtractorException e) {
				failedValues.put(key, e.getMessage());
			}
		}
	}

	@SafeVarargs
	public MapWithErrors(final Collection<K> keys, final Pair<Testor<K>, Extractor<K, V>>... pairs) {
		passedValues = new HashMap<>();
		failedValues = new HashMap<>();
		fillFrom(keys, pairs);
	}

	@SafeVarargs
	private final void fillFrom(final Collection<K> keys, final Pair<Testor<K>, Extractor<K, V>>... pairs) {
		for (K key : keys) {
			for (Pair<Testor<K>, Extractor<K, V>> pair : pairs) {
				Testor<K> testor = pair.getKey();
				if (testor.test(key)) {
					Extractor<K, V> extractor = pair.getValue();
					try {
						passedValues.put(key, extractor.extract(key));
					} catch (ExtractorException e) {
						failedValues.put(key, e.getMessage());
					}
				}
			}
		}
	}

	public boolean allFailed() {
		return passedValues.isEmpty();
	}

	public boolean allPassed() {
		return failedValues.isEmpty();
	}

	public boolean hasUniquePass() {
		return passedValues.size() == 1;
	}

	public boolean hasAmbiguousPasses() {
		return passedValues.size() > 1;
	}

	public V getUniquePassedValue() {
		if (hasUniquePass()) {
			return passedValues.values().iterator().next();
		} else {
			return null;
		}
	}

	public Map<K, V> getPassedValues() {
		return passedValues;
	}

	public String concatenateErrorMessages() {
		return StringUtils.join(failedValues.values(), "\n");
	}
}
