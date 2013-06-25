package logic;

import logic.model.universe.AbstractUniverse;
import logic.set.NamedSet;
import logic.set.Set;

/**
 * @author Steven Weston
 */
public class TestClassUniverse extends AbstractUniverse<TestClass> {
	protected Set<TestClass> universalSet;

	protected Set<Set<TestClass>> universalSetOfSets;

	protected Set<TestClass> variableSet;

	protected Set<TestClass> valueSet;

	@Override
	public Set<TestClass> getUniversalSet() {
		return universalSet;
	}

	@Override
	public Set<Set<TestClass>> getUniversalSetOfSets() {
		return universalSetOfSets;
	}

	@Override
	public Set<TestClass> getVariables() {
		return variableSet;
	}

	@Override
	public Set<TestClass> getValueSet() {
		valueSet = universalSet.copy("values");
		valueSet.uniteWith(variableSet);
		return valueSet;
	}

	public TestClassUniverse() {
		variableSet        = new NamedSet<>("variables");
		universalSet       = new NamedSet<>("universalSet");
		universalSetOfSets = new NamedSet<>("universalSetOfSets");
		universalSetOfSets.put(universalSet);
	}

	public void setVariables(NamedSet<TestClass> variables) {
		this.variableSet = variables;
	}

	public void setUniverse(NamedSet<TestClass> universe) {
		this.universalSet = universe;
	}

	public void setUniversalSetOfSets(Set<Set<TestClass>> universalSetOfSets) {
		this.universalSetOfSets = universalSetOfSets;
	}

	public void setUniversalSet(Set<TestClass> universalSet) {
		universalSetOfSets.remove(universalSet.getName());
		this.universalSet = universalSet;
		universalSetOfSets.put(universalSet);
	}
}
