package logic.function;

import logic.Nameable;
import logic.model.universe.Universe;

/**
 * @author Steven Weston
 */
public abstract class FunctionTest<T extends Nameable, U extends Universe<T>, F extends Function<T, ?>> {

	protected final U universe;
	protected F function;

	public FunctionTest(U universe) {
		this.universe = universe;
	}
}
