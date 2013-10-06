package logic.model;

import logic.Nameable;
import logic.function.evaluable.Evaluable;
import logic.function.reflexive.ReflexiveFunction;
import logic.model.universe.Universe;
import logic.set.Set;

/**
 * @author Steven Weston
 */
public abstract class AbstractModelImpl<T extends Nameable> implements Model<T> {
	protected Universe<T> universe;
	protected Set<ReflexiveFunction<T>> functions;
	protected Set<Evaluable<T>> evaluables;

	@Override
	public Universe<T> getUniverse() {
		return universe;
	}

	@Override
	public Set<ReflexiveFunction<T>> getFunctions() {
		return functions;
	}

	@Override
	public Set<Evaluable<T>> getEvaluables() {
		return evaluables;
	}
}
