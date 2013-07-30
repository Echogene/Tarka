package logic;

import logic.model.universe.AbstractUniverse;
import logic.set.FiniteSet;
import logic.set.ModifiableSet;
import logic.set.Set;

/**
 * @author Steven Weston
 */
public class TestClassUniverse extends AbstractUniverse<TestClass> {
	protected ModifiableSet<TestClass> universalSet;

	protected ModifiableSet<Set<TestClass>> universalSetOfSets;

	protected ModifiableSet<TestClass> variableSet;

	protected Set<TestClass> valueSet;

	@Override
	public ModifiableSet<TestClass> getUniversalSet() {
		return universalSet;
	}

	@Override
	public ModifiableSet<Set<TestClass>> getUniversalSetOfSets() {
		return universalSetOfSets;
	}

	@Override
	public ModifiableSet<TestClass> getVariables() {
		return variableSet;
	}

	@Override
	public Set<TestClass> getValueSet() {
		valueSet = universalSet.copy("values");
		valueSet.uniteWith(variableSet);
		return valueSet;
	}

	public TestClassUniverse() {
		variableSet        = new FiniteSet<>("variables");
		universalSet       = new FiniteSet<>("universalSet");
		universalSetOfSets = new FiniteSet<>("universalSetOfSets");
		universalSetOfSets.put(universalSet);
	}

	public void setVariables(FiniteSet<TestClass> variables) {
		this.variableSet = variables;
	}

	public void setUniverse(FiniteSet<TestClass> universe) {
		this.universalSet = universe;
	}

	public void setUniversalSetOfSets(ModifiableSet<Set<TestClass>> universalSetOfSets) {
		this.universalSetOfSets = universalSetOfSets;
	}

	public void setUniversalSet(ModifiableSet<TestClass> universalSet) {
		universalSetOfSets.remove(universalSet.getName());
		this.universalSet = universalSet;
		universalSetOfSets.put(universalSet);
	}
}
