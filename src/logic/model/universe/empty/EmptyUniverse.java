package logic.model.universe.empty;

import logic.Nameable;
import logic.model.universe.Universe;
import logic.model.universe.VariableAlreadyExistsException;
import logic.model.universe.VariableNotAssignedException;
import logic.set.Dictionary;
import logic.set.Set;
import logic.set.finite.StandardSet;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Type;

/**
 * @author Steven Weston
 */
public class EmptyUniverse<T extends Nameable> implements Universe<T> {

	private Class<T> clazz;

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
	public Set<T> getValueSet() {
		return new EmptySet<>();
	}

	@Override
	public Class<T> getTypeOfUniverse() {
		return clazz;
	}

	@Override
	public Object unassignVariable(String variableSymbol) {
		throw new NotImplementedException();
	}

	@Override
	public void assignVariable(String variableSymbol) throws VariableAlreadyExistsException {
		throw new NotImplementedException();
	}

	@Override
	public Object setVariable(String variableSymbol, Object object) throws VariableNotAssignedException {
		throw new NotImplementedException();
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
		throw new NotImplementedException();
	}
}
