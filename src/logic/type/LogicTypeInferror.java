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

		guessFreeVariableTypes();

		throw new NotImplementedException();
	}

	void findVariableAssignments() throws TypeInferrorException {

		TreeUtils.recurse(
				tree.getFirstNode(),
				this::shouldWalkDownAt,
				(parent, children) -> {
					List<ParseTreeNode> surroundedChildren = surroundWithParentNodes(children);

					Collection<? extends VariableAssignerFactory> assigners = passedAssigners.get(parent);
					variablesAssigned.put(
							parent,
							CollectionUtils.extractOrThrowIfDifferent(
									assigners,
									(assigner) -> assigner.getVariablesToAssign(surroundedChildren),
									(expectedVariables, variables, assigner) -> {
										throw new TypeInferrorException(
												MessageFormat.format(
														"Ambiguous variables for assigner {0}.  Expected {1} but got {2}",
														assigner,
														expectedVariables,
														variables
												)
										);
									}
							)
					);
				}
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

	void guessFreeVariableTypes() throws TypeInferrorException {

		TreeUtils.recurse(
				tree.getFirstNode(),
				this::shouldWalkDownAt,
				this::guessTypesOfFreeVariables
		);
	}

	private void guessTypesOfFreeVariables(ParseTreeNode parent, List<ParseTreeNode> children)
			throws TypeInferrorException {

		List<ParseTreeNode> surroundedChildren = surroundWithParentNodes(children);

		Collection<? extends TypeMatcher> matchers = passedMatchers.get(parent);
		List<ParseTreeNode> variables = Collections.emptyList();
		for (TypeMatcher matcher : matchers) {
			List<ParseTreeNode> matcherVariables = matcher.getVariables(surroundedChildren);
			if (!variables.isEmpty() && !variables.equals(matcherVariables)) {
				throw new TypeInferrorException(
						MessageFormat.format(
								"Ambiguous free variables for {0} from matchers {1}.",
								surroundedChildren,
								matchers
						)
				);
			}
			variables = matcherVariables;

			Map<ParseTreeNode, Set<Type>> matcherVariableTypes = matcher.guessVariableTypes(
					surroundedChildren
			);
			MapUtils.overlay(typeMap, matcherVariableTypes);
		}
		List<String> variableSymbols = getStringsFromNodes(variables);
		overlayFreeVariableTypesOfDescendants(parent, variableSymbols);
	}

	private void overlayFreeVariableTypesOfDescendants(
			ParseTreeNode parent,
			List<String> variables
	) {

		Map<String, Set<ParseTreeNode>> variableToNodesMap = new HashMap<>(variables.size());
		Map<String, Set<Type>> variableToTypesMap = new HashMap<>(variables.size());
		TreeUtils.recurse(
				parent,
				this::shouldWalkDownAt,
				(parent2, children2) -> {
					for (ParseTreeNode child : children2) {
						String childSymbol = child.getToken().getValue();
						if (!variables.contains(childSymbol)) {
							continue;
						}
						MapUtils.overlay(variableToTypesMap, childSymbol, typeMap.get(child));
						Set<Type> types = variableToTypesMap.get(childSymbol);
						MapUtils.updateSetBasedMap(variableToNodesMap, childSymbol, child);
						for (ParseTreeNode otherVariableNode : variableToNodesMap.get(childSymbol)) {
							MapUtils.overlay(typeMap, otherVariableNode, types);
						}
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
