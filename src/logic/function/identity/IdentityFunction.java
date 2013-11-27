package logic.function.identity;

import logic.Nameable;
import logic.function.Function;
import logic.model.universe.Universe;

/**
 * @author Steven Weston
 */
class IdentityFunction<D extends Nameable, C> implements Function<D, C> {

	private String parameter;
	private Function<D, C> function;

	IdentityFunction(String parameter) {
		this.parameter = parameter;
		this.function  = null;
	}

	IdentityFunction(Function<D, C> function) {
		this.parameter = null;
		this.function  = function;
	}

	@Override
	public C evaluate(Universe<D> universe) throws Exception {
		if (function != null) {
			return function.evaluate(universe);
		}
		return (C) universe.get(parameter);

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
		boolean areClassesEqual = o.getClass().isInstance(this) || getClass().isInstance(o);
		return areParametersEqual && areFunctionsEqual && areClassesEqual;
	}

	@Override
	public int hashCode() {
		return function == null ? parameter.hashCode() : function.hashCode();
	}
}
