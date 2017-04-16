package logic.function.voidfunction.definition.constant;

import logic.Nameable;
import logic.function.Function;
import logic.function.voidfunction.VoidFunction;
import logic.model.Model;

import java.util.Map;

/**
 * @author Steven Weston
 */
abstract class AbstractDefinition<D extends Nameable, C, F extends AbstractDefinition<D, C, F, G>, G extends Function<D, C, ?>>
		implements VoidFunction<D, F> {

	final String variableName;
	final G definition;

	public static final String DEFINITION_SYMBOL = "≔";

	AbstractDefinition(String variableName, G definition) {
		this.variableName = variableName;
		this.definition   = definition;
	}

	@Override
	public Void evaluate(Model<D, ?, ?> model) throws Exception {
		model.assignVariable(variableName);
		model.setVariable(variableName, definition.evaluate(model));
		return null;
	}

	@Override
	public void reduce(Map<String, Function<D, ?, ?>> reductions) {
		definition.reduce(reductions);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof AbstractDefinition)) {
			return false;
		}
		AbstractDefinition other = (AbstractDefinition) o;
		return variableName.equals(other.variableName)
				&& definition.equals(other.definition);
	}
}
