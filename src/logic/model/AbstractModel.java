package logic.model;

import logic.Nameable;
import logic.factory.SimpleLogicReader;
import logic.function.Function;
import logic.function.factory.FunctionFactory;
import logic.model.universe.Universe;
import logic.model.universe.VariableAlreadyExistsException;
import logic.model.universe.VariableNotAssignedException;

/**
 * @author Steven Weston
 */
public abstract class AbstractModel<T extends Nameable, U extends Universe<T>> implements Model<T, U, SimpleLogicReader<T>> {

	private final U universe;

	private SimpleLogicReader<T> reader;

	protected AbstractModel(U universe) {
		this.universe = universe;
	}

	@Override
	public U getUniverse() {
		return universe;
	}

	public SimpleLogicReader<T> getReader() {
		return reader;
	}

	@Override
	public void assignVariable(String assignee) throws VariableAlreadyExistsException {
		universe.assignVariable(assignee);
	}

	@Override
	public void setVariable(String assignee, Object evaluate) throws VariableNotAssignedException {
		universe.setVariable(assignee, evaluate);
	}

	@Override
	public void unassignVariable(String assignee) {
		universe.unassignVariable(assignee);
	}

	@Override
	public void addFactory(FunctionFactory<T, ?, ? extends Function<T, ?>> factory) {
		reader.addFactory(factory);
	}

	public void setReader(SimpleLogicReader<T> reader) {
		this.reader = reader;
	}
}
