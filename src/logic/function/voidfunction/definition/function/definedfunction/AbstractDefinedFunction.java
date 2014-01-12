package logic.function.voidfunction.definition.function.definedfunction;

import logic.Nameable;
import logic.function.Function;
import logic.model.Model;

import java.util.Map;

/**
 * @author Steven Weston
 */
public abstract class AbstractDefinedFunction<D extends Nameable, C> implements Function<D, C> {

	private String functionSymbol;
	private final Map<String, Function<D, ?>> parameters;
	private final Function<D, C> definition;

	public AbstractDefinedFunction(String functionSymbol, Function<D, C> definition, Map<String, Function<D, ?>> parameters) {
		this.functionSymbol = functionSymbol;
		this.definition = definition;
		// todo: ensure the parameters are in the correct order
		this.parameters = parameters;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		builder.append(functionSymbol);
		for (Function<D, ?> function : parameters.values()) {
			builder.append(" ");
			builder.append(function.toString());
		}
		builder.append(")");
		return builder.toString();
	}
}
