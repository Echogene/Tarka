package logic.evaluable.statements.quantified.restricted;

import logic.Nameable;
import logic.evaluable.Evaluable;
import logic.evaluable.statements.quantified.standard.QuantifiedStatement;
import logic.evaluable.statements.quantified.standard.Quantifier;
import logic.function.reflexiveset.ReflexiveSetFunction;
import logic.model.universe.Universe;
import logic.set.Set;

/**
 * @author Steven Weston
 */
public class RestrictedQuantifiedStatement<T extends Nameable> extends QuantifiedStatement<T> {

	private ReflexiveSetFunction<T> setFunction;

	public RestrictedQuantifiedStatement(
			Quantifier quantifier,
			String variableSymbol,
			ReflexiveSetFunction<T> setFunction,
			Evaluable<T> evaluable
	) {
		super(quantifier, variableSymbol, evaluable);
		this.setFunction = setFunction;
	}

	@Override
	public Boolean evaluate(Universe<T> universe) throws Exception {
		Set<T> restrictedSet = setFunction.evaluate(universe);
		return getQuantifier().apply(
				getVariableSymbol(),
				getEvaluable(),
				universe,
				restrictedSet
		);
	}
}
