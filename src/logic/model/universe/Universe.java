package logic.model.universe;

import logic.Nameable;
import logic.set.Dictionary;
import logic.set.Set;
import logic.set.finite.StandardSet;

/**
 * @author Steven Weston
 */
public interface Universe<T extends Nameable> {
	Dictionary<T> getUniversalSet();

	Dictionary<Set<T>> getUniversalSetOfSets();

	StandardSet<T> getVariables();

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
