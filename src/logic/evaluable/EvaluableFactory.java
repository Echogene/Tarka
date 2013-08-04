package logic.evaluable;

import logic.Nameable;
import logic.function.Function;
import logic.function.factory.FunctionFactory;
import logic.function.factory.construction.ValidatorAndConstructor;

import java.util.List;

/**
 * @author Steven Weston
 */
public abstract class EvaluableFactory<T extends Nameable> extends FunctionFactory<T, Boolean> {
	public EvaluableFactory(List<ValidatorAndConstructor<Function<T, Boolean>>> constructors) {
		super(constructors);
	}
}
