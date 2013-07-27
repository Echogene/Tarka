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
	public String getName() {
		return "(" + variableName + " := " + definition.toString() + ")";
	}
}