package logic.function.voidfunction.definition.function.definedfunction;

import logic.Nameable;
import logic.function.Function;
import logic.model.Model;

import java.util.Map;

/**
 * @author Steven Weston
 */
abstract class AbstractDefinedFunction<D extends Nameable, C> implements Function<D, C> {

	private final Map<String, Function<D, ?>> parameters;
	private final Function<D, C> definition;

	public AbstractDefinedFunction(Function<D, C> definition, Map<String, Function<D, ?>> parameters) {
		this.parameters = parameters;
		this.definition = definition;
	}

	@Override
	public C evaluate(Model<D, ?, ?> model) throws Exception {
		for (Map.Entry<String, Function<D, ?>> entry : parameters.entrySet()) {
			model.assignVariable(entry.getKey());
			model.setVariable(entry.getKey(), entry.getValue().evaluate(model));
		}
		C output = definition.evaluate(model);
		for (String variable : parameters.keySet()) {
			model.unassignVariable(variable);
		}
		return output;
	}
}
