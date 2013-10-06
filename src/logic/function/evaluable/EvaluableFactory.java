package logic.function.evaluable;

import logic.Nameable;
import logic.function.factory.FunctionFactory;
import logic.function.factory.construction.ValidatorAndConstructor;

import java.util.List;

/**
 * @author Steven Weston
 */
public abstract class EvaluableFactory<T extends Nameable, F extends Evaluable<T>> extends FunctionFactory<T, Boolean, F> {
	public EvaluableFactory(List<ValidatorAndConstructor<F>> constructors) {
		super(constructors);
	}
}
