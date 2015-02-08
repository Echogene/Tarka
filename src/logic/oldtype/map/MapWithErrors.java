package logic.oldtype.map;

import javafx.util.Pair;
import util.Extractor;
import util.ExtractorException;
import util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A map created using an {@link util.Extractor} that may fail for some values.  The error messages may be collected
 * for the failed values.
 * @author Steven Weston
 */
@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
public class MapWithErrors<K, V> implements ErrorMap {

	private final Map<K, V> passedValues;

	private final Map<K, Exception> failedValues;

	public MapWithErrors(final Collection<? extends K> keys, final Extractor<K, V> extractor) {
		passedValues = new HashMap<>();
		failedValues = new HashMap<>();
		if (keys != null && !keys.isEmpty()) {
			fillFrom(keys, extractor);
		}
	}

	private void fillFrom(final Collection<? extends K> keys, final Extractor<K, V> extractor) {
		for (K key : keys) {
			try {
				passedValues.put(key, extractor.extract(key));
			} catch (ExtractorException e) {
				failedValues.put(key, e);
			}
		}
	}

	@SafeVarargs
	public MapWithErrors(final Collection<? extends K> keys, final Pair<Testor<K>, Extractor<K, V>>... pairs) {
		passedValues = new HashMap<>();
		failedValues = new HashMap<>();
		fillFrom(keys, pairs);
	}

	@SafeVarargs
	private final void fillFrom(final Collection<? extends K> keys, final Pair<Testor<K>, Extractor<K, V>>... pairs) {
		for (K key : keys) {
			for (Pair<Testor<K>, Extractor<K, V>> pair : pairs) {
				Testor<K> testor = pair.getKey();
				if (testor.test(key)) {
					Extractor<K, V> extractor = pair.getValue();
					try {
						passedValues.put(key, extractor.extract(key));
					} catch (ExtractorException e) {
						failedValues.put(key, e);
					}
				}
			}
		}
	}

	@Override
	public boolean allFailed() {
		return passedValues.isEmpty();
	}

	@Override
	public boolean allPassed() {
		return failedValues.isEmpty();
	}

	/**
	 * @return whether there was exactly one key that passed
	 */
	public boolean hasTotallyUniquePass() {
		return passedValues.size() == 1;
	}

	/**
	 * @return whether there was exactly one value mapped to by the passed keys
	 */
	public boolean hasUniquePass() {
		HashSet<Object> passedValueSet = new HashSet<>();
		passedValueSet.addAll(passedValues.values());
		return passedValueSet.size() == 1;
	}

	/**
	 * @return whether there was more than one value mapped to by the passed keys
	 */
	public boolean hasTotallyAmbiguousPasses() {
		HashSet<Object> passedValueSet = new HashSet<>();
		passedValueSet.addAll(passedValues.values());
		return passedValueSet.size() > 1;
	}

	/**
	 * @return whether there was more than one key that passed
	 */
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

	public K getUniquePassedKey() {
		if (hasTotallyUniquePass()) {
			return passedValues.keySet().iterator().next();
		} else {
			return null;
		}
	}

	public Map<K, V> getPassedValues() {
		return passedValues;
	}

	@Override
	public String concatenateErrorMessages() {
		return StringUtils.join(getErrorMessages(), "\n");
	}

	@Override
	public Collection<String> getErrorMessages() {
		return failedValues.values()
				.stream()
				.map(Exception::getMessage)
				.collect(Collectors.toList());
	}
}
