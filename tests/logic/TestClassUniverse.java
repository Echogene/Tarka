package logic;

import logic.model.universe.AbstractUniverse;
import logic.set.Dictionary;
import logic.set.Set;
import logic.set.finite.StandardSet;

/**
 * @author Steven Weston
 */
public class TestClassUniverse extends AbstractUniverse<TestClass> {

	private StandardSet<TestClass> universalSet;

	private StandardSet<Set<TestClass>> universalSetOfSets;

	private StandardSet<Object> variableSet;

	@Override
	public Dictionary<TestClass> getUniversalSet() {
		return universalSet;
	}

	@Override
	public Dictionary<Set<TestClass>> getUniversalSetOfSets() {
		return universalSetOfSets;
	}

	@Override
	public StandardSet<Object> getVariables() {
		return variableSet;
	}

	private StandardSet<TestClass> getVariableSetInUniverse() {
		StandardSet<TestClass> output = new StandardSet<>("variablesInUniverse");
		for (Object o : variableSet) {
			if (o instanceof TestClass) {
				output.put(o.toString(), (TestClass) o);
			}
		}
		return output;
	}

	@Override
	public Class<TestClass> getTypeOfUniverse() {
		return TestClass.class;
	}

	public TestClassUniverse() {
		variableSet        = new StandardSet<>("variables");
		universalSet       = new StandardSet<>("universalSet");
		universalSetOfSets = new StandardSet<>("universalSetOfSets");
		universalSetOfSets.put(universalSet);
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
