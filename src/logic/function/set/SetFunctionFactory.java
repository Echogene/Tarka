package logic.function.set;

import logic.Nameable;
import logic.function.factory.FunctionFactory;
import logic.function.factory.construction.ValidatorAndConstructor;
import logic.set.Set;

import java.util.List;

/**
 * @author Steven Weston
 */
public abstract class SetFunctionFactory<T extends Nameable, F extends SetFunction<T>> extends FunctionFactory<T, Set<T>, F> {

	public SetFunctionFactory(List<ValidatorAndConstructor<F>> constructors) {
		super(constructors);
	}
}
