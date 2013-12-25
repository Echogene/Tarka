package logic.type;

import logic.type.map.MapWithErrors;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Steven Weston
 */
public interface VariableAssignerFactory {

	/**
	 * Return a map from variable names to the represented type given the nodes and function types.
	 *
	 *
	 *
	 * @param nodes the nodes to analyse (including surrounding brackets)
	 * @param functionTypes
	 * @param freeVariables
	 * @return
	 * @throws VariableAssignmentTypeException
	 */
	Map<String, Set<Type>> assignVariableTypes(
			List<ParseTreeNode> nodes,
			MapWithErrors<ParseTreeNode, Set<Type>> functionTypes,
			Map<String, Set<Type>> freeVariables
	) throws VariableAssignmentTypeException;

	boolean shouldWalkDownAt(ParseTreeNode node, List<ParseTreeNode> nodes);
}
