package logic.type;

import logic.Nameable;
import logic.model.universe.Universe;
import reading.parsing.ParseTree;
import reading.parsing.ParseTreeNode;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import util.TreeUtils;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.OPEN_BRACKET;

/**
 * @author Steven Weston
 */
public class LogicTypeInferror<T extends Nameable> implements TypeInferror {

	private final Universe<T, ?, ?> universe;

	public LogicTypeInferror(Universe<T, ?, ?> universe) {
		this.universe = universe;
	}

	@Override
	public Map<ParseTreeNode, Set<Type>> inferTypes(
			ParseTree tree,
			Map<ParseTreeNode, ? extends Collection<? extends TypeMatcher>> passedMatchers,
			Map<ParseTreeNode, ? extends Collection<? extends VariableAssignerFactory>> passedAssigners
	) throws TypeInferrorException {

		throw new NotImplementedException();
	}

	Map<ParseTreeNode, Set<Type>> inferTypesFromMatchers(
			ParseTree tree,
			Map<ParseTreeNode, ? extends Collection<? extends TypeMatcher>> passedMatchers
	) {

		Map<ParseTreeNode, Set<Type>> output = new HashMap<>();

		TreeUtils.recurse(
				tree.getFirstNode(),
				this::shouldWalkDownAt,
				(parent, children) -> {
					Collection<? extends TypeMatcher> matchers = passedMatchers.get(parent);
					for (TypeMatcher matcher : matchers) {
						output.put(parent, matcher.getPotentialReturnTypes(parent, children));
					}
				}
		);

		return output;
	}


	private boolean shouldWalkDownAt(ParseTreeNode n) {
		return n.getToken().isOfType(OPEN_BRACKET);
	}
}
