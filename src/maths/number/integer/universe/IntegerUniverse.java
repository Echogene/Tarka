package maths.number.integer.universe;

import logic.model.universe.Universe;
import logic.model.universe.VariableAlreadyExistsException;
import logic.model.universe.VariableNotAssignedException;
import logic.set.NamedSet;
import logic.set.Set;
import maths.number.integer.Integer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author Steven Weston
 */
public class IntegerUniverse implements Universe<maths.number.integer.Integer> {
	IntegerUniversalSet universalSet;

	public IntegerUniverse() {
		universalSet = new IntegerUniversalSet("universe");
	}

	@Override
	public Set<Integer> getUniversalSet() {
		return universalSet;
	}

	@Override
	public Set<Set<Integer>> getUniversalSetOfSets() {
		throw new NotImplementedException();
	}

	@Override
	public Set<Integer> getVariables() {
		// todo
		return new NamedSet<>("NotImplemented");
	}

	@Override
	public Set<Integer> getValueSet() {
		throw new NotImplementedException();
	}

	@Override
	public Integer unassignVariable(String variableSymbol) {
		throw new NotImplementedException();
	}

	@Override
	public void assignVariable(String variableSymbol) throws VariableAlreadyExistsException {
		throw new NotImplementedException();
	}

	@Override
	public Integer setVariable(String variableSymbol, Integer integer) throws VariableNotAssignedException {
		throw new NotImplementedException();
	}

	@Override
	public void setUniversalSet(Set<Integer> set) {
		throw new NotImplementedException();
	}

	@Override
	public void setUniversalSetOfSets(Set<Set<Integer>> sets) {
		throw new NotImplementedException();
	}
}
