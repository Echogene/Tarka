package logic.type;

import logic.Nameable;
import logic.model.universe.Universe;
import reading.parsing.ParseTree;
import reading.parsing.ParseTreeNode;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import util.CollectionUtils;
import util.MapUtils;
import util.TreeUtils;

import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.*;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.OPEN_BRACKET;
import static util.TreeUtils.surroundWithParentNodes;

/**
 * @author Steven Weston
 */
public class LogicTypeInferror<T extends Nameable> extends TypeInferror<T> {

	final Map<ParseTreeNode, Set<Type>> typeMap = new HashMap<>();
	final Map<ParseTreeNode, List<String>> variablesAssigned = new HashMap<>();
	final Map<ParseTreeNode, Map<String, Set<Type>>> assignedVariableTypes = new HashMap<>();

	protected LogicTypeInferror(
			Universe<T, ?, ?> universe,
			ParseTree tree,
			Map<ParseTreeNode, ? extends Collection<? extends TypeMatcher>> passedMatchers,
			Map<ParseTreeNode, ? extends Collection<? extends VariableAssignerFactory>> passedAssigners
	) {
		super(universe, tree, passedMatchers, passedAssigners);
	}

	@Override
	public Map<ParseTreeNode, Set<Type>> inferTypes() throws TypeInferrorException {

		inferTypesFromMatchers();

		findVariableAssignments();


		throw new NotImplementedException();
	}

	void findVariableAssignments() throws TypeInferrorException {

		for (Map.Entry<ParseTreeNode, ? extends Collection<? extends VariableAssignerFactory>> entry
				: passedAssigners.entrySet()) {

			ParseTreeNode parent = entry.getKey();
			Collection<? extends VariableAssignerFactory> assigners = entry.getValue();
			List<ParseTreeNode> surroundedChildren = surroundWithParentNodes(parent.getChildren());
			variablesAssigned.put(
					parent,
					CollectionUtils.extractUnique(
							assigners,
							(assigner) -> assigner.getVariablesToAssign(surroundedChildren),
							this::throwIfVariablesDiffer
					)
			);
		}
	}

	private void throwIfVariablesDiffer(
			List<String> expectedVariables,
			List<String> variables,
			VariableAssignerFactory assigner
	) throws TypeInferrorException {

		throw new TypeInferrorException(
				MessageFormat.format(
						"Ambiguous variables for assigner {0}.  Expected {1} but got {2}",
						assigner,
						expectedVariables,
						variables
				)
		);
	}

	void inferTypesFromMatchers() {

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

	private boolean shouldWalkDownAt(ParseTreeNode n) {
		return n.getToken().isOfType(OPEN_BRACKET);
	}


	private List<String> getStringsFromNodes(List<ParseTreeNode> variables) {

		List<String> variableSymbols = new ArrayList<>();
		for (ParseTreeNode variable : variables) {
			variableSymbols.add(variable.getToken().getValue());
		}
		return variableSymbols;
	}
}
