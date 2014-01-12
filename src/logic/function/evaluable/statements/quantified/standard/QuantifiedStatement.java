package logic.function.evaluable.statements.quantified.standard;

import logic.Nameable;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.model.Model;

import java.util.Map;

/**
 * @author Steven Weston
 */
public class QuantifiedStatement<T extends Nameable> implements Evaluable<T> {

	private final Quantifier quantifier;
	private final String variableSymbol;
	private final Evaluable<T> evaluable;

	public QuantifiedStatement(Quantifier quantifier, String variableSymbol, Evaluable<T> evaluable) {
		this.quantifier = quantifier;
		this.variableSymbol = variableSymbol;
		this.evaluable = evaluable;
	}

	@Override
	public Boolean evaluate(Model<T, ?, ?> model) throws Exception {
		return quantifier.apply(variableSymbol, evaluable, model);
	}

	@Override
	public void reduce(Map<String, Function<T, ?>> reductions) {
		evaluable.reduce(reductions);
	}

	@Override
	public String toString() {
		return "(" + quantifier.toString() + variableSymbol + " " + evaluable.toString()  + ")";
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

	@Override
	public QuantifiedStatement<T> copy() {
		return new QuantifiedStatement<>(quantifier, variableSymbol, evaluable.copy());
	}
}
