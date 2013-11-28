package logic.set;

import logic.Nameable;
import logic.function.set.union.Union;
import logic.set.finite.FiniteCompoundSet;
import logic.set.infinite.InfiniteCompoundSet;
import logic.set.infinite.InfiniteSet;

import java.util.Iterator;

/**
 * @author Steven Weston
 */
public class Uniter {

	public static <T extends Nameable> Set<T> unite(java.util.Set<? extends Set<T>> sets) {
		//todo: look into caching this
		boolean infinite = false;
		for (Set<T> set : sets) {
			if (set instanceof InfiniteSet<?>) {
				infinite = true;
			}
		}
		CompoundSet<T> output;
		if (infinite) {
			output = new InfiniteCompoundSet<>(getNameForUnion(sets));
		} else {
			output = new FiniteCompoundSet<>(getNameForUnion(sets));
		}
		for (Set<T> set : sets) {
			output.uniteWith(set);
		}
		return output;
	}

	private static <T extends Nameable> String getNameForUnion(java.util.Set<? extends Set<T>> sets) {
		if (sets.size() == 2) {
			Iterator<? extends Set<T>> iterator = sets.iterator();
			return iterator.next().getName() + " " + Union.BINARY_SYMBOL + " " + iterator.next().getName();
		} else {
			String output = Union.MULTARY_SYMBOL;
			for (Set<T> set : sets) {
				output += " " + set.getName();
			}
			return output;
		}
	}
}
