package logic.function.reflexive.assignment;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import logic.function.factory.FunctionFactory;
import reading.lexing.Token;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * @author Steven Weston
 */
public class AssignmentFactory<T extends Nameable> extends FunctionFactory<T, Object> {
	@Override
	public Assignment<T> createElement(List<Token> tokens) throws FactoryException {
		throw new NotImplementedException();
	}

	@Override
	public Assignment<T> createElement(List<Token> tokens, List<Function<?, ?>> functions) throws FactoryException {
		throw new NotImplementedException();
	}
}
