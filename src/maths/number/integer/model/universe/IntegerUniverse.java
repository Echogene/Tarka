package maths.number.integer.model.universe;

import logic.model.universe.AbstractUniverse;
import logic.set.Dictionary;
import logic.set.Set;
import logic.set.finite.StandardSet;
import maths.number.integer.Integer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * @author Steven Weston
 */
public class IntegerUniverse extends AbstractUniverse<Integer> {
	PrimeNumberSet ℙ;
	IntegerSet ℤ;
	StandardSet<Object> variables;

	StandardSet<Set<Integer>> universalSetOfSets;
	private final List<String> logicalConstants = Arrays.asList("⊤", "⊥");

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
		return Integer.class;
	}

	@Override
	public boolean contains(String value) {
		return ℤ.contains(value)
				|| variables.contains(value)
				|| universalSetOfSets.contains(value)
				|| logicalConstants.contains(value);
	}

	@Override
	public Type getTypeOfElement(String value) {
		if (ℤ.contains(value)) {
			return Integer.class;
		} else if (variables.contains(value)) {
			return variables.get(value).getClass();
		} else if (universalSetOfSets.contains(value)) {
			return Set.class;
		} else if (logicalConstants.contains(value)) {
			return Boolean.class;
		} else {
			return null;
		}
	}

	@Override
	public Object get(String value) {
		if (ℤ.contains(value)) {
			return ℤ.get(value);
		} else if (variables.contains(value)) {
			return variables.get(value);
		} else if (universalSetOfSets.contains(value)) {
			return universalSetOfSets.get(value);
		} else if (logicalConstants.contains(value)) {
			return value.equals("⊤");
		}
		throw new UniverseException();
	}

	private class UniverseException extends RuntimeException {

	}
}
