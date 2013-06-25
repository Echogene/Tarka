package logic.evaluable.statements.unary;

import logic.Nameable;
import logic.evaluable.Evaluable;
import logic.function.ParameterNotFoundException;
import logic.model.universe.Universe;
import logic.set.Set;

/**
 * @author Steven Weston
 */
public class UnaryStatement<T extends Nameable> implements Evaluable<T> {
	protected UnaryConnective connective;
	protected Evaluable<T> evaluable;

	public UnaryStatement(UnaryConnective connective, Evaluable<T> evaluable) {
		this.connective = connective;
		this.evaluable  = evaluable;
	}

	public UnaryStatement(Evaluable<T> evaluable) {
		this.connective = new UnaryConnective(UnaryConnective.UnaryConnectiveType.EMPTY);
		this.evaluable  = evaluable;
	}

	@Override
	public Boolean evaluate(Set<? extends T> variables) throws ParameterNotFoundException {
		return connective.apply(evaluable.evaluate(variables));
	}

	@Override
	public Boolean evaluate(Universe<T> universe) throws Exception {
		return connective.apply(evaluable.evaluate(universe));
	}

	@Override
	public String toString() {
		return "(" + connective.toString() + evaluable.toString() + ")";
	}

	@Override
	public String getName() {
		return toString();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof UnaryStatement<?>)) {
			return false;
		}
		UnaryStatement<?> other = (UnaryStatement<?>) o;
		return connective.equals(other.connective) && evaluable.equals(other.evaluable);
	}
}
