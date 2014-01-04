package logic;

import logic.model.universe.AbstractUniverse;
import logic.model.universe.empty.EmptySet;
import logic.set.Set;
import logic.set.finite.StandardSet;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author Steven Weston
 */
public class TestClassUniverse extends AbstractUniverse<TestClass, StandardSet<TestClass>, StandardSet<Set<TestClass>>> {

	private StandardSet<TestClass> universalSet;

	private StandardSet<Set<TestClass>> universalSetOfSets;

	private StandardSet<Object> variableSet;

	private Map<String, Stack<Object>> boundParameters;

	public TestClassUniverse() {
		variableSet        = new StandardSet<>("variables");
		universalSet       = new StandardSet<>("universalSet");
		universalSetOfSets = new StandardSet<>("universalSetOfSets");
		universalSetOfSets.put(universalSet);
		universalSetOfSets.put(new EmptySet<>());
		boundParameters = new HashMap<>();
	}

	@Override
	public StandardSet<TestClass> getUniversalSet() {
		return universalSet;
	}

	@Override
	public StandardSet<Set<TestClass>> getUniversalSetOfSets() {
		return universalSetOfSets;
	}

	@Override
	public StandardSet<Object> getVariables() {
		return variableSet;
	}

	@Override
	public Class<TestClass> getTypeOfUniverse() {
		return TestClass.class;
	}

	@Override
	public Map<String, Stack<Object>> getBoundParameters() {
		return boundParameters;
	}

	public void setVariables(StandardSet<Object> variables) {
		this.variableSet = variables;
	}

	public void setUniverse(StandardSet<TestClass> universe) {
		this.universalSet = universe;
	}

	public void setUniversalSetOfSets(StandardSet<Set<TestClass>> universalSetOfSets) {
		this.universalSetOfSets = universalSetOfSets;
	}

	public void setUniversalSet(StandardSet<TestClass> universalSet) {
		universalSetOfSets.remove(universalSet.getName());
		this.universalSet = universalSet;
		universalSetOfSets.put(universalSet);
	}

	public void put(String s) {
		universalSet.put(s, new TestClass(s));
	}

	public void putSet(String setName, String... elements) {
		StandardSet<TestClass> set = new StandardSet<>(setName);
		for (String element : elements) {
			set.put(universalSet.get(element));
		}
		universalSetOfSets.put(setName, set);
	}
}
