package maths.number.integer.functions.addition;

import javafx.util.Pair;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.NumberedChecker;
import logic.function.factory.validation.checking.checkers.OperatorChecker;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.ReflexiveFunctionFactory;
import logic.type.TypeInferrorException;
import logic.type.map.MapWithErrors;
import maths.number.Summor;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static logic.function.factory.validation.checking.CheckerWithNumber.Number.MANY;

/**
 * @author Steven Weston
 */
public class MultaryAdditionFactory<N extends maths.number.Number> extends ReflexiveFunctionFactory<N, Addition<N>> {

	private final Summor<N> summor;

	public MultaryAdditionFactory(Summor<N> summor, Class<N> universeType) {
		super(getCheckers(), Arrays.asList(new Pair<>("(", ")")), universeType);
		this.summor = summor;
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.asList(
				new OperatorChecker(Addition.PLUS_SYMBOL),
				new NumberedChecker(MANY, new FunctionOrVariableChecker(ReflexiveFunction.class))
		);
	}

	@Override
	public Addition<N> construct(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		List<ReflexiveFunction<N>> parameters = new ArrayList<>(functions.size());
		for (Function<?, ?> function : functions) {
			parameters.add((ReflexiveFunction<N>) function);
		}
		return new Addition<>(parameters, summor);
	}

	@Override
	public Type getType(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Type> types) throws TypeInferrorException {
		return types.getPassedValues().get(nodes.get(2));
	}

	@Override
	public List<ParseTreeNode> getVariables(List<ParseTreeNode> nodes) {
		return getAllVariables(nodes);
	}
}
