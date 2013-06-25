package logic.model.universe;

import logic.Nameable;
import logic.set.Set;

/**
 * @author Steven Weston
 */
public interface Universe<T extends Nameable> {
	Set<T> getUniversalSet();

	Set<Set<T>> getUniversalSetOfSets();

	Set<T> getVariables();

	Set<T> getValueSet();

	T unassignVariable(String variableSymbol);

	void assignVariable(String variableSymbol) throws VariableAlreadyExistsException;

	T setVariable(String variableSymbol, T t) throws VariableNotAssignedException;

	void setUniversalSet(Set<T> set);

	void setUniversalSetOfSets(Set<Set<T>> sets);
}
