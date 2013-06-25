package logic.function.reflexive;

import logic.Nameable;
import logic.function.ParameterNotFoundException;
import logic.model.universe.Universe;

/**
 * @author Steven Weston
 */
public abstract class AbstractReflexiveFunction<T extends Nameable> implements ReflexiveFunction<T> {
	@Override
	public T evaluate(Universe<T> universe) throws ParameterNotFoundException {
		try {
			return evaluate(universe.getVariables());
		} catch (ParameterNotFoundException e) {
			return evaluate(universe.getUniversalSet());
		}
	}
}
