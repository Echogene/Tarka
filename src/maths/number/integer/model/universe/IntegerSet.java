package maths.number.integer.model.universe;

import logic.set.Set;
import logic.set.infinite.InfiniteSet;
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
		if (this.contains(string)) {
			return new Integer(string);
		} else {
			return null;
		}
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

	private boolean stringIsNumber(String string) {
		return string.matches("\\-?[\\d]+");
	}
}
