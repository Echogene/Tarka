package logic.function.assignment;

import javafx.util.Pair;
import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.function.factory.FunctionFactory;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.StringChecker;
import logic.function.factory.validation.checking.checkers.VariableChecker;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.set.SetFunction;
import logic.function.voidfunction.VoidFunction;
import logic.type.TypeInferrorException;
import logic.type.map.MapWithErrors;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.OPEN_BRACKET;

/**
 * @author Steven Weston
 */
public class AssignmentFactory<T extends Nameable> extends FunctionFactory<T, Object, Assignment<T, ?>> {

	public static final String WHERE = "where";
	public static final String IS = "is";

	public AssignmentFactory(Class<T> universeType) {
		super(getCheckers(), Arrays.asList(new Pair<>("(", ")")), universeType);
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.asList(
				new FunctionOrVariableChecker(),
				new StringChecker(WHERE),
				new VariableChecker(),
				new StringChecker(IS),
				new FunctionOrVariableChecker(
						ReflexiveFunction.class,
						Evaluable.class,
						SetFunction.class
				)
		);
	}

	@Override
	public Assignment<T, ?> construct(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		Function<?, ?> evaluee = functions.get(0);
		String assignee;
		if (tokens.get(1).isOfType(OPEN_BRACKET)) {
			assignee = tokens.get(4).getValue();
		} else {
			assignee = tokens.get(3).getValue();
		}
		Function<T, ?> assignment = (Function<T, ?>) functions.get(2);
		if (evaluee instanceof ReflexiveFunction) {
			return new ReflexiveAssignment<>((ReflexiveFunction<T>) evaluee, assignee, assignment);
		} else if (evaluee instanceof Evaluable) {
			return new EvaluableAssignment<>((Evaluable<T>) evaluee, assignee, assignment);
		} else if (evaluee instanceof SetFunction) {
			return new SetAssignment<>((SetFunction<T>) evaluee, assignee, assignment);
		} else if (evaluee instanceof VoidFunction) {
			return new VoidAssignment<>((VoidFunction<T>) evaluee, assignee, assignment);
		} else {
			throw new FactoryException("Unknown function type.");
		}
	}

	@Override
	public Type getType(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Type> types) throws TypeInferrorException {
		return types.getPassedValues().get(nodes.get(1));
	}
}
