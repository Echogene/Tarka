package logic;

import logic.model.universe.AbstractUniverse;
import logic.set.Dictionary;
import logic.set.Set;
import logic.set.Uniter;
import logic.set.finite.StandardSet;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * @author Steven Weston
 */
public class TestClassUniverse extends AbstractUniverse<TestClass> {
	protected StandardSet<TestClass> universalSet;

	protected StandardSet<Set<TestClass>> universalSetOfSets;

	protected StandardSet<Object> variableSet;
	private final List<String> logicalConstants = Arrays.asList("⊤", "⊥");

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

	@Override
	public Set<TestClass> getValueSet() {
		java.util.Set<Set<TestClass>> unitees = new LinkedHashSet<>();
		unitees.add(universalSet);
		unitees.add(getVariableSetInUniverse());
		return Uniter.unite(unitees);
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

	@Override
	public boolean contains(String value) {
		return variableSet.contains(value)
				|| universalSet.contains(value)
				|| universalSetOfSets.contains(value)
				|| logicalConstants.contains(value);
	}

	@Override
	public Type getTypeOfElement(String value) {
		if (variableSet.contains(value)) {
			return variableSet.get(value).getClass();
		} else if (universalSetOfSets.contains(value)) {
			return Set.class;
		} else if (logicalConstants.contains(value)) {
			return Boolean.class;
		} else {
			return TestClass.class;
		}
	}

	@Override
	public Object get(String value) {
		if (variableSet.contains(value)) {
			return variableSet.get(value);
		} else if (universalSetOfSets.contains(value)) {
			return universalSetOfSets.get(value);
		} else if (logicalConstants.contains(value)) {
			return value.equals("⊤");
		} else {
			return universalSet.get(value);
		}
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
