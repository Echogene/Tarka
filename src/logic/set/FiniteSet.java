package logic.set;

import logic.Nameable;
import maths.number.integer.Integer;

/**
 * @author Steven Weston
 */
public abstract class FiniteSet<T extends Nameable> extends NamedSet<T> implements Iterable<T> {

	public FiniteSet(String name) {
		super(name);
	}

	public abstract Integer size();
}
