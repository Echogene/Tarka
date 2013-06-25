package logic.model;

import logic.Nameable;
import logic.evaluable.Evaluable;
import logic.function.reflexive.ReflexiveFunction;
import logic.model.universe.Universe;
import logic.set.Set;

/**
 * An interface representing a model in the logical sense.  A model has a universe, a collection of functions and a
 * collection of evaluables.  A model is also a factory for its own universe, functions and evaluables.
 * @author Steven Weston
 */
public interface Model<T extends Nameable> {
	public Universe<T> getUniverse();

	public Set<ReflexiveFunction<T>> getFunctions();

	public Set<Evaluable<T>> getEvaluables();
}
