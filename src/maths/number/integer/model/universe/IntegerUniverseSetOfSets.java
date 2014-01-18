package maths.number.integer.model.universe;

import logic.model.universe.empty.EmptySet;
import logic.set.Dictionary;
import logic.set.Set;
import logic.set.finite.StandardSet;
import maths.number.integer.Integer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author Steven Weston
 */
public class IntegerUniverseSetOfSets implements Dictionary<Set<Integer>> {

	private final StandardSet<Set<Integer>> simpleSets = new StandardSet<>(null);

	public IntegerUniverseSetOfSets(IntegerSet ℤ) {
		simpleSets.put(ℤ);
		simpleSets.put(new PrimeNumberSet("ℙ"));
		simpleSets.put(new EmptySet<>());
	}

	@Override
	public Set<Integer> get(String string) {
		if (simpleSets.contains(string)) {
			return simpleSets.get(string);
		}
		if (stringRepresentsIntegerSetMultiple(string)) {
			return new IntegerSetMultiple(string);
		}
		return null;
	}

	@Override
	public boolean contains(String string) {
		return simpleSets.contains(string) || stringRepresentsIntegerSetMultiple(string);
	}

	@Override
	public boolean containsValue(Set<Integer> thing) {
		return simpleSets.containsValue(thing) || thing instanceof IntegerSetMultiple;
	}

	@Override
	public String getName() {
		throw new NotImplementedException();
	}

	private boolean stringRepresentsIntegerSetMultiple(String string) {
		return string.matches("\\-?[\\d]+ℤ");
	}
}
