package maths.number.integer.model.universe;

import logic.model.universe.AbstractUniverse;
import logic.set.Dictionary;
import logic.set.Set;
import logic.set.finite.StandardSet;
import maths.number.integer.Integer;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author Steven Weston
 */
public class IntegerUniverse extends AbstractUniverse<Integer> {

	private final IntegerSet ℤ;
	private final StandardSet<Object> variables;
	private final Map<String, Stack<Object>> boundParameters;

	private final IntegerUniverseSetOfSets universalSetOfSets;

	public IntegerUniverse() {
		ℤ = new IntegerSet("ℤ");

		variables = new StandardSet<>("variables");

		universalSetOfSets = new IntegerUniverseSetOfSets(ℤ);
		boundParameters = new HashMap<>();
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
	public Class<Integer> getTypeOfUniverse() {
		return Integer.class;
	}

	@Override
	public Map<String, Stack<Object>> getBoundParameters() {
		return boundParameters;
	}
}
