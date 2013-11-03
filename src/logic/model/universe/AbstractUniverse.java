package logic.model.universe;

import logic.Nameable;

/**
 * @author Steven Weston
 */
public abstract class AbstractUniverse<T extends Nameable> implements Universe<T> {
	@Override
	public Object unassignVariable(String variableSymbol) {
		return getVariables().remove(variableSymbol);
	}

	@Override
	public void assignVariable(String variableSymbol) throws VariableAlreadyExistsException {
		if (getVariables().contains(variableSymbol)) {
			throw new VariableAlreadyExistsException("The variable " + variableSymbol + " already is defined in the universe.");
		}
		getVariables().put(variableSymbol, null);
	}

	@Override
	public Object setVariable(String variableSymbol, T t) throws VariableNotAssignedException {
		if (!getVariables().contains(variableSymbol)) {
			throw new VariableNotAssignedException("The variable " + variableSymbol + " has not yet been assigned.");
		}
		return getVariables().put(variableSymbol, t);
	}
}
