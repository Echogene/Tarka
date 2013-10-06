package logic.function.ifelse;

import logic.Nameable;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.model.universe.Universe;

/**
 * @author Steven Weston
 */
class IfElse<D extends Nameable, C> implements Function<D, C> {

	private final Evaluable<D> condition;
	private final Function<D, C> ifTrue;
	private final Function<D, C> ifFalse;

	public IfElse(Evaluable<D> condition, Function<D, C> ifTrue, Function<D, C> ifFalse) {
		this.condition = condition;
		this.ifTrue = ifTrue;
		this.ifFalse = ifFalse;
	}

	@Override
	public C evaluate(Universe<D> universe) throws Exception {
		if (condition.evaluate(universe)) {
			return ifTrue.evaluate(universe);
		} else {
			return ifFalse.evaluate(universe);
		}
	}

	@Override
	public String toString() {
		return "(" + ifTrue.toString() + " if " + condition.toString() + " otherwise " + ifFalse.toString() + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof IfElse)) {
			return false;
		}
		IfElse<?, ?> other = (IfElse<?, ?>) o;
		return condition.equals(other.condition)
				&& ifTrue.equals(other.ifTrue)
				&& ifFalse.equals(other.ifFalse);
	}
}
