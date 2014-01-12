package logic.function.voidfunction;

import logic.Nameable;
import logic.function.Function;

/**
 * @author Steven Weston
 */
public interface VoidFunction<T extends Nameable> extends Function<T, Void> {

	@Override
	VoidFunction<T> copy();
}
