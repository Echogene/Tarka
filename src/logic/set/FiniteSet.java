package logic.set;

import maths.number.integer.Integer;

/**
 * @author Steven Weston
 */
public abstract class FiniteSet<T> extends NamedSet<T> implements Iterable<T> {

	protected FiniteSet(String name) {
		super(name);
	}

	public abstract Integer size();
}
