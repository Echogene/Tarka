package maths.number.integer.model.universe;

import logic.model.universe.AbstractUniverse;
import logic.set.Dictionary;
import logic.set.Set;
import logic.set.finite.StandardSet;
import maths.number.integer.Integer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Type;

/**
 * @author Steven Weston
 */
public class IntegerUniverse extends AbstractUniverse<Integer> {
	PrimeNumberSet ℙ;
	IntegerSet ℤ;
	StandardSet<Object> variables;

	StandardSet<Set<Integer>> universalSetOfSets;

	public IntegerUniverse() {
		ℤ = new IntegerSet("ℤ");
		ℙ = new PrimeNumberSet("ℙ");
		variables = new StandardSet<>("variables");

		universalSetOfSets = new StandardSet<>("sets");
		universalSetOfSets.put(ℤ);
		universalSetOfSets.put(ℙ);
//		universalSetOfSets.put(variables);
	}

	@Override
	public Dictionary<Integer> getUniversalSet() {
		return ℤ;
	}

	@Override
	public Dictionary<Set<Integer>> getUniversalSetOfSets() {
		return universalSetOfSets;
	}

	@Override
	public StandardSet<Object> getVariables() {
		return variables;
	}

	@Override
	public Set<Integer> getValueSet() {
		throw new NotImplementedException();
	}

	@Override
	public Class<Integer> getTypeOfUniverse() {
		throw new NotImplementedException();
	}

	@Override
	public boolean contains(String value) {
		throw new NotImplementedException();
	}

	@Override
	public Type getTypeOfElement(String value) {
		throw new NotImplementedException();
	}
}
