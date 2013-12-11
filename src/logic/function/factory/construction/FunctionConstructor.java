package logic.function.factory.construction;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import reading.lexing.Token;

import java.util.List;

/**
 * @author Steven Weston
 */
public interface FunctionConstructor<T extends Nameable, F> {

	F construct(List<Token> tokens, List<Function<T, ?>> functions) throws FactoryException;
}
