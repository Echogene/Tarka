package logic.function.set;

import logic.Nameable;
import logic.function.factory.FunctionFactory;
import logic.set.Set;

/**
 * @author Steven Weston
 */
public abstract class SetFunctionFactory<T extends Nameable> extends FunctionFactory<T, Set<T>> {
}
