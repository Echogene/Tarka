package logic.evaluable.statements.quantified.standard;

import logic.Nameable;
import logic.evaluable.Evaluable;
import logic.model.universe.Universe;

/**
 * @author Steven Weston
 */
public class QuantifiedStatement<T extends Nameable> implements Evaluable<T> {

	private Quantifier quantifier;
	private String variableSymbol;
	private Evaluable<T> evaluable;

	public QuantifiedStatement(Quantifier quantifier, String variableSymbol, Evaluable<T> evaluable) {
		this.quantifier = quantifier;
		this.variableSymbol = variableSymbol;
		this.evaluable = evaluable;
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
