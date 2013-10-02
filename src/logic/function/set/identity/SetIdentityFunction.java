package logic.function.set.identity;

import logic.Nameable;
import logic.function.ParameterNotFoundException;
import logic.function.set.SetFunction;
import logic.model.universe.Universe;
import logic.set.Dictionary;
import logic.set.Set;

/**
 * @author Steven Weston
 */
public class SetIdentityFunction<T extends Nameable> implements SetFunction<T> {
	public static final String SET_IDENTITY_NAME = "Id";
	protected String parameter;
	protected SetFunction<T> function;

	public SetIdentityFunction(String parameter) {
		this.parameter = parameter;
		this.function  = null;
	}

	public SetIdentityFunction(SetFunction<T> function) {
		this.parameter = null;
		this.function  = function;
	}

	@Override
	public Set<T> evaluate(Universe<T> universe) throws Exception {
		Dictionary<Set<T>> universalSetOfSets = universe.getUniversalSetOfSets();
		if (universalSetOfSets == null || !universalSetOfSets.contains(getParameter())) {
			throw new ParameterNotFoundException("The parameter \"" + getParameter() + "\" was not found");
		}
		return getFunction() == null ? universalSetOfSets.get(getParameter()) : getFunction().evaluate(universe);
	}

	public String getParameter() {
		return parameter;
	}

	public SetFunction<T> getFunction() {
		return function;
	}

	@Override
	public String toString() {
		return function == null ? getParameter() : function.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof SetIdentityFunction<?>)) {
			return false;
		}
		SetIdentityFunction<?> other = (SetIdentityFunction<?>) o;
		boolean areParametersBothNull = getParameter() == null && other.getParameter() == null;
		boolean areParametersEqual = areParametersBothNull
				|| (getParameter() != null && getParameter().equals(other.getParameter()));
		boolean areFunctionsBothNull = getFunction() == null && other.getFunction() == null;
		boolean areFunctionsEqual = areFunctionsBothNull
				|| (getFunction() != null && getFunction().equals(other.getFunction()));
		return areParametersEqual && areFunctionsEqual;
	}

	@Override
	public int hashCode() {
		if (parameter != null) {
			return parameter.hashCode();
		} else {
			return function.hashCode();
		}
	}
}
