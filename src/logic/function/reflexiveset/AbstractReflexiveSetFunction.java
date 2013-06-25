package logic.function.reflexiveset;

import logic.Nameable;
import logic.function.ParameterNotFoundException;
import logic.model.universe.Universe;
import logic.set.Set;

/**
 * @author Steven Weston
 */
public abstract class AbstractReflexiveSetFunction<T extends Nameable> implements ReflexiveSetFunction<T> {
	public Set<T> evaluate(Universe<T> universe) throws ParameterNotFoundException {
		return evaluate(universe.getUniversalSetOfSets());
	}
}
