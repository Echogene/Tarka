package logic.identity;

import logic.Nameable;
import logic.function.Function;
import logic.function.ParameterNotFoundException;
import logic.model.universe.Universe;
import logic.set.Dictionary;

/**
 * @author Steven Weston
 */
class IdentityFunction<D extends Nameable, C> implements Function<D, C> {

	public static final String IDENTITY_NAME = "id";
	protected String parameter;
	protected Function<D, C> function;

	public IdentityFunction(String parameter) {
		this.parameter = parameter;
		this.function  = null;
	}

	public IdentityFunction(Function<D, C> function) {
		this.parameter = null;
		this.function  = function;
	}

	@Override
	public C evaluate(Universe<D> universe) throws Exception {
		if (function != null) {
			return function.evaluate(universe);
		}
		Dictionary<Object> variables = universe.getVariables();
		Dictionary<D> universalSet = universe.getUniversalSet();
		boolean variablesContainsParameter    = variables    != null && variables.contains(getParameter());
		boolean universalSetContainsParameter = universalSet != null && universalSet.contains(getParameter());
		if (!variablesContainsParameter && !universalSetContainsParameter) {
			throw new ParameterNotFoundException("Identity function could not find the parameter " + getParameter());
		}
		if (variablesContainsParameter) {
			return (C) variables.get(getParameter()); //todo
		} else {
			return (C) universalSet.get(getParameter());
		}

	}

	public String getParameter() {
		return parameter;
	}

	@Override
	public String toString() {
		return function == null ? getParameter() : function.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof IdentityFunction<?, ?>)) {
			return false;
		}
		IdentityFunction<?, ?> other = (IdentityFunction<?, ?>) o;
		boolean areParametersBothNull = getParameter() == null && other.getParameter() == null;
		boolean areParametersEqual = areParametersBothNull
				|| (getParameter() != null && getParameter().equals(other.getParameter()));
		boolean areFunctionsBothNull = function == null && other.function == null;
		boolean areFunctionsEqual = areFunctionsBothNull
				|| (function != null && function.equals(other.function));
		return areParametersEqual && areFunctionsEqual;
	}

	@Override
	public int hashCode() {
		return function == null ? parameter.hashCode() : function.hashCode();
	}
}
