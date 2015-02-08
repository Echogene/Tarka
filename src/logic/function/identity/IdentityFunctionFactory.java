package logic.function.identity;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.function.factory.ConstructorFromString;
import logic.function.factory.FunctionFactory;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.set.SetFunction;
import logic.set.Set;
import logic.oldtype.TypeInferrorException;
import logic.oldtype.map.MapWithErrors;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;
import util.CollectionUtils;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.OPEN_BRACKET;

/**
 * @author Steven Weston
 */
public class IdentityFunctionFactory<T extends Nameable>
		extends FunctionFactory<T, Object, AbstractIdentityFunction<T, ?, ?, ?>>
		implements
				ConstructorFromString<MemberIdentityFunction<T>>,
				IdentityConstructorFromType<T>  {

	public IdentityFunctionFactory(Class<T> universeType) {
		super(getCheckers(), STANDARD_BRACKETS, universeType);
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.<CheckerWithNumber>asList(
				new FunctionOrVariableChecker(
						ReflexiveFunction.class,
						Evaluable.class,
						SetFunction.class
				)
		);
	}

	@Override
	public MemberIdentityFunction<T> construct(String parameterName) {
		return new MemberIdentityFunction<>(parameterName);
	}

	@Override
	public java.util.Set<Type> getTypes(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, java.util.Set<Type>> types) throws TypeInferrorException {
		return types.getPassedValues().get(nodes.get(1));
	}

	@Override
	public AbstractIdentityFunction<T, ?, ?, ?> construct(List<Token> tokens, List<Function<T, ?, ?>> functions, Map<String, java.util.Set<Type>> boundVariables) throws FactoryException {
		Function<T, ?, ?> function = CollectionUtils.first(functions);
		if (tokens.get(1).isOfType(OPEN_BRACKET)) {
			if (function instanceof ReflexiveFunction<?, ?>) {
				return new MemberIdentityFunction<>((ReflexiveFunction<T, ?>) function);
			} else if (function instanceof Evaluable<?, ?>) {
				return new EvaluableIdentityFunction<>((Evaluable<T, ?>) function);
			} else if (function instanceof SetFunction<?, ?>) {
				return new SetIdentityFunction<>((SetFunction<T, ?>) function);
			} else {
				return new MultitypeIdentityFunction<>((Function<T,Object,?>) function);
			}
		} else {
			return (AbstractIdentityFunction<T, ?, ?, ?>) function;
		}
	}

	@Override
	public AbstractIdentityFunction<T, ?, ?, ?> create(String parameter, java.util.Set<Type> types) {
		if (types.size() == 1) {
			Type type = types.iterator().next();
			if (type == Boolean.class) {
				return new EvaluableIdentityFunction<>(parameter);
			} else if (type == Set.class || Set.class.isAssignableFrom((Class) type)) {
				return new SetIdentityFunction<>(parameter);
			} else {
				return new MemberIdentityFunction<>(parameter);
			}
		} else {
			return new MultitypeIdentityFunction<>(parameter);
		}
	}

	@Override
	public List<ParseTreeNode> getVariables(List<ParseTreeNode> nodes) {
		return getSingleVariableWithIndex(nodes, 1);
	}

	@Override
	public java.util.Set<Type> guessTypes(ParseTreeNode variable, List<ParseTreeNode> nodes) {
		return nonVoidTypes;
	}

	@Override
	public java.util.Set<Type> getPotentialReturnTypes(List<ParseTreeNode> surroundedChildren) {
		return nonVoidTypes;
	}
}
