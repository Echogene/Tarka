package maths.number.integer.model.universe;

import logic.set.Dictionary;
import logic.set.infinite.InfiniteSet;
import maths.number.integer.Integer;

/**
 * @author Steven Weston
 */
public class IntegerSet extends InfiniteSet<Integer> implements Dictionary<Integer> {
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

	private boolean stringIsNumber(String string) {
		return string.matches("\\-?[\\d]+");
	}
}
