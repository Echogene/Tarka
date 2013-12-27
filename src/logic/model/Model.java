package logic.model;

import logic.Nameable;
import logic.function.Function;
import logic.function.factory.FunctionFactory;
import logic.model.universe.Universe;
import logic.model.universe.VariableAlreadyExistsException;
import logic.model.universe.VariableNotAssignedException;
import reading.reading.Reader;

/**
 * @author Steven Weston
 */
public interface Model<T extends Nameable, U extends Universe<T>, R extends Reader<? extends Function<T, ?>>> {

	public U getUniverse();

	public R getReader();

	void assignVariable(String assignee) throws VariableAlreadyExistsException;

	void setVariable(String assignee, Object evaluate) throws VariableNotAssignedException;

	void unassignVariable(String assignee);

	void addFactory(FunctionFactory<T, ?, ? extends Function<T, ?>> factory);

	Object pushParameter(String parameterSymbol, Object value);

	Object popParameter(String parameterSymbol) throws VariableNotAssignedException;
}
