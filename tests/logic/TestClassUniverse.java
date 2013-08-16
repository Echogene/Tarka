package logic;

import logic.model.universe.AbstractUniverse;
import logic.set.ModifiableSet;
import logic.set.Set;
import logic.set.Uniter;
import logic.set.finite.StandardSet;

import java.util.LinkedHashSet;

/**
 * @author Steven Weston
 */
public class TestClassUniverse extends AbstractUniverse<TestClass> {
	protected ModifiableSet<TestClass> universalSet;

	protected ModifiableSet<Set<TestClass>> universalSetOfSets;

	protected ModifiableSet<TestClass> variableSet;

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

	public void setUniversalSetOfSets(ModifiableSet<Set<TestClass>> universalSetOfSets) {
		this.universalSetOfSets = universalSetOfSets;
	}

	public void setUniversalSet(ModifiableSet<TestClass> universalSet) {
		universalSetOfSets.remove(universalSet.getName());
		this.universalSet = universalSet;
		universalSetOfSets.put(universalSet);
	}
}
