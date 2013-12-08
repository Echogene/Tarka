package logic.model;

import logic.Nameable;
import logic.factory.SimpleLogicReader;
import logic.function.Function;
import logic.model.universe.Universe;
import reading.reading.Reader;

/**
 * @author Steven Weston
 */
public abstract class AbstractModel<T extends Nameable> implements Model<T> {

	private final Universe<T> universe;

	private final SimpleLogicReader<T> reader;

	protected AbstractModel(Universe<T> universe, SimpleLogicReader<T> reader) {
		this.universe = universe;
		this.reader = reader;
	}

	@Override
	public Universe<T> getUniverse() {
		return universe;
	}

	public Reader<Function<T, ?>> getReader() {
		return reader;
	}
}
