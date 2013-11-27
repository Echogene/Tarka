package logic.model.universe;

import logic.Nameable;
import logic.set.Dictionary;
import logic.set.Set;
import logic.set.finite.StandardSet;

import java.lang.reflect.Type;

/**
 * @author Steven Weston
 */
public interface Universe<T extends Nameable> {

	Dictionary<T> getUniversalSet();

	Dictionary<Set<T>> getUniversalSetOfSets();

	StandardSet<Object> getVariables();

	Set<T> getValueSet();

	Class<T> getTypeOfUniverse();

	/**
	 * Unassign the variable with the given symbol.
	 * @param variableSymbol the symbol of the variable to unassign
	 * @return The value to which the variable was assigned previously.
	 */
	Object unassignVariable(String variableSymbol);

	void assignVariable(String variableSymbol) throws VariableAlreadyExistsException;

	/**
	 * Set the value of a variable with the given symbol to another value.
	 * @param variableSymbol the symbol of the variable to revalue
	 * @param object the new value to give the variable
	 * @return The value to which the variable was assigned previously.
	 * @throws VariableNotAssignedException
	 */
	Object setVariable(String variableSymbol, Object object) throws VariableNotAssignedException;

	/**
	 * @param value the name of the object of which to test existence
	 * @return whether the universe contains an object with the given name
	 */
	boolean contains(String value);

	/**
	 * Get the type assigned to an element of the universe (including variables).
	 * @param value
	 * @return
	 */
	Type getTypeOfElement(String value);

	Object get(String value);
}
