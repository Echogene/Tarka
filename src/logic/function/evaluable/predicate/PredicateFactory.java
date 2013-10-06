package logic.function.evaluable.predicate;

import logic.Nameable;
import logic.function.evaluable.EvaluableFactory;
import logic.function.factory.construction.ValidatorAndConstructor;

import java.util.List;

/**
 * @author Steven Weston
 */
public abstract class PredicateFactory<T extends Nameable, F extends Predicate<T>> extends EvaluableFactory<T, F> {
	public PredicateFactory(List<ValidatorAndConstructor<F>> validatorAndConstructors) {
		super(validatorAndConstructors);
	}
}
