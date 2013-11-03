package logic.type;

import reading.parsing.ParseTree;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author Steven Weston
 */
public interface TypeInferror {

	/**
	 * Go through the tree and for each scope, attach a map of variables to their types existing in that scope.
	 *
	 * @param tree
	 * @return
	 */
	Map<ParseTreeNode, Type> inferTypes(ParseTree tree) throws TypeInferrorException;
}
