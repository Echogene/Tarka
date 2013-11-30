package maths.number.integer.functions.multiplication;

import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.OperatorChecker;
import logic.function.reflexive.ReflexiveFunction;
import maths.number.Multiplior;
import maths.number.Number;

import java.util.Arrays;
import java.util.List;

/**
 * @author Steven Weston
 */
public class BinaryMultiplicationFactory<N extends Number> extends AbstractMultiplicationFactory<N> {

	public BinaryMultiplicationFactory(Multiplior<N> multiplior, Class<N> universeType) {
		super(getCheckers(), universeType, multiplior);
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.asList(
				new FunctionOrVariableChecker(ReflexiveFunction.class),
				new OperatorChecker(Multiplication.TIMES_SYMBOL),
				new FunctionOrVariableChecker(ReflexiveFunction.class)
		);
	}
}
