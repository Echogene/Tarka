package logic.evaluable.statements.quantified;

import logic.Nameable;
import logic.evaluable.Evaluable;
import logic.function.ParameterNotFoundException;
import logic.model.universe.Universe;
import logic.set.Set;

/**
 * @author Steven Weston
 */
public class QuantifiedStatement<T extends Nameable> implements Evaluable<T> {
	protected Quantifier quantifier;
	protected String variableSymbol;
	protected Evaluable<T> evaluable;

	public QuantifiedStatement(Quantifier quantifier, String variableSymbol, Evaluable<T> evaluable) {
		this.quantifier     = quantifier;
		this.variableSymbol = variableSymbol;
		this.evaluable      = evaluable;
	}

	@Override
	public Boolean evaluate(Set<? extends T> variables) throws ParameterNotFoundException {
		throw new ParameterNotFoundException("Quantified statement can only be evaluated in a universe");
	}

	@Override
	public Boolean evaluate(Universe<T> universe) throws Exception {
		return quantifier.apply(variableSymbol, evaluable, universe);
	}

	@Override
	public String toString() {
		return "(" + quantifier.toString() + variableSymbol + " " + evaluable.toString()  + ")";
	}

	@Override
	public String getName() {
		return toString();
	}

	public Quantifier getQuantifier() {
		return quantifier;
	}

	public String getVariableSymbol() {
		return variableSymbol;
	}

	public Evaluable<T> getEvaluable() {
		return evaluable;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof QuantifiedStatement<?>)) {
			return false;
		}
		QuantifiedStatement<?> other = (QuantifiedStatement<?>) o;
		return getQuantifier().equals(other.getQuantifier())
				&& getVariableSymbol().equals(other.getVariableSymbol())
				&& getEvaluable().equals(other.getEvaluable());
	}
}
