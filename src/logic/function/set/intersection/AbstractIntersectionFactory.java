package logic.function.set.intersection;

import logic.Nameable;
import logic.function.Function;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.identity.SetIdentityFunction;
import logic.function.set.SetFunction;
import logic.function.set.SetFunctionFactory;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.*;

/**
 * @author Steven Weston
 */
public abstract class AbstractIntersectionFactory<T extends Nameable> extends SetFunctionFactory<T, Intersection<T>> {

	AbstractIntersectionFactory(List<CheckerWithNumber> checkers, Class<T> universeType) {
		super(checkers, STANDARD_BRACKETS, universeType);
	}

	@Override
	public Intersection<T> construct(List<Token> tokens, List<Function<T, ?, ?>> functions, Map<String, Set<Type>> boundVariables) {
		java.util.Set<SetFunction<T, ?>> sets = new HashSet<>();
		for (Function<?, ?, ?> function : functions) {
			sets.add((SetFunction<T, ?>) function);
		}
		return new Intersection<>(sets);
	}

	@Override
	public List<ParseTreeNode> getVariables(List<ParseTreeNode> nodes) {
		return getAllVariables(nodes);
	}

	public static <T extends Nameable> Intersection<T> createElement(String... setNames) {
		java.util.Set<SetFunction<T, ?>> sets = new HashSet<>();
		for (String setName : setNames) {
			sets.add(new SetIdentityFunction<>(setName));
		}
		return new Intersection<>(sets);
	}

	@SafeVarargs
	public static <T extends Nameable> Intersection<T> createElement(SetFunction<T, ?>... setFunctions) {
		java.util.Set<SetFunction<T, ?>> sets = new HashSet<>();
		Collections.addAll(sets, setFunctions);
		return new Intersection<>(sets);
	}

	@Override
	public Set<Type> guessTypes(ParseTreeNode variable, List<ParseTreeNode> nodes) {
		return Collections.singleton(logic.set.Set.class);
	}
}
