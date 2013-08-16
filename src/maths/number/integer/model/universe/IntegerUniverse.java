package maths.number.integer.model.universe;

import logic.model.universe.AbstractUniverse;
import logic.set.ModifiableSet;
import logic.set.Set;
import logic.set.finite.StandardSet;
import maths.number.integer.Integer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author Steven Weston
 */
public class IntegerUniverse extends AbstractUniverse<Integer> {
	PrimeNumberSet ℙ;
	IntegerSet ℤ;
	StandardSet<Integer> variables;

	ModifiableSet<Set<Integer>> universalSetOfSets;

	public IntegerUniverse() {
		ℤ = new IntegerSet("ℤ");
		ℙ = new PrimeNumberSet("ℙ");
		variables = new StandardSet<>("variables");

		universalSetOfSets = new StandardSet<>("sets");
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
	public ModifiableSet<Integer> getVariables() {
		return variables;
	}

	@Override
	public Set<Integer> getValueSet() {
		throw new NotImplementedException();
	}
}
