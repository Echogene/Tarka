package maths.number.integer.model.universe;

import logic.StandardReader;
import logic.factory.SimpleLogicReaderImpl;
import logic.function.factory.FunctionFactory;
import logic.model.universe.Universe;
import maths.number.integer.Integer;
import maths.number.integer.IntegerSubtractor;
import maths.number.integer.IntegerSummor;
import maths.number.integer.functions.addition.BinaryAdditionFactory;
import maths.number.integer.functions.interval.IntervalFunctionFactory;
import maths.number.integer.functions.subtraction.SubtractionFactory;
import maths.number.integer.sets.interval.FiniteIntegerIntervalFactory;

import java.util.List;

/**
 * @author Steven Weston
 */
public class IntegerReader {
	public static SimpleLogicReaderImpl<Integer> createStandardReader(Universe<Integer> universe) {
		Class<Integer> universeType = universe.getTypeOfUniverse();
		List<FunctionFactory<Integer, ?, ?>> factories = StandardReader.getStandardFunctionFactories(universeType);
		factories.add(new BinaryAdditionFactory<>(new IntegerSummor(), universeType));
		factories.add(new SubtractionFactory<>(new IntegerSubtractor(), universeType));
		factories.add(new IntervalFunctionFactory<>(new FiniteIntegerIntervalFactory(), universeType));
		return new SimpleLogicReaderImpl<>(factories, universe);
	}
}
