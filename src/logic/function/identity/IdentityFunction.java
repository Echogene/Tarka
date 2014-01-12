package logic.function.identity;

import logic.Nameable;
import logic.function.Function;
import logic.model.Model;

import java.util.Map;

/**
 * The basic function from which all other functions are built.  This wraps another function or maps to an element of
 * the model's universe.
 * @author Steven Weston
 */
class IdentityFunction<D extends Nameable, C> implements Function<D, C> {

	final String parameter;
	Function<D, C> function;

	IdentityFunction(String parameter) {
		this(parameter, null);
	}

	IdentityFunction(Function<D, C> function) {
		this(null, function);
	}

	IdentityFunction(String parameter, Function<D, C> function) {
		this.parameter = parameter;
		this.function  = function;
	}

	@Override
	public C evaluate(Model<D, ?, ?> model) throws Exception {
		if (function != null) {
			return function.evaluate(model);
		}
		return (C) model.getUniverse().get(parameter);

	}

	@Override
	public void reduce(Map<String, Function<D, ?>> reductions) {
		if (parameter != null && reductions.containsKey(parameter)) {
			function = (Function<D, C>) reductions.get(parameter);
		}
	}

	@Override
	public IdentityFunction<D, C> copy() {
		return new IdentityFunction<>(parameter, function.copy());
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
