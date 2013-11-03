package maths.number.integer.model.universe;

import logic.StandardReader;
import logic.factory.SimpleLogicReaderImpl;
import logic.function.factory.FunctionFactory;
import maths.number.integer.Integer;
import maths.number.integer.IntegerSubtractor;
import maths.number.integer.IntegerSummor;
import maths.number.integer.functions.addition.AdditionFactory;
import maths.number.integer.functions.interval.IntervalFunctionFactory;
import maths.number.integer.functions.subtraction.SubtractionFactory;
import maths.number.integer.sets.interval.FiniteIntegerIntervalFactory;

import java.util.List;

/**
 * @author Steven Weston
 */
public class IntegerReader {
	public static SimpleLogicReaderImpl<Integer> createStandardReader() {
		List<FunctionFactory<Integer, ?, ?>> factories = StandardReader.getStandardFunctionFactories();
		factories.add(new AdditionFactory<>(new IntegerSummor()));
		factories.add(new SubtractionFactory<>(new IntegerSubtractor()));
		factories.add(new IntervalFunctionFactory<>(new FiniteIntegerIntervalFactory()));
		return new SimpleLogicReaderImpl<>(factories);
	}
}
