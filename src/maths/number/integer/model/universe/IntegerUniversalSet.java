package maths.number.integer.model.universe;

import logic.set.InfiniteSet;
import maths.number.integer.Integer;

/**
 * @author Steven Weston
 */
public class IntegerUniversalSet extends InfiniteSet<Integer> {
	public IntegerUniversalSet(String name) {
		super(name);
	}

	@Override
	public Integer get(String string) {
		try {
			return new Integer(string);
		} catch (NumberFormatException e) {
			return super.get(string);
		}
	}

	@Override
	public boolean contains(String string) {
		return stringIsNumber(string) || super.contains(string);
	}

	@Override
	public boolean containsValue(Integer thing) {
		return true;
	}

	private boolean stringIsNumber(String string) {
		return string.matches("\\-?[\\d]+");
	}
}
