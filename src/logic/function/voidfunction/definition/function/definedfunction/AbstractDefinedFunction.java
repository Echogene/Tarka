package logic.function.voidfunction.definition.function.definedfunction;

import logic.Nameable;
import logic.function.Function;
import logic.model.Model;

import java.util.Map;

/**
 * @author Steven Weston
 */
public abstract class AbstractDefinedFunction<D extends Nameable, C> implements Function<D, C> {

	private final Map<String, Function<D, ?>> parameters;
	private final Function<D, C> definition;

	public AbstractDefinedFunction(Function<D, C> definition, Map<String, Function<D, ?>> parameters) {
		this.parameters = parameters;
		this.definition = definition;
	}

	@Override
	public C evaluate(Model<D, ?, ?> model) throws Exception {
		definition.reduce(parameters);
		return definition.evaluate(model);
	}

	@Override
	public void reduce(Map<String, Function<D, ?>> reductions) {
		definition.reduce(reductions);
	}
}
