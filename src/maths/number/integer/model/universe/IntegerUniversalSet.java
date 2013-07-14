package maths.number.integer.model.universe;

import logic.set.NamedSet;
import maths.number.integer.Integer;

/**
 * @author Steven Weston
 */
public class IntegerUniversalSet extends NamedSet<Integer> {
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

	private boolean stringIsNumber(String string) {
		return string.matches("[\\d]+");
	}
}
