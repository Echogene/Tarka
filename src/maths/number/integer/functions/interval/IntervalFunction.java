package maths.number.integer.functions.interval;

import logic.function.reflexive.ReflexiveFunction;
import logic.function.set.SetFunction;
import logic.model.universe.Universe;
import logic.set.Set;
import maths.number.Number;
import maths.number.integer.sets.interval.IntervalFactory;

import static maths.number.integer.sets.interval.IntervalBound.BoundType;

/**
 * @author Steven Weston
 */
public class IntervalFunction<N extends Number> implements SetFunction<N> {

	private final BoundType lowerType;
	private final ReflexiveFunction<N> lowerBound;
	private final ReflexiveFunction<N> upperBound;
	private final BoundType upperType;
	private final IntervalFactory<N> factory;

	public IntervalFunction(
			BoundType lowerType,
			ReflexiveFunction<N> lowerBound,
			ReflexiveFunction<N> upperBound,
			BoundType upperType,
			IntervalFactory<N> factory
	) {
		this.lowerType = lowerType;
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
		this.upperType = upperType;
		this.factory = factory;
	}

	@Override
	public Set<N> evaluate(Universe<N> universe) throws Exception {
		return factory.createElement(lowerType, lowerBound.evaluate(universe), upperBound.evaluate(universe), upperType);
	}

	@Override
	public String toString() {
		return lowerType.toLowerString() + lowerBound.toString() + " " + upperBound.toString() + upperType.toUpperString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		IntervalFunction that = (IntervalFunction) o;

		if (lowerBound != null ? !lowerBound.equals(that.lowerBound) : that.lowerBound != null) return false;
		if (lowerType != that.lowerType) return false;
		if (upperBound != null ? !upperBound.equals(that.upperBound) : that.upperBound != null) return false;
		if (upperType != that.upperType) return false;

		return true;
	}
}
