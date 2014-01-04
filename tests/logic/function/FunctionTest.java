package logic.function;

import logic.Nameable;
import logic.model.Model;
import logic.model.universe.Universe;

/**
 * @author Steven Weston
 */
public abstract class FunctionTest<T extends Nameable, U extends Universe<T, ?, ?>, M extends Model<T, U, ?>, F extends Function<T, ?>> {

	protected final M model;
	protected final U universe;
	protected F function;

	public FunctionTest(M model) {
		this.model = model;
		this.universe = model.getUniverse();
	}
}
