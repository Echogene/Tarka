package logic.factory;

import reading.lexing.Token;

import java.util.List;

/**
 * @author Steven Weston
 */
public interface Factory<T> {
	public T createElement(List<Token> tokens) throws FactoryException;
}
