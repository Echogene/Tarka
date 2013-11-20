package logic.type;

import reading.parsing.ParseTree;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

/**
 * @author Steven Weston
 */
public interface TypeInferror {

	/**
	 * Go through the tree and for each scope, attach a map of variables to their types existing in that scope.
	 *
	 *
	 * @param tree
	 * @param passedMatchers The type matchers to use to match each node
	 * @param passedAssigners
	 * @return
	 */
	Map<ParseTreeNode, Type> inferTypes(
			ParseTree tree,
			Map<ParseTreeNode, ? extends Collection<? extends TypeMatcher>> passedMatchers,
			Map<ParseTreeNode, ? extends Collection<? extends VariableAssignerFactory>> passedAssigners) throws TypeInferrorException;
}
