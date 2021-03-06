package logic.model.universe.empty;

import logic.Nameable;
import logic.model.universe.Universe;
import logic.model.universe.VariableAlreadyExistsException;
import logic.model.universe.VariableNotAssignedException;
import logic.set.Dictionary;
import logic.set.Set;
import logic.set.finite.StandardSet;
import ophelia.exceptions.NotImplementedYetException;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Stack;

/**
 * @author Steven Weston
 */
public class EmptyUniverse<T extends Nameable> implements Universe<T, Dictionary<T>, Dictionary<Set<T>>> {

	private final Class<T> clazz;

	public EmptyUniverse(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public Dictionary<T> getUniversalSet() {
		return new EmptyDictionary<>();
	}

	@Override
	public Dictionary<Set<T>> getUniversalSetOfSets() {
		return new EmptyDictionary<>();
	}

	@Override
	public StandardSet<Object> getVariables() {
		return new StandardSet<>("∅");
	}

	@Override
	public Class<T> getTypeOfUniverse() {
		return clazz;
	}

	@Override
	public Object unassignVariable(String variableSymbol) {
		throw new NotImplementedYetException();
	}

	@Override
	public void assignVariable(String variableSymbol) throws VariableAlreadyExistsException {
		throw new NotImplementedYetException();
	}

	@Override
	public Object setVariable(String variableSymbol, Object object) throws VariableNotAssignedException {
		throw new NotImplementedYetException();
	}

	@Override
	public boolean contains(String value) {
		return false;
	}

	@Override
	public Type getTypeOfElement(String value) {
		return null;
	}

	@Override
	public Object get(String value) {
		throw new NotImplementedYetException();
	}

	@Override
	public Map<String, Stack<Object>> getBoundParameters() {
		throw new NotImplementedYetException();
	}
}
