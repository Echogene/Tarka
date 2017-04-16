package logic.function.evaluable.statements.unary;

import logic.Nameable;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.model.Model;

import java.util.Map;

/**
 * @author Steven Weston
 */
public class UnaryStatement<T extends Nameable> implements Evaluable<T, UnaryStatement<T>> {

	private final UnaryConnective connective;
	private final Evaluable<T, ?> evaluable;

	public UnaryStatement(UnaryConnective connective, Evaluable<T, ?> evaluable) {
		this.connective = connective;
		this.evaluable  = evaluable;
	}

	public UnaryStatement(Evaluable<T, ?> evaluable) {
		this.connective = new UnaryConnective(UnaryConnective.UnaryConnectiveType.EMPTY);
		this.evaluable  = evaluable;
	}

	@Override
	public Boolean evaluate(Model<T, ?, ?> model) throws Exception {
		return connective.apply(evaluable.evaluate(model));
	}

	@Override
	public void reduce(Map<String, Function<T, ?, ?>> reductions) {
		evaluable.reduce(reductions);
	}

	@Override
	public UnaryStatement<T> copy() {
		return new UnaryStatement<>(connective, evaluable.copy());
	}

	@Override
	public String toString() {
		return "(" + connective.toString() + evaluable.toString() + ")";
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
