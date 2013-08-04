package logic.evaluable.predicate;

import logic.Nameable;
import logic.evaluable.EvaluableFactory;
import logic.function.Function;
import logic.function.factory.construction.ValidatorAndConstructor;

import java.util.List;

/**
 * @author Steven Weston
 */
public abstract class PredicateFactory<T extends Nameable> extends EvaluableFactory<T> {
	public PredicateFactory(List<ValidatorAndConstructor<Function<T, Boolean>>> validatorAndConstructors) {
		super(validatorAndConstructors);
	}
}
