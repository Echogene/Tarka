package logic.function.reference;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.FunctionFactory;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionOrVariableChecker;
import logic.function.factory.validation.checking.checkers.NumberedChecker;
import logic.function.factory.validation.checking.checkers.VariableChecker;
import logic.oldtype.TypeInferrorException;
import logic.oldtype.map.MapWithErrors;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;
import exceptions.NotImplementedYetException;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static logic.function.factory.validation.checking.CheckerWithNumber.Number.MANY;

/**
 * @author Steven Weston
 */
public class FunctionReferenceFactory<T extends Nameable>
		extends FunctionFactory<T, Object, AbstractFunctionReference<T, ?, ?, ?, ?>> {

	public FunctionReferenceFactory(Class<T> universeType) {
		super(getCheckers(), STANDARD_BRACKETS, universeType);
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.asList(
				new VariableChecker(),
				new NumberedChecker(MANY, new FunctionOrVariableChecker())
		);
	}

	@Override
	public AbstractFunctionReference<T, ?, ?, ?, ?> construct(
			List<Token> tokens, List<Function<T, ?, ?>> functions, Map<String, Set<Type>> boundVariables
	) throws FactoryException {

		throw new NotImplementedYetException();
	}

	@Override
	public Set<Type> getTypes(
			List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Set<Type>> types
	) throws TypeInferrorException {

		// We don't know the type yet...
		return null;
	}

	@Override
	public List<ParseTreeNode> getVariables(List<ParseTreeNode> nodes) {
		return getAllVariablesExcept(nodes, 1);
	}

	@Override
	public Set<Type> guessTypes(ParseTreeNode variable, List<ParseTreeNode> nodes) {
		return nonVoidTypes;
	}

	@Override
	public Set<Type> getPotentialReturnTypes(List<ParseTreeNode> surroundedChildren) {
		return allTypes;
	}
}
