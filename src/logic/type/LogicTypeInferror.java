package logic.type;

import logic.Nameable;
import logic.model.universe.Universe;
import reading.parsing.ParseTree;
import reading.parsing.ParseTreeNode;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import util.MapUtils;
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
public class LogicTypeInferror<T extends Nameable> extends TypeInferror<T> {

	final Map<ParseTreeNode, Set<Type>> typeMap = new HashMap<>();

	protected LogicTypeInferror(Universe<T, ?, ?> universe, ParseTree tree) {
		super(universe, tree);
	}

	@Override
	public Map<ParseTreeNode, Set<Type>> inferTypes(
			Map<ParseTreeNode, ? extends Collection<? extends TypeMatcher>> passedMatchers,
			Map<ParseTreeNode, ? extends Collection<? extends VariableAssignerFactory>> passedAssigners
	) throws TypeInferrorException {

		inferTypesFromMatchers(passedMatchers);

		guessTypesOfFreeVariables(passedMatchers);

		throw new NotImplementedException();
	}

	void inferTypesFromMatchers(Map<ParseTreeNode, ? extends Collection<? extends TypeMatcher>> passedMatchers) {

		TreeUtils.recurse(
				tree.getFirstNode(),
				this::shouldWalkDownAt,
				(parent, children) -> {
					Collection<? extends TypeMatcher> matchers = passedMatchers.get(parent);
					for (TypeMatcher matcher : matchers) {
						MapUtils.updateSetBasedMap(
								typeMap,
								parent,
								matcher.getPotentialReturnTypes(parent, children));
					}
				}
		);
	}

	private void guessTypesOfFreeVariables(Map<ParseTreeNode,? extends Collection<? extends TypeMatcher>> passedMatchers) {

	}


	private boolean shouldWalkDownAt(ParseTreeNode n) {
		return n.getToken().isOfType(OPEN_BRACKET);
	}
}
