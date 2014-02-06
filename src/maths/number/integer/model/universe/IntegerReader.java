package maths.number.integer.model.universe;

import logic.StandardReader;
import logic.factory.SimpleLogicReader;
import logic.function.factory.FunctionFactory;
import logic.function.maths.number.addition.BinaryAdditionFactory;
import logic.function.maths.number.addition.MultaryAdditionFactory;
import logic.function.maths.number.addition.SetTotalFactory;
import logic.function.maths.number.interval.IntervalFunctionFactory;
import logic.function.maths.number.multiplication.BinaryMultiplicationFactory;
import logic.function.maths.number.multiplication.MultaryMultiplicationFactory;
import logic.function.maths.number.subtraction.SubtractionFactory;
import maths.number.integer.Integer;
import maths.number.integer.IntegerMultiplior;
import maths.number.integer.IntegerSubtractor;
import maths.number.integer.IntegerSummor;
import maths.number.integer.sets.interval.FiniteIntegerIntervalFactory;

import java.util.List;

/**
 * @author Steven Weston
 */
public class IntegerReader {
	public static SimpleLogicReader<Integer> createStandardReader(IntegerUniverse universe) {
		Class<Integer> universeType = universe.getTypeOfUniverse();
		List<FunctionFactory<Integer, ?, ?>> factories = StandardReader.getStandardFunctionFactories(universeType);
		factories.add(new BinaryAdditionFactory<>(new IntegerSummor(), universeType));
		factories.add(new MultaryAdditionFactory<>(new IntegerSummor(), universeType));
		factories.add(new BinaryMultiplicationFactory<>(new IntegerMultiplior(), universeType));
		factories.add(new MultaryMultiplicationFactory<>(new IntegerMultiplior(), universeType));
		factories.add(new SetTotalFactory<>(new IntegerSummor(), universeType));
		factories.add(new SubtractionFactory<>(new IntegerSubtractor(), universeType));
		factories.add(new IntervalFunctionFactory<>(new FiniteIntegerIntervalFactory(), universeType));
		return new SimpleLogicReader<>(factories, universe);
	}
}
