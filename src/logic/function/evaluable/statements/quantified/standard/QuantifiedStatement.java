package logic.function.evaluable.statements.quantified.standard;

import logic.Nameable;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.function.evaluable.statements.quantified.AbstractQuantifiedStatement;
import logic.model.Model;

import java.util.Map;

/**
 * @author Steven Weston
 */
public class QuantifiedStatement<T extends Nameable> extends AbstractQuantifiedStatement<T, QuantifiedStatement<T>> {

	public QuantifiedStatement(Quantifier quantifier, String variableSymbol, Evaluable<T, ?> evaluable) {
		super(quantifier, variableSymbol, evaluable);
	}

	@Override
	public Boolean evaluate(Model<T, ?, ?> model) throws Exception {
		return quantifier.apply(variableSymbol, evaluable, model);
	}

	@Override
	public void reduce(Map<String, Function<T, ?, ?>> reductions) {
		evaluable.reduce(reductions);
	}

	@Override
	public String toString() {
		return "(" + quantifier.toString() + variableSymbol + " " + evaluable.toString()  + ")";
	}

	@Override
	public QuantifiedStatement<T> copy() {
		return new QuantifiedStatement<>(quantifier, variableSymbol, evaluable.copy());
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof QuantifiedStatement<?> && super.equals(o);
	}
}
