package maths.number.integer.functions.subtraction;

import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.OperatorChecker;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.ReflexiveFunctionFactory;
import maths.number.Number;
import maths.number.Subtractor;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;

import java.util.Arrays;
import java.util.List;

/**
 * @author Steven Weston
 */
public class SubtractionFactory<N extends Number> extends ReflexiveFunctionFactory<N, Subtraction<N>> {

	private final Subtractor<N> subtractor;

	public SubtractionFactory(Subtractor<N> subtractor, Class<N> universeType) {
		super(getCheckers(), STANDARD_BRACKETS, universeType);
		this.subtractor = subtractor;
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.asList(
				new FunctionOrVariableChecker(ReflexiveFunction.class),
				new OperatorChecker(Subtraction.MINUS),
				new FunctionOrVariableChecker(ReflexiveFunction.class)
		);
	}

	@Override
	public Subtraction<N> construct(List<Token> tokens, List<Function<N, ?>> functions) throws FactoryException {
		return new Subtraction<>((ReflexiveFunction<N>) functions.get(0), (ReflexiveFunction<N>) functions.get(1), subtractor);
	}

	@Override
	public List<ParseTreeNode> getVariables(List<ParseTreeNode> nodes) {
		return getAllVariables(nodes);
	}
}
