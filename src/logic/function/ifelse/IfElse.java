package logic.function.ifelse;

import logic.Nameable;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.model.Model;

import java.util.Map;

import static java.text.MessageFormat.format;

/**
 * @author Steven Weston
 */
class IfElse<D extends Nameable, C> implements Function<D, C> {

	public static final String IF = "if";
	public static final String OTHERWISE = "otherwise";

	private final Evaluable<D> condition;
	private final Function<D, C> ifTrue;
	private final Function<D, C> ifFalse;

	IfElse(Evaluable<D> condition, Function<D, C> ifTrue, Function<D, C> ifFalse) {
		this.condition = condition;
		this.ifTrue = ifTrue;
		this.ifFalse = ifFalse;
	}

	@Override
	public C evaluate(Model<D, ?, ?> model) throws Exception {
		if (condition.evaluate(model)) {
			return ifTrue.evaluate(model);
		} else {
			return ifFalse.evaluate(model);
		}
	}

	@Override
	public void reduce(Map<String, Function<D, ?>> reductions) {
		condition.reduce(reductions);
		ifTrue.reduce(reductions);
		ifFalse.reduce(reductions);
	}

	@Override
	public String toString() {
		return format("({0} {1} {2} {3} {4})", ifTrue, IF, condition, OTHERWISE, ifFalse);
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
