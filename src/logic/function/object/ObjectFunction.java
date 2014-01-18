package logic.function.object;

import logic.Nameable;
import logic.function.Function;
import logic.model.Model;

import java.util.Map;

/**
 * @author Steven Weston
 */
public class ObjectFunction<D extends Nameable, C> implements Function<D, C, ObjectFunction<D, C>> {

	private final C object;

	public ObjectFunction(C object) {
		this.object = object;
	}

	@Override
	public C evaluate(Model<D, ?, ?> model) throws Exception {
		return object;
	}

	@Override
	public void reduce(Map<String, Function<D, ?, ?>> reductions) {
		// Do nothing
	}

	@Override
	public ObjectFunction<D, C> copy() {
		return new ObjectFunction<>(object);
	}
}
