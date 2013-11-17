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
	 *
	 * @param variableSymbol
	 * @return The value to which the variable was assigned previously.
	 */
	Object unassignVariable(String variableSymbol);

	void assignVariable(String variableSymbol) throws VariableAlreadyExistsException;

	/**
	 *
	 * @param variableSymbol
	 * @param t
	 * @return The value to which the variable was assigned previously.
	 * @throws VariableNotAssignedException
	 */
	Object setVariable(String variableSymbol, Object t) throws VariableNotAssignedException;

	/**
	 * @param value
	 * @return whether the universe contains an object with the given name
	 */
	boolean contains(String value);

	/**
	 * Get the type assigned to an element of the universe (including variables).
	 * @param value
	 * @return
	 */
	Type getTypeOfElement(String value);
}
