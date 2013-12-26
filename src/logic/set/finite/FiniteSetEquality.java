package logic.set.finite;

/**
 * @author Steven Weston
 */
public class FiniteSetEquality {

	public static <T> boolean areEqual(FiniteSet<T> set1, FiniteSet<T> set2) {
		if (set1.size().compareTo(set2.size()) != 0) {
			return false;
		}
		for (T t : set1) {
			if (!set2.containsValue(t)) {
				return false;
			}
		}
		return true;
	}
}
