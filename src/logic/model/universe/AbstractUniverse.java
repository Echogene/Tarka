package logic.model.universe;

import logic.Nameable;
import logic.set.Set;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * @author Steven Weston
 */
public abstract class AbstractUniverse<T extends Nameable> implements Universe<T> {

	protected final List<String> logicalConstants = Arrays.asList("⊤", "⊥");

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
	public Object setVariable(String variableSymbol, Object object) throws VariableNotAssignedException {
		if (!getVariables().contains(variableSymbol)) {
			throw new VariableNotAssignedException("The variable " + variableSymbol + " has not yet been assigned.");
		}
		return getVariables().put(variableSymbol, object);
	}

	@Override
	public final boolean contains(String value) {
		return getVariables().contains(value)
				|| getUniversalSet().contains(value)
				|| getUniversalSetOfSets().contains(value)
				|| logicalConstants.contains(value);
	}

	@Override
	public final Type getTypeOfElement(String value) {
		if (getUniversalSet().contains(value)) {
			return maths.number.integer.Integer.class;
		} else if (getVariables().contains(value)) {
			return getVariables().get(value).getClass();
		} else if (getUniversalSetOfSets().contains(value)) {
			return Set.class;
		} else if (logicalConstants.contains(value)) {
			return Boolean.class;
		} else {
			return null;
		}
	}

	@Override
	public final Object get(String value) {
		if (getUniversalSet().contains(value)) {
			return getUniversalSet().get(value);
		} else if (getVariables().contains(value)) {
			return getVariables().get(value);
		} else if (getUniversalSetOfSets().contains(value)) {
			return getUniversalSetOfSets().get(value);
		} else if (logicalConstants.contains(value)) {
			return value.equals("⊤");
		}
		throw new UniverseException();
	}

	private class UniverseException extends RuntimeException {

	}
}
