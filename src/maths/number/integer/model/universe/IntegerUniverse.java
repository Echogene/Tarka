package maths.number.integer.model.universe;

import logic.model.universe.AbstractUniverse;
import logic.set.NamedSet;
import logic.set.Set;
import maths.number.integer.Integer;
import maths.number.integer.prime.SimplePrimalityChecker;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author Steven Weston
 */
public class IntegerUniverse extends AbstractUniverse<Integer> {
	PrimeNumberSet ℙ;
	IntegerSet ℤ;
	NamedSet<Integer> variables;

	Set<Set<Integer>> universalSetOfSets;

	public IntegerUniverse() {
		ℤ = new IntegerSet("ℤ");
		ℙ = new PrimeNumberSet("ℙ", new SimplePrimalityChecker());
		variables = new NamedSet<>("variables");

		universalSetOfSets = new NamedSet<>("sets");
		universalSetOfSets.put(ℤ);
		universalSetOfSets.put(ℙ);
		universalSetOfSets.put(variables);
	}

	@Override
	public Set<Integer> getUniversalSet() {
		return ℤ;
	}

	@Override
	public Set<Set<Integer>> getUniversalSetOfSets() {
		return universalSetOfSets;
	}

	@Override
	public Set<Integer> getVariables() {
		return variables;
	}

	@Override
	public Set<Integer> getValueSet() {
		throw new NotImplementedException();
	}

	@Override
	public void setUniversalSet(Set<Integer> set) {
		throw new NotImplementedException();
	}

	@Override
	public void setUniversalSetOfSets(Set<Set<Integer>> sets) {
		throw new NotImplementedException();
	}
}
