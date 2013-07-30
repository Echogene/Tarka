package logic.model.universe;

import logic.Nameable;
import logic.set.ModifiableSet;
import logic.set.Set;

/**
 * @author Steven Weston
 */
public interface Universe<T extends Nameable> {
	Set<T> getUniversalSet();

	Set<Set<T>> getUniversalSetOfSets();

	ModifiableSet<T> getVariables();

	Set<T> getValueSet();

	/**
	 * @param variableSymbol
	 * @return The value to which the variable was assigned previously.
	 */
	T unassignVariable(String variableSymbol);

	void assignVariable(String variableSymbol) throws VariableAlreadyExistsException;

	/**
	 * @param variableSymbol
	 * @param t
	 * @return The value to which the variable was assigned previously.
	 * @throws VariableNotAssignedException
	 */
	T setVariable(String variableSymbol, T t) throws VariableNotAssignedException;
}
