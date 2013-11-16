package logic.type;

import logic.type.map.MapWithErrors;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Steven Weston
 */
public interface TypeMatcher {

	/**
	 * Given a list of nodes and types, find out the type that is represented by the nodes.
	 *
	 * @param nodes The list of nodes (including surrounding brackets)
	 * @param types The map of types
	 * @return The type if the type was found, null otherwise.
	 */
	Type getType(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Type> types) throws TypeInferrorException;
}
