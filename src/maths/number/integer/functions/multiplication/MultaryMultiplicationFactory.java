package maths.number.integer.functions.multiplication;

import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.NumberedChecker;
import logic.function.factory.validation.checking.checkers.OperatorChecker;
import logic.function.reflexive.ReflexiveFunction;
import maths.number.Multiplior;
import maths.number.Number;

import java.util.Arrays;
import java.util.List;

import static logic.function.factory.validation.checking.CheckerWithNumber.Number.MANY;

/**
 * @author Steven Weston
 */
public class MultaryMultiplicationFactory<N extends Number> extends AbstractMultiplicationFactory<N> {

	public MultaryMultiplicationFactory(Multiplior<N> multiplior, Class<N> universeType) {
		super(getCheckers(), universeType, multiplior);
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.asList(
				new OperatorChecker(Multiplication.PRODUCT_SYMBOL),
				new NumberedChecker(MANY, new FunctionOrVariableChecker(ReflexiveFunction.class))
		);
	}
}
