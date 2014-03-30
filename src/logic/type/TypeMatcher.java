package logic.type;

import logic.type.map.MapWithErrors;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Steven Weston
 */
public interface TypeMatcher {

	/**
	 * Given a list of nodes and types, find out the type that is represented by the nodes.
	 * todo: why does this have a map with errors
	 *
	 *
	 * @param nodes The list of nodes (including surrounding brackets)
	 * @param types The map of types
	 * @return The type if the type was found, null otherwise.
	 */
	Set<Type> getTypes(List<ParseTreeNode> nodes, MapWithErrors<ParseTreeNode, Set<Type>> types) throws TypeInferrorException;

	/**
	 * Given a list of nodes, return a sublist of those who represent variables
	 * @param nodes the list of nodes (including surrounding brackets)
	 * @return
	 */
	List<ParseTreeNode> getVariables(List<ParseTreeNode> nodes);

	/**
	 * Guess the possible types of the free variable based on its position within the given nodes.
	 * @param variable
	 * @param nodes the list of nodes (including surrounding brackets)
	 * @return
	 */
	Set<Type> guessTypes(ParseTreeNode variable, List<ParseTreeNode> nodes);

	Set<Type> getPotentialReturnTypes(List<ParseTreeNode> surroundedChildren);

	/**
	 * Go through the variables and guess the types of each.
	 * @param nodes the list of nodes (including surrounding brackets)
	 * @return
	 */
	default Map<ParseTreeNode, Set<Type>> guessVariableTypes(List<ParseTreeNode> nodes) {

		List<ParseTreeNode> variables = getVariables(nodes);
		Map<ParseTreeNode, Set<Type>> output = new HashMap<>(variables.size());
		for (ParseTreeNode variable : variables) {
			output.put(variable, guessTypes(variable, nodes));
		}
		return output;
	}
}
