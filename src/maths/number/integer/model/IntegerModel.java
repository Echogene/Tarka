package maths.number.integer.model;

import logic.model.AbstractModel;
import maths.number.integer.Integer;
import maths.number.integer.model.universe.IntegerReader;
import maths.number.integer.model.universe.IntegerUniverse;

/**
 * @author Steven Weston
 */
public class IntegerModel extends AbstractModel<Integer, IntegerUniverse> {

	public IntegerModel() {
		super(createUniverse());
		setReader(IntegerReader.createStandardReader(getUniverse()));
	}

	private static IntegerUniverse createUniverse() {
		return new IntegerUniverse();
	}
}
