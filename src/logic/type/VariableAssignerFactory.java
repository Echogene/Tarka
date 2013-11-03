package logic.type;

import logic.type.map.MapWithErrors;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author Steven Weston
 */
public interface VariableAssignerFactory {

	Map<String, Type> assignVariableTypes(
			List<ParseTreeNode> nodes,
			MapWithErrors<ParseTreeNode, Type> functionTypes
	) throws VariableAssignmentTypeException;
}
