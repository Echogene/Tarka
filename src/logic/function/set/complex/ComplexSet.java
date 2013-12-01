package logic.function.set.complex;

import logic.Nameable;
import logic.function.evaluable.Evaluable;
import logic.function.set.SetFunction;
import logic.model.universe.Universe;
import logic.set.Set;
import logic.set.filtered.FiniteFilteredSet;
import logic.set.filtered.UndeterminableFilteredSet;
import logic.set.finite.Filter;
import logic.set.finite.FiniteSet;

import java.text.MessageFormat;

/**
 * @author Steven Weston
 */
public class ComplexSet<T extends Nameable> implements SetFunction<T> {

	public static final String SUCH_THAT = "|";
	private final String variable;
	private final SetFunction<T> boundingSet;
	private final Evaluable<T> evaluable;

	public ComplexSet(String variable, SetFunction<T> boundingSet, Evaluable<T> evaluable) {
		this.variable = variable;
		this.boundingSet = boundingSet;
		this.evaluable = evaluable;
	}

	@Override
	public Set<T> evaluate(Universe<T> universe) throws Exception {
		Filter<T> filter = t -> {
			universe.assignVariable(variable);
			universe.setVariable(variable, t);
			Boolean output = evaluable.evaluate(universe);
			universe.unassignVariable(variable);
			return output;
		};
		Set<T> set = boundingSet.evaluate(universe);
		if (set instanceof FiniteSet) {
			return new FiniteFilteredSet<>(toString(), (FiniteSet<T>) set, filter);
		} else {
			return new UndeterminableFilteredSet<>(toString(), set, filter);
		}
	}

	@Override
	public String toString() {
		return MessageFormat.format("'{'{0} ∊ {1} | {2}'}'", variable, boundingSet, evaluable);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ComplexSet that = (ComplexSet) o;

		if (boundingSet != null ? !boundingSet.equals(that.boundingSet) : that.boundingSet != null) return false;
		if (evaluable != null ? !evaluable.equals(that.evaluable) : that.evaluable != null) return false;
		if (variable != null ? !variable.equals(that.variable) : that.variable != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = variable != null ? variable.hashCode() : 0;
		result = 31 * result + (boundingSet != null ? boundingSet.hashCode() : 0);
		result = 31 * result + (evaluable != null ? evaluable.hashCode() : 0);
		return result;
	}
}
