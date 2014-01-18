package logic.function.evaluable.statements.quantified;

import logic.Nameable;
import logic.function.evaluable.Evaluable;
import logic.function.evaluable.statements.quantified.standard.Quantifier;

/**
 * @author Steven Weston
 */
public abstract class AbstractQuantifiedStatement<T extends Nameable, F extends AbstractQuantifiedStatement<T, F>>
		implements Evaluable<T, F> {

	protected final Quantifier quantifier;
	protected final String variableSymbol;
	protected final Evaluable<T, ?> evaluable;

	public AbstractQuantifiedStatement(Quantifier quantifier, String variableSymbol, Evaluable<T, ?> evaluable) {
		this.quantifier = quantifier;
		this.variableSymbol = variableSymbol;
		this.evaluable = evaluable;
	}

	public Quantifier getQuantifier() {
		return quantifier;
	}

	public String getVariableSymbol() {
		return variableSymbol;
	}

	public Evaluable<T, ?> getEvaluable() {
		return evaluable;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof AbstractQuantifiedStatement<?, ?>)) {
			return false;
		}
		AbstractQuantifiedStatement<?, ?> other = (AbstractQuantifiedStatement<?, ?>) o;
		return getQuantifier().equals(other.getQuantifier())
				&& getVariableSymbol().equals(other.getVariableSymbol())
				&& getEvaluable().equals(other.getEvaluable());
	}
}
