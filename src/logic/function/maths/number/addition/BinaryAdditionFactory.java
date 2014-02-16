package logic.function.maths.number.addition;

import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.OperatorChecker;
import logic.function.identity.MemberIdentityFunction;
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

	public Addition<N> create(ReflexiveFunction<N, ?> summand1, String summand2) {

		return new Addition<>(
				Arrays.asList(
						summand1,
						new MemberIdentityFunction<>(summand2)
				),
				summor
		);
	}

	public Addition<N> create(ReflexiveFunction<N, ?> summand1, Integer summand2) {
		return create(summand1, Integer.toString(summand2));
	}
}
