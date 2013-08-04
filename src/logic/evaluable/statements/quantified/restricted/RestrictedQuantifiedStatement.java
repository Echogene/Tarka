package logic.evaluable.statements.quantified.restricted;

import logic.Nameable;
import logic.evaluable.Evaluable;
import logic.evaluable.predicate.membership.MembershipPredicate;
import logic.evaluable.statements.quantified.standard.QuantifiedStatement;
import logic.evaluable.statements.quantified.standard.Quantifier;
import logic.function.set.SetFunction;
import logic.model.universe.Universe;
import logic.set.Set;

/**
 * @author Steven Weston
 */
public class RestrictedQuantifiedStatement<T extends Nameable> extends QuantifiedStatement<T> {

	private SetFunction<T> setFunction;

	public RestrictedQuantifiedStatement(
			Quantifier quantifier,
			String variableSymbol,
			SetFunction<T> setFunction,
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

	@Override
	public String toString() {
		return "("
				+ getQuantifier().toString()
				+ getVariableSymbol()
				+ " "
				+ MembershipPredicate.MEMBERSHIP_SYMBOL
				+ " "
				+ setFunction.toString()
				+ " "
				+ getEvaluable().toString()
				+ ")";
	}

	@Override
	public boolean equals(Object o) {
		if (!(super.equals(o) && o instanceof RestrictedQuantifiedStatement)) {
			return false;
		}
		RestrictedQuantifiedStatement other = (RestrictedQuantifiedStatement) o;
		return this.setFunction.equals(other.setFunction);
	}
}
