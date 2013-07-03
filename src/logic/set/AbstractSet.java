package logic.set;

import logic.function.reflexiveset.union.Union;

import java.util.Iterator;

/**
 * @author Steven Weston
 */
public abstract class AbstractSet<T> implements Set<T> {
	/**
	 * Take the union of a collection of sets.
	 * @param sets The collection of sets to unite.
	 * @param <T> The class that the sets contain.
	 * @return The union of the input.
	 */
	public static <T> Set<T> union(Set<? extends Set<T>> sets) {
		//todo: look into caching this
		String newName = Union.MULTARY_SYMBOL;
		Iterator<? extends Set<T>> iterator = sets.iterator();
		Set<T> set = iterator.next();
		Set<T> output = set.copy(newName);
		output.setName(output.getName() + " " + set.getName());
		while (iterator.hasNext()) {
			set = iterator.next();
			output.uniteWith(set);
			output.setName(output.getName() + " " + set.getName());
		}
		return output;
	}

	public static <T> Set<T> union(Set<T> unior, Set<T> uniand) {
		String newName = unior.getName() + " " + Union.BINARY_SYMBOL + " " + uniand.getName();
		Set<T> output = unior.copy(newName);
		output.uniteWith(uniand);
		return output;
	}
}
