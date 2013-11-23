package logic.function.voidfunction.definition.member;

import logic.Nameable;
import logic.function.Function;
import logic.function.voidfunction.VoidFunction;
import logic.model.universe.Universe;

/**
 * @author Steven Weston
 */
class Definition<D extends Nameable, C> implements VoidFunction<D> {

	private String variableName;
	private Function<D, C> definition;

	public static final String DEFINITION_SYMBOL = "≔";

	protected Definition(String variableName, Function<D, C> definition) {
		this.variableName = variableName;
		this.definition   = definition;
	}

	@Override
	public Void evaluate(Universe<D> universe) throws Exception {
		universe.assignVariable(variableName);
		universe.setVariable(variableName, definition.evaluate(universe));
		return null;
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
