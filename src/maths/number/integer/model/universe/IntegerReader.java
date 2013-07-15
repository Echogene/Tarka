package maths.number.integer.model.universe;

import logic.StandardReader;
import logic.factory.SimpleLogicReaderImpl;
import logic.function.factory.FunctionFactory;
import maths.number.integer.Integer;
import maths.number.integer.IntegerSummor;
import maths.number.integer.functions.addition.AdditionFactory;

import java.util.List;

/**
 * @author Steven Weston
 */
public class IntegerReader {
	public static <T extends Integer> SimpleLogicReaderImpl<T> createStandardReader() {
		List<FunctionFactory<?, ?>> factories = StandardReader.getStandardFunctionFactories();
		factories.add(new AdditionFactory<>(new IntegerSummor()));
		return new SimpleLogicReaderImpl<>(factories);
	}
}
