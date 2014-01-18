package logic.function.evaluable.statements.quantified.restricted;

import logic.Nameable;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.function.evaluable.predicate.membership.MembershipPredicate;
import logic.function.evaluable.statements.quantified.AbstractQuantifiedStatement;
import logic.function.evaluable.statements.quantified.standard.Quantifier;
import logic.function.set.SetFunction;
import logic.model.Model;
import logic.set.Set;

import java.util.Map;

/**
 * @author Steven Weston
 */
public class RestrictedQuantifiedStatement<T extends Nameable>
		extends AbstractQuantifiedStatement<T, RestrictedQuantifiedStatement<T>> {

	private final SetFunction<T, ?> setFunction;

	public RestrictedQuantifiedStatement(
			Quantifier quantifier,
			String variableSymbol,
			SetFunction<T, ?> setFunction,
			Evaluable<T, ?> evaluable
	) {
		super(quantifier, variableSymbol, evaluable);
		this.setFunction = setFunction;
	}

	@Override
	public Boolean evaluate(Model<T, ?, ?> model) throws Exception {
		Set<T> restrictedSet = setFunction.evaluate(model);
		return getQuantifier().apply(
				getVariableSymbol(),
				getEvaluable(),
				model,
				restrictedSet
		);
	}

	@Override
	public void reduce(Map<String, Function<T, ?, ?>> reductions) {
		setFunction.reduce(reductions);
		evaluable.reduce(reductions);
	}

	@Override
	public RestrictedQuantifiedStatement<T> copy() {
		return new RestrictedQuantifiedStatement<>(quantifier, variableSymbol, setFunction.copy(), evaluable.copy());
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
