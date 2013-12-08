package logic.model;

import logic.Nameable;
import logic.function.Function;
import logic.model.universe.Universe;
import reading.reading.Reader;

/**
 * @author Steven Weston
 */
public interface Model<T extends Nameable> {

	public Universe<T> getUniverse();

	public Reader<Function<T, ?>> getReader();
}
