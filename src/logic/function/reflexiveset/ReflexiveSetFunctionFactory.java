package logic.function.reflexiveset;

import logic.Nameable;
import logic.function.factory.FunctionFactory;
import logic.set.Set;

/**
 * @author Steven Weston
 */
public interface ReflexiveSetFunctionFactory<T extends Nameable> extends FunctionFactory<T, Set<T>> {
}
