package logic.evaluable.statements.binary;

import logic.Nameable;
import logic.evaluable.Evaluable;
import logic.model.universe.Universe;

/**
 * @author Steven Weston
 */
public class BinaryStatement<T extends Nameable> implements Evaluable<T> {
	protected Evaluable<T> firstEvaluable;
	protected BinaryConnective connective;
	protected Evaluable<T> secondEvaluable;

	public BinaryStatement(Evaluable<T> firstEvaluable, BinaryConnective connective, Evaluable<T> secondEvaluable) {
		this.firstEvaluable  = firstEvaluable;
		this.connective      = connective;
		this.secondEvaluable = secondEvaluable;
	}

	@Override
	public Boolean evaluate(Universe<T> universe) throws Exception {
		return connective.apply(firstEvaluable.evaluate(universe), secondEvaluable.evaluate(universe));
	}

	@Override
	public String toString() {
		return "(" + firstEvaluable.toString() + " " + connective.toString() + " " + secondEvaluable.toString() + ")";
	}

	public Evaluable<T> getFirstEvaluable() {
		return firstEvaluable;
	}

	public BinaryConnective getConnective() {
		return connective;
	}

	public Evaluable<T> getSecondEvaluable() {
		return secondEvaluable;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof BinaryStatement<?>)) {
			return false;
		}
		BinaryStatement<?> other = (BinaryStatement<?>) o;
		return getFirstEvaluable().equals(other.getFirstEvaluable())
				&& getConnective().equals(other.getConnective())
				&& getSecondEvaluable().equals(other.getSecondEvaluable());
	}
}
