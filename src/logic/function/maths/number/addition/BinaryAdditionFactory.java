package logic.function.maths.number.addition;

import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.OperatorChecker;
import logic.function.reflexive.ReflexiveFunction;
import maths.number.Number;
import maths.number.Summor;

import java.util.Arrays;
import java.util.List;

/**
 * @author Steven Weston
 */
public class BinaryAdditionFactory<N extends Number> extends AbstractAdditionFactory<N> {

	public BinaryAdditionFactory(Summor<N> summor, Class<N> universeType) {
		super(getCheckers(), universeType, summor);
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.asList(
				new FunctionOrVariableChecker(ReflexiveFunction.class),
				new OperatorChecker(Addition.PLUS_SYMBOL),
				new FunctionOrVariableChecker(ReflexiveFunction.class)
		);
	}
}
