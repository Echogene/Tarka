package logic.function.reflexive;

import logic.Nameable;
import logic.function.factory.FunctionFactory;
import logic.function.factory.construction.ValidatorAndConstructor;

import java.util.List;

/**
 * @author Steven Weston
 */
public abstract class ReflexiveFunctionFactory<T extends Nameable, F extends ReflexiveFunction<T>>
		extends FunctionFactory<T, T, F> {

	public ReflexiveFunctionFactory(List<ValidatorAndConstructor<F>> constructors) {
		super(constructors);
	}
}
