package maths.number.integer.model.universe;

import logic.set.InfiniteSet;
import logic.set.Set;
import maths.number.integer.Integer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author Steven Weston
 */
public class IntegerSet extends InfiniteSet<Integer> {
	public IntegerSet(String name) {
		super(name);
	}

	@Override
	public Integer get(String string) {
		return new Integer(string);
	}

	@Override
	public boolean contains(String string) {
		return stringIsNumber(string);
	}

	@Override
	public boolean containsValue(Integer thing) {
		return true;
	}

	@Override
	public void uniteWith(Set<Integer> s) {
		throw new NotImplementedException();
	}

	@Override
	public Set<Integer> copy(String name) {
		throw new NotImplementedException();
	}

	private boolean stringIsNumber(String string) {
		return string.matches("\\-?[\\d]+");
	}
}
