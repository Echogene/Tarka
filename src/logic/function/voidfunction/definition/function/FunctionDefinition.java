package logic.function.voidfunction.definition.function;

import logic.Nameable;
import logic.function.Function;
import logic.function.voidfunction.VoidFunction;
import logic.model.Model;

import java.util.List;

/**
 * @author Steven Weston
 */
public class FunctionDefinition<D extends Nameable, C> implements VoidFunction<D> {

	private final String functionName;
	private final List<String> parameters;
	private final Function<D, C> definition;

	public FunctionDefinition(String functionName, List<String> parameters, Function<D, C> definition) {
		this.functionName = functionName;
		this.parameters = parameters;
		this.definition = definition;
	}

	@Override
	public Void evaluate(Model<D, ?, ?> model) throws Exception {
		model.addFactory(
				new DefinedFunctionFactory<>(
						model.getUniverse().getTypeOfUniverse(),
						definition
				)
		);
		return null;
	}
}
