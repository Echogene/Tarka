package logic.function.maths.number.addition;

import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.NumberedChecker;
import logic.function.factory.validation.checking.checkers.OperatorChecker;
import logic.function.identity.MemberIdentityFunction;
import logic.function.reflexive.ReflexiveFunction;
import maths.number.Summor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static logic.function.factory.validation.checking.CheckerWithNumber.Number.MANY;

/**
 * @author Steven Weston
 */
public class MultaryAdditionFactory<N extends maths.number.Number> extends AbstractAdditionFactory<N> {

	public MultaryAdditionFactory(Summor<N> summor, Class<N> universeType) {
		super(getCheckers(), universeType, summor);
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.asList(
				new OperatorChecker(Addition.SUM_SYMBOL),
				new NumberedChecker(MANY, new FunctionOrVariableChecker(ReflexiveFunction.class))
		);
	}

	public Addition<N> create(String... summands) {

		List<ReflexiveFunction<N, ?>> parameters = new ArrayList<>(summands.length);
		for (String summand : summands) {
			parameters.add(new MemberIdentityFunction<>(summand));
		}
		return new Addition<>(parameters, summor);
	}
}
