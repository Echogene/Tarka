package logic.function.voidfunction.definition.constant;

import logic.Nameable;
import logic.function.Function;
import logic.function.voidfunction.VoidFunction;
import logic.model.Model;

import java.util.Map;

/**
 * @author Steven Weston
 */
class Definition<D extends Nameable, C> implements VoidFunction<D> {

	final String variableName;
	final Function<D, C> definition;

	public static final String DEFINITION_SYMBOL = "≔";

	Definition(String variableName, Function<D, C> definition) {
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
	public void reduce(Map<String, Function<D, ?>> reductions) {
		definition.reduce(reductions);
	}

	@Override
	public Definition<D, C> copy() {
		return new Definition<>(variableName, definition.copy());
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Definition)) {
			return false;
		}
		Definition other = (Definition) o;
		return variableName.equals(other.variableName)
				&& definition.equals(other.definition);
	}
}
