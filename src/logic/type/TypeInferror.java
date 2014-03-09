package logic.type;

import logic.Nameable;
import logic.model.universe.Universe;
import reading.parsing.ParseTree;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author Steven Weston
 */
public abstract class TypeInferror<T extends Nameable> {

	protected final Universe<T, ?, ?> universe;
	protected final ParseTree tree;
	protected final Map<ParseTreeNode,? extends Collection<? extends TypeMatcher>> passedMatchers;
	protected final Map<ParseTreeNode, ? extends Collection<? extends VariableAssignerFactory>> passedAssigners;

	protected TypeInferror(
			Universe<T, ?, ?> universe,
			ParseTree tree,
			Map<ParseTreeNode, ? extends Collection<? extends TypeMatcher>> passedMatchers,
			Map<ParseTreeNode, ? extends Collection<? extends VariableAssignerFactory>> passedAssigners
	) {
		this.universe = universe;
		this.tree = tree;
		this.passedMatchers = passedMatchers;
		this.passedAssigners = passedAssigners;
	}

	/**
	 * Go through the tree and for each scope, attach a map of variables to their types existing in that scope.
	 * @return
	 */
	abstract Map<ParseTreeNode, Set<Type>> inferTypes() throws TypeInferrorException;
}
