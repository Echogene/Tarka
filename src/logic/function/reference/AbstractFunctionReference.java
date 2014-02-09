package logic.function.reference;

import logic.Nameable;
import logic.function.Function;
import logic.model.Model;

import java.util.Map;

/**
 * @author Steven Weston
 */
public abstract class AbstractFunctionReference<
		D extends Nameable,
		C,
		F extends AbstractFunctionReference<D, C, F, G>,
		G extends Function<D, C, ? extends G>
>
		implements Function<D, C, F> {

	G referee;
	private G reference;

	AbstractFunctionReference() {}

	AbstractFunctionReference(G referee) {
		this.referee = referee;
	}

	@Override
	public C evaluate(Model<D, ?, ?> model) throws Exception {
		return getReference().evaluate(model);
	}

	@Override
	public void reduce(Map<String, Function<D, ?, ?>> reductions) {
		getReference().reduce(reductions);
	}

	private G getReference() {
		if (reference == null) {
			reference = referee.copy();
		}
		return reference;
	}

	public void setReferee(G referee) {
		this.referee = referee;
	}

	@Override
	public String toString() {
		return referee.toString();
	}
}
