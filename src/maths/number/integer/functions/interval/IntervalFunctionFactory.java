package maths.number.integer.functions.interval;

import javafx.util.Pair;
import logic.function.Function;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.set.SetFunctionFactory;
import maths.number.Number;
import maths.number.integer.sets.interval.IntervalFactory;
import reading.lexing.Token;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static maths.number.integer.sets.interval.IntervalBound.BoundType;
import static maths.number.integer.sets.interval.IntervalBound.BoundType.CLOSED;
import static maths.number.integer.sets.interval.IntervalBound.BoundType.OPEN;
import static util.CollectionUtils.first;
import static util.CollectionUtils.last;

/**
 * @author Steven Weston
 */
public class IntervalFunctionFactory<N extends Number> extends SetFunctionFactory<N, IntervalFunction<N>> {

	private final IntervalFactory<N> factory;

	public IntervalFunctionFactory(IntervalFactory<N> factory, Class<N> universeType) {
		super(getCheckers(), getAcceptedBracketPairs(), universeType);
		this.factory = factory;
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.<CheckerWithNumber>asList(
				new FunctionOrVariableChecker(),
				new FunctionOrVariableChecker()
		);
	}

	private static List<Pair<String, String>> getAcceptedBracketPairs() {
		return asList(
				new Pair<>("[", "]"),
				new Pair<>("[", ")"),
				new Pair<>("(", "]"),
				new Pair<>("(", ")")
		);
	}

	@Override
	public IntervalFunction<N> construct(List<Token> tokens, List<Function<?, ?>> functions) {
		String openingBracket = first(tokens).getValue();
		BoundType lowerType;
		if ("[".equals(openingBracket)) {
			lowerType = CLOSED;
		} else {
			lowerType = OPEN;
		}
		String closingBracket = last(tokens).getValue();
		BoundType upperType;
		if ("]".equals(closingBracket)) {
			upperType = CLOSED;
		} else {
			upperType = OPEN;
		}

		return new IntervalFunction<>(
				lowerType,
				(ReflexiveFunction<N>) functions.get(0),
				(ReflexiveFunction<N>) functions.get(1),
				upperType,
				factory
		);
	}
}
