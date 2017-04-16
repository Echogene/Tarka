package maths.number.integer.sets.interval;

import maths.number.integer.Integer;

import static maths.number.integer.sets.interval.IntervalBound.BoundType;
import static maths.number.integer.sets.interval.IntervalBound.BoundType.OPEN;

/**
 * @author Steven Weston
 */
public class FiniteIntegerIntervalFactory implements IntervalFactory<Integer> {

	public FiniteIntegerInterval createElement(
			BoundType lowerType, int lowerBound, int upperBound, BoundType upperType
	) {
		return createElement(lowerType, new Integer(lowerBound), new Integer(upperBound), upperType);
	}

	@Override
	public FiniteIntegerInterval createElement(
			BoundType lowerType, Integer lowerBound, Integer upperBound, BoundType upperType
	) {
		return new FiniteIntegerInterval(
				generateName(lowerType, lowerBound, upperBound, upperType),
				new IntegerIntervalBound(upperBound, upperType),
				new IntegerIntervalBound(lowerBound, lowerType)
		);
	}

	private static String generateName(BoundType lowerType, Integer lowerBound, Integer upperBound, BoundType upperType) {
		String output = "";
		if (lowerType == OPEN) {
			output += "(";
		} else {
			output += "[";
		}
		output += lowerBound.toString();
		output += " ";
		output +=  upperBound.toString();
		if (upperType == OPEN) {
			output += ")";
		} else {
			output += "]";
		}
		return output;
	}
}
