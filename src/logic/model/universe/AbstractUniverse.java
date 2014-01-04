package logic.model.universe;

import logic.Nameable;
import logic.factory.SimpleLogicReader;
import logic.set.Dictionary;
import logic.set.Set;

import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

/**
 * @author Steven Weston
 */
public abstract class AbstractUniverse<T extends Nameable, D extends Dictionary<T>, E extends Dictionary<Set<T>>>
		implements Universe<T, D, E> {

	protected final List<String> logicalConstants = Arrays.asList("⊤", "⊥");

	protected SimpleLogicReader<T> reader;

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
		return getBoundParameters().containsKey(value)
				|| getVariables().contains(value)
				|| getUniversalSet().contains(value)
				|| getUniversalSetOfSets().contains(value)
				|| logicalConstants.contains(value);
	}

	@Override
	public final Type getTypeOfElement(String value) {
		if (getBoundParameters().containsKey(value)) {
			return getBoundParameters().get(value).peek().getClass();
		} else if (getVariables().contains(value)) {
			return getVariables().get(value).getClass();
		} else if (getUniversalSet().contains(value)) {
			return getTypeOfUniverse();
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
		if (getBoundParameters().containsKey(value)) {
			return getBoundParameters().get(value).peek();
		} else if (getVariables().contains(value)) {
			return getVariables().get(value);
		} else if (getUniversalSet().contains(value)) {
			return getUniversalSet().get(value);
		} else if (getUniversalSetOfSets().contains(value)) {
			return getUniversalSetOfSets().get(value);
		} else if (logicalConstants.contains(value)) {
			return value.equals("⊤");
		}
		throw new UniverseException(MessageFormat.format("Unknown value {0} when getting object from universe.", value));
	}

	private static class UniverseException extends RuntimeException {

		public UniverseException(String s) {
			super(s);
		}
	}
}
