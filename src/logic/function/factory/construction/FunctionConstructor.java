package logic.function.factory.construction;

import logic.Nameable;
import logic.factory.FactoryException;
import logic.function.Function;
import reading.lexing.Token;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Steven Weston
 */
public interface FunctionConstructor<T extends Nameable, F> {

	F construct(List<Token> tokens, List<Function<T, ?>> functions, Map<String, Set<Type>> boundVariables) throws FactoryException;
}
