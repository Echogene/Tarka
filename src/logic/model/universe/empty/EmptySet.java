package logic.model.universe.empty;

import logic.set.NamedSet;
import logic.set.finite.FiniteSet;

import java.util.Collections;
import java.util.Iterator;

import static maths.number.integer.Integer.ZERO;

/**
 * @author Steven Weston
 */
public class EmptySet<T> extends NamedSet<T> implements FiniteSet<T> {

	public EmptySet() {
		super("∅");
	}

	@Override
	public boolean containsValue(Object thing) {
		return false;
	}

	@Override
	public String getName() {
		return "∅";
	}

	@Override
	public maths.number.integer.Integer size() {
		return ZERO;
	}

	@Override
	public Iterator<T> iterator() {
		return Collections.<T>emptySet().iterator();
	}
}
