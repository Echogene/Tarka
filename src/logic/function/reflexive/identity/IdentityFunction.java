package logic.function.reflexive.identity;

import logic.Nameable;
import logic.function.ParameterNotFoundException;
import logic.function.reflexive.ReflexiveFunction;
import logic.model.universe.Universe;
import logic.set.Set;

/**
 * @author Steven Weston
 */
public class IdentityFunction<T extends Nameable> implements ReflexiveFunction<T> {
	public static final String IDENTITY_NAME = "id";
	protected String parameter;
	protected ReflexiveFunction<T> function;

	public IdentityFunction(String parameter) {
		this.parameter = parameter;
		this.function  = null;
	}

	public IdentityFunction(ReflexiveFunction<T> function) {
		this.parameter = null;
		this.function  = function;
	}

	@Override
	public T evaluate(Universe<T> universe) throws Exception {
		if (function != null) {
			return function.evaluate(universe);
		}
		Set<T> variables = universe.getVariables();
		Set<T> universalSet = universe.getUniversalSet();
		boolean variablesContainsParameter    = variables    != null && variables.contains(getParameter());
		boolean universalSetContainsParameter = universalSet != null && universalSet.contains(getParameter());
		if (!variablesContainsParameter && !universalSetContainsParameter) {
			throw new ParameterNotFoundException("Identity function could not find its parameter");
		}
		T t;
		if (variablesContainsParameter) {
			return variables.get(getParameter());
		} else {
			return universalSet.get(getParameter());
		}

	}

	@Override
	public String getName() {
		return IDENTITY_NAME;
	}

	public String getParameter() {
		return parameter;
	}

	public ReflexiveFunction<T> getFunction() {
		return function;
	}

	@Override
	public String toString() {
		return function == null ? getParameter() : function.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof IdentityFunction<?>)) {
			return false;
		}
		IdentityFunction<?> other = (IdentityFunction<?>) o;
		boolean areParametersBothNull = getParameter() == null && other.getParameter() == null;
		boolean areParametersEqual = areParametersBothNull
				|| (getParameter() != null && getParameter().equals(other.getParameter()));
		boolean areFunctionsBothNull = getFunction() == null && other.getFunction() == null;
		boolean areFunctionsEqual = areFunctionsBothNull
				|| (getFunction() != null && getFunction().equals(other.getFunction()));
		return areParametersEqual && areFunctionsEqual;
	}
}