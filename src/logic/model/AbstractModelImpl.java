package logic.model;

import logic.Nameable;
import logic.evaluable.Evaluable;
import logic.function.reflexive.ReflexiveFunction;
import logic.model.universe.Universe;
import logic.set.Set;
import logic.set.finite.StandardSet;

/**
 * @author Steven Weston
 */
public abstract class AbstractModelImpl<T extends Nameable> implements Model<T> {
	protected Universe<T> universe;
	protected StandardSet<ReflexiveFunction<T>> functions;
	protected StandardSet<Evaluable<T>> evaluables;

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
