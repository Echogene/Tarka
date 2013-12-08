package logic.function.voidfunction.definition.function;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.FunctionFactory;
import logic.function.factory.validation.checking.CheckerWithNumber;
import logic.function.factory.validation.checking.checkers.FunctionfulChecker;
import logic.function.factory.validation.checking.checkers.NumberedChecker;
import logic.function.factory.validation.checking.checkers.VariableChecker;
import logic.type.TypeInferrorException;
import logic.type.map.MapWithErrors;
import reading.lexing.Token;
import reading.parsing.ParseTreeNode;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static logic.function.factory.validation.checking.CheckerWithNumber.Number.MANY;

/**
 * @author Steven Weston
 */
public class DefinedFunctionFactory<D extends Nameable, C, F extends Function<D, C>> extends FunctionFactory<D, C, F> {

	private final F definedFunction;

	protected DefinedFunctionFactory(Class<D> universeType, F definedFunction) {
		super(getCheckers(), STANDARD_BRACKETS, universeType);
		this.definedFunction = definedFunction;
	}

	private static List<CheckerWithNumber> getCheckers() {
		return Arrays.asList(
				new VariableChecker(),
				new NumberedChecker(MANY, new VariableChecker()),
				new FunctionfulChecker()
		);
	}

	@Override
	public List<ParseTreeNode> getVariables(List<ParseTreeNode> nodes) {
		throw new NotImplementedException();
	}

	@Override
	public F construct(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		throw new NotImplementedException();
	}

	@Override
	public Type getType(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Type> types) throws TypeInferrorException {
		throw new NotImplementedException();
	}
}
