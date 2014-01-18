package logic.function.evaluable.statements.binary;

import logic.Nameable;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.model.Model;

import java.util.Map;

/**
 * @author Steven Weston
 */
public class BinaryStatement<T extends Nameable> implements Evaluable<T, BinaryStatement<T>> {

	private final Evaluable<T, ?> firstEvaluable;
	private final BinaryConnective connective;
	private final Evaluable<T, ?> secondEvaluable;

	public BinaryStatement(Evaluable<T, ?> firstEvaluable, BinaryConnective connective, Evaluable<T, ?> secondEvaluable) {
		this.firstEvaluable  = firstEvaluable;
		this.connective      = connective;
		this.secondEvaluable = secondEvaluable;
	}

	@Override
	public Boolean evaluate(Model<T, ?, ?> model) throws Exception {
		return connective.apply(firstEvaluable.evaluate(model), secondEvaluable.evaluate(model));
	}

	@Override
	public void reduce(Map<String, Function<T, ?, ?>> reductions) {
		firstEvaluable.reduce(reductions);
		secondEvaluable.reduce(reductions);
	}

	@Override
	public String toString() {
		return "(" + firstEvaluable.toString() + " " + connective.toString() + " " + secondEvaluable.toString() + ")";
	}

	public Evaluable<T, ?> getFirstEvaluable() {
		return firstEvaluable;
	}

	public BinaryConnective getConnective() {
		return connective;
	}

	public Evaluable<T, ?> getSecondEvaluable() {
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

	@Override
	public BinaryStatement<T> copy() {
		return new BinaryStatement<>(firstEvaluable.copy(), connective, secondEvaluable.copy());
	}
}
