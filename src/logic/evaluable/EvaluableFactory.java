package logic.evaluable;

import logic.Nameable;
import logic.function.factory.FunctionFactory;

/**
 * @author Steven Weston
 */
public abstract class EvaluableFactory<T extends Nameable> extends FunctionFactory<T, Boolean> {
}
