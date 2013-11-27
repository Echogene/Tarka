package logic.function.ifelse;

import javafx.util.Pair;
import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.function.factory.FunctionFactory;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.StringChecker;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.set.SetFunction;
import logic.function.voidfunction.VoidFunction;
import logic.type.TypeInferrorException;
import logic.type.map.MapWithErrors;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.OPEN_BRACKET;

/**
 * @author Steven Weston
 */
public class IfElseFactory<T extends Nameable> extends FunctionFactory<T, Object, IfElse<T, ?>> {

	public IfElseFactory(Class<T> universeType) {
		super(getCheckers(), Arrays.asList(new Pair<>("(", ")")), universeType);
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.asList(
				new FunctionOrVariableChecker(Function.class),
				new StringChecker(IfElse.IF),
				new FunctionOrVariableChecker(Evaluable.class),
				new StringChecker(IfElse.OTHERWISE),
				new FunctionOrVariableChecker(Function.class)
				// todo: somehow validate that the first and third functions are the same type
		);
	}

	@Override
	public Type getType(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Type> types) throws TypeInferrorException {
		return types.getPassedValues().get(nodes.get(1));
	}

	@Override
	public IfElse<T, ?> construct(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		Function<?, ?> ifTrue = functions.get(0);
		Function<?, ?> condition = functions.get(1);
		Function<?, ?> ifFalse = functions.get(2);
		// todo: this might be able to be neater
		if (ifTrue instanceof ReflexiveFunction<?>) {
			if (!(ifFalse instanceof ReflexiveFunction<?>)) {
				throw new FactoryException("The true and false cases were different types.");
			}
			return new ReflexiveIfElse<>((Evaluable<T>) condition, (ReflexiveFunction<T>) ifTrue, (ReflexiveFunction<T>) ifFalse);
		} else if (ifTrue instanceof Evaluable<?>) {
			if (!(ifFalse instanceof Evaluable<?>)) {
				throw new FactoryException("The true and false cases were different types.");
			}
			return new EvaluableIfElse<>((Evaluable<T>) condition, (Evaluable<T>) ifTrue, (Evaluable<T>) ifFalse);
		} else if (ifTrue instanceof VoidFunction<?>) {
			if (!(ifFalse instanceof VoidFunction<?>)) {
				throw new FactoryException("The true and false cases were different types.");
			}
			return new VoidIfElse<>((Evaluable<T>) condition, (VoidFunction<T>) ifTrue, (VoidFunction<T>) ifFalse);
		} else if (ifTrue instanceof SetFunction<?>) {
			if (!(ifFalse instanceof SetFunction<?>)) {
				throw new FactoryException("The true and false cases were different types.");
			}
			return new SetIfElse<>((Evaluable<T>) condition, (SetFunction<T>) ifTrue, (SetFunction<T>) ifFalse);
		} else {
			throw new FactoryException("Unknown function type.");
		}
	}

	@Override
	public List<ParseTreeNode> getVariables(List<ParseTreeNode> nodes) {
		ParseTreeNode firstNode = nodes.get(1);
		if (firstNode.getToken().isOfType(OPEN_BRACKET)) {
			ParseTreeNode secondNode = nodes.get(4);
			if (secondNode.getToken().isOfType(OPEN_BRACKET)) {
				ParseTreeNode thirdNode = nodes.get(7);
				if (thirdNode.getToken().isOfType(OPEN_BRACKET)) {
					return new ArrayList<>();
				} else {
					return Arrays.asList(thirdNode);
				}
			} else {
				ParseTreeNode thirdNode = nodes.get(6);
				if (thirdNode.getToken().isOfType(OPEN_BRACKET)) {
					return Arrays.asList(secondNode);
				} else {
					return Arrays.asList(secondNode, thirdNode);
				}
			}
		} else {
			ParseTreeNode secondNode = nodes.get(3);
			if (secondNode.getToken().isOfType(OPEN_BRACKET)) {
				ParseTreeNode thirdNode = nodes.get(6);
				if (thirdNode.getToken().isOfType(OPEN_BRACKET)) {
					return Arrays.asList(firstNode);
				} else {
					return Arrays.asList(firstNode, thirdNode);
				}
			} else {
				ParseTreeNode thirdNode = nodes.get(5);
				if (thirdNode.getToken().isOfType(OPEN_BRACKET)) {
					return Arrays.asList(firstNode, secondNode);
				} else {
					return Arrays.asList(firstNode, secondNode, thirdNode);
				}
			}
		}
	}
}
