package logic.function.voidfunction.definition.member;

import logic.Nameable;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.voidfunction.VoidFunction;
import logic.model.universe.Universe;

/**
 * @author Steven Weston
 */
public class MemberDefinition<T extends Nameable> implements VoidFunction<T> {

	private String variableName;
	private ReflexiveFunction<T> definition;

	public static final String DEFINITION_SYMBOL = "≔";

	public MemberDefinition(String variableName, ReflexiveFunction<T> definition) {
		this.variableName = variableName;
		this.definition   = definition;
	}

	@Override
	public Void evaluate(Universe<T> universe) throws Exception {
		universe.assignVariable(variableName);
		universe.setVariable(variableName, definition.evaluate(universe));
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof MemberDefinition)) {
			return false;
		}
		MemberDefinition other = (MemberDefinition) o;
		return variableName.equals(other.variableName)
				&& definition.equals(other.definition);
	}
}
