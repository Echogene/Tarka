package logic.function.reflexiveset.identity;

import logic.Nameable;
import logic.function.ParameterNotFoundException;
import logic.function.reflexiveset.AbstractReflexiveSetFunction;
import logic.function.reflexiveset.ReflexiveSetFunction;
import logic.set.Set;

/**
 * @author Steven Weston
 */
public class SetIdentityFunction<T extends Nameable> extends AbstractReflexiveSetFunction<T> {
	public static final String SET_IDENTITY_NAME = "Id";
	protected String parameter;
	protected ReflexiveSetFunction<T> function;

	public SetIdentityFunction(String parameter) {
		this.parameter = parameter;
		this.function  = null;
	}

	public SetIdentityFunction(ReflexiveSetFunction<T> function) {
		this.parameter = null;
		this.function  = function;
	}

	@Override
	public Set<T> evaluate(Set<? extends Set<T>> variables) throws ParameterNotFoundException {
		if (variables == null || !variables.contains(getParameter())) {
			throw new ParameterNotFoundException();
		}
		return getFunction() == null ? variables.get(getParameter()) : getFunction().evaluate(variables);
	}

	@Override
	public String getName() {
		return SET_IDENTITY_NAME;
	}

	public String getParameter() {
		return parameter;
	}

	public ReflexiveSetFunction<T> getFunction() {
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
