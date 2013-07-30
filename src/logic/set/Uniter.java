package logic.set;

import logic.Nameable;
import logic.function.set.union.Union;
import logic.set.finite.FiniteSet;
import logic.set.infinite.CompoundSet;
import logic.set.infinite.InfiniteSet;

import java.util.Iterator;

/**
 * @author Steven Weston
 */
public class Uniter {
	public static <T extends Nameable> Set<T> unite(Set<? extends Set<T>> sets) {
		//todo: look into caching this
		boolean infinite = false;
		for (Set<T> set : sets) {
			if (set instanceof InfiniteSet<?>) {
				infinite = true;
			}
		}
		Set<T> output;
		if (infinite) {
			output = new CompoundSet<>(getNameForUnion(sets));
		} else {
			output = new FiniteSet<>(getNameForUnion(sets));
		}
		for (Set<T> set : sets) {
			output.uniteWith(set);
		}
		return output;
	}

	private static <T extends Nameable> String getNameForUnion(Set<? extends Set<T>> sets) {
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
