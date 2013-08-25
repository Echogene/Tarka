package maths.number.integer.sets.interval;

import maths.number.Number;

/**
 * @author Steven Weston
 */
public class IntervalBound<N extends Number> {

	private final N bound;
	private final BoundType boundType;

	public IntervalBound(N bound, BoundType boundType) {
		this.bound = bound;
		this.boundType = boundType;
	}

	public BoundType getBoundType() {
		return boundType;
	}

	public N getBound() {
		return bound;
	}

	public enum BoundType {
		CLOSED, OPEN;

		public String toLowerString() {
			return toString(true);
		}

		public String toUpperString() {
			return toString(false);
		}

		private String toString(boolean lower) {
			switch(this) {
				case CLOSED:
					return lower ? "[" : "]";
				case OPEN:
					return lower ? "(" : ")";
				default:
					throw new IllegalStateException();
			}
		}
	}
}
