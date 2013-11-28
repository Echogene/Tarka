package logic.set;

import logic.Nameable;
import logic.function.set.intersection.Intersection;
import logic.set.finite.FiniteImpoundSet;
import logic.set.finite.FiniteSet;
import logic.set.infinite.InfiniteImpoundSet;

import java.util.Iterator;

/**
 * @author Steven Weston
 */
public class Intersector {

	public static <T extends Nameable> Set<T> intersect(java.util.Set<? extends Set<T>> sets) {
		//todo: look into caching this
		boolean finite = false;
		for (Set<T> set : sets) {
			if (set instanceof FiniteSet<?>) {
				finite = true;
			}
		}
		ImpoundSet<T> output;
		if (finite) {
			output = new FiniteImpoundSet<>(getNameForIntersection(sets));
		} else {
			output = new InfiniteImpoundSet<>(getNameForIntersection(sets));
		}
		for (Set<T> set : sets) {
			output.intersectWith(set);
		}
		return output;
	}

	private static <T extends Nameable> String getNameForIntersection(java.util.Set<? extends Set<T>> sets) {
		if (sets.size() == 2) {
			Iterator<? extends Set<T>> iterator = sets.iterator();
			return iterator.next().getName() + " " + Intersection.BINARY_SYMBOL + " " + iterator.next().getName();
		} else {
			String output = Intersection.MULTARY_SYMBOL;
			for (Set<T> set : sets) {
				output += " " + set.getName();
			}
			return output;
		}
	}
}
