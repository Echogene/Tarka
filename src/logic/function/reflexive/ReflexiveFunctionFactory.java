package logic.function.reflexive;

import logic.Nameable;
import logic.function.Function;
import logic.function.factory.FunctionFactory;
import logic.function.factory.construction.ValidatorAndConstructor;

import java.util.List;

/**
 * @author Steven Weston
 */
public abstract class ReflexiveFunctionFactory<T extends Nameable> extends FunctionFactory<T, T> {
	public ReflexiveFunctionFactory(List<ValidatorAndConstructor<Function<T, T>>> constructors) {
		super(constructors);
	}
}
