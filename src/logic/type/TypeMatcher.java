package logic.type;

import logic.type.map.ExtractorException;
import logic.type.map.MapWithErrors;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Steven Weston
 */
public interface TypeMatcher {

	/**
	 * Given a list of tokens and types, find out the type that is represented by the tokens.
	 *
	 * @param tokens The list of tokens
	 * @param types The map of types
	 * @return The type if the type was found, null otherwise.
	 */
	Type getType(List<ParseTreeNode> tokens, MapWithErrors<ParseTreeNode, Type> types) throws ExtractorException;
}
