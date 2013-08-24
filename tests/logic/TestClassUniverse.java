package logic;

import logic.model.universe.AbstractUniverse;
import logic.set.Dictionary;
import logic.set.Set;
import logic.set.Uniter;
import logic.set.finite.StandardSet;

import java.util.LinkedHashSet;

/**
 * @author Steven Weston
 */
public class TestClassUniverse extends AbstractUniverse<TestClass> {
	protected Dictionary<TestClass> universalSet;

	protected StandardSet<Set<TestClass>> universalSetOfSets;

	protected StandardSet<TestClass> variableSet;

	@Override
	public Dictionary<TestClass> getUniversalSet() {
		return universalSet;
	}

	@Override
	public Dictionary<Set<TestClass>> getUniversalSetOfSets() {
		return universalSetOfSets;
	}

	@Override
	public StandardSet<TestClass> getVariables() {
		return variableSet;
	}

	@Override
	public Set<TestClass> getValueSet() {
		java.util.Set<Set<TestClass>> unitees = new LinkedHashSet<>();
		unitees.add(universalSet);
		unitees.add(variableSet);
		return Uniter.unite(unitees);
	}

	public TestClassUniverse() {
		variableSet        = new StandardSet<>("variables");
		universalSet       = new StandardSet<>("universalSet");
		universalSetOfSets = new StandardSet<>("universalSetOfSets");
		universalSetOfSets.put(universalSet);
	}

	public void setVariables(StandardSet<TestClass> variables) {
		this.variableSet = variables;
	}

	public void setUniverse(StandardSet<TestClass> universe) {
		this.universalSet = universe;
	}

	public void setUniversalSetOfSets(StandardSet<Set<TestClass>> universalSetOfSets) {
		this.universalSetOfSets = universalSetOfSets;
	}

	public void setUniversalSet(Dictionary<TestClass> universalSet) {
		universalSetOfSets.remove(universalSet.getName());
		this.universalSet = universalSet;
		universalSetOfSets.put(universalSet);
	}
}
