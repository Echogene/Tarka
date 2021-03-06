package logic.oldtype;

import javafx.util.Pair;
import logic.Nameable;
import logic.model.universe.Universe;
import logic.oldtype.map.MapWithErrors;
import logic.oldtype.map.Testor;
import ophelia.function.ExceptionalFunction;
import ophelia.util.MapUtils;
import ophelia.util.StringUtils;
import reading.parsing.ParseTree;
import reading.parsing.ParseTreeNode;

import java.lang.reflect.Type;
import java.util.*;

import static java.text.MessageFormat.format;
import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.OPEN_BRACKET;
import static ophelia.util.ListUtils.first;
import static util.TreeUtils.surroundWithParentNodes;

/**
 * @author Steven Weston
 */
public class SimpleLogicTypeInferror<T extends Nameable> extends TypeInferror<T> {

	private Map<ParseTreeNode, Set<Type>> typeMap;

	/**
	 * For each node, collect a map of free variables to their possible types.
	 */
	private Map<ParseTreeNode, Map<String, Set<Type>>> freeVariableMap;
	private Map<ParseTreeNode, Map<String, Set<Type>>> boundVariableMap;

	public SimpleLogicTypeInferror(
			Universe<T, ?, ?> universe,
			ParseTree tree,
			Map<ParseTreeNode, ? extends Collection<? extends TypeMatcher>> passedMatchers,
			Map<ParseTreeNode, ? extends Collection<? extends VariableAssignerFactory>> passedAssigners
	) {
		super(universe, tree, passedMatchers, passedAssigners);
	}

	@Override
	public synchronized Map<ParseTreeNode, Set<Type>> inferTypes(
	) throws TypeInferrorException {

		if (tree == null) {
			return null;
		}
		freeVariableMap = new HashMap<>();
		boundVariableMap = new HashMap<>();

		typeMap = new HashMap<>(tree.getNodes().size());
		ParseTreeNode firstNode = tree.getFirstNode();
		Set<Type> type = inferType(firstNode.getChildren(), new HashMap<>());
		typeMap.put(firstNode, type);
		return typeMap;
	}

	private Set<Type> inferType(
			final List<ParseTreeNode> nodes,
			final Map<String, Set<Type>> variablesTypes
	) throws TypeInferrorException {

		// Recurse
		final MapWithErrors<ParseTreeNode, Set<Type>> functionTypes
				= inferChildTypes(nodes, variablesTypes);

		List<ParseTreeNode> surroundedNodes = surroundWithParentNodes(nodes);

		Map<String, Set<Type>> freeVariables = guessFreeVariableTypes(surroundedNodes, functionTypes);
		freeVariables(freeVariables, surroundedNodes);
		collectDescendantFreeVariables(freeVariables, surroundedNodes);

		final MapWithErrors<VariableAssignerFactory, Map<String, Set<Type>>> variableAssignments
				= assignVariableTypes(surroundedNodes, functionTypes, freeVariables);

		bindVariables(variableAssignments, surroundedNodes);

		final MapWithErrors<ParseTreeNode, Set<Type>> functionTypesAfterAssignment
				= inferChildTypesAfterVariableAssignment(nodes, variablesTypes, functionTypes, variableAssignments);

		final MapWithErrors<TypeMatcher, Set<Type>> matchedTypes
				= matchTypes(surroundedNodes, functionTypesAfterAssignment);

		return matchedTypes.getUniquePassedValue();
	}

	private void collectDescendantFreeVariables(Map<String, Set<Type>> freeVariables, List<ParseTreeNode> surroundedNodes) {

		first(surroundedNodes).getDescendants()
				.stream()
				.filter(freeVariableMap::containsKey)
				.forEach(
						descendant -> MapUtils.overlay(
								freeVariables,
								freeVariableMap.get(descendant)
						)
				);
	}

	private void freeVariables(Map<String, Set<Type>> freeVariables, List<ParseTreeNode> surroundedNodes) {
		freeVariableMap.put(first(surroundedNodes), freeVariables);
	}

	private void bindVariables(
			MapWithErrors<VariableAssignerFactory, Map<String, Set<Type>>> variableAssignments,
			List<ParseTreeNode> surroundedNodes
	) throws TypeInferrorException {

		if (variableAssignments.allFailed()) {
			return;
		}

		if (variableAssignments.hasTotallyAmbiguousPasses()) {
			throw new TypeInferrorException(
					format(
							"There were ambiguous bound variables for {0}.",
							surroundedNodes.toString()
					)
			);
		}
		Map<String, Set<Type>> boundVariables = variableAssignments.getUniquePassedValue();
		ParseTreeNode mother = first(surroundedNodes);
		bindVariablesToNode(boundVariables, mother);
		for (ParseTreeNode descendant : mother.getDescendants()) {
			bindVariablesToNode(boundVariables, descendant);
		}
	}

	private void bindVariablesToNode(Map<String, Set<Type>> boundVariables, ParseTreeNode node) {

		if (!freeVariableMap.containsKey(node)) {
			return;
		}
		for (Map.Entry<String, Set<Type>> binding : boundVariables.entrySet()) {
			freeVariableMap.get(node).remove(binding.getKey());
		}
		if (boundVariableMap.containsKey(node)) {
			boundVariableMap.get(node).putAll(boundVariables);
		} else {
			boundVariableMap.put(node, boundVariables);
		}
	}

	private Map<String, Set<Type>> guessFreeVariableTypes(
			List<ParseTreeNode> nodes,
			MapWithErrors<ParseTreeNode,
			Set<Type>> functionTypes
	) throws TypeInferrorException {

		MapWithErrors<TypeMatcher, Map<String, Set<Type>>> map = new MapWithErrors<>(
				passedMatchers.get(first(nodes)),
				matcher -> {
					Map<String, Set<Type>> output = new HashMap<>();
					List<ParseTreeNode> variables = matcher.getVariables(nodes);
					for (ParseTreeNode variable : variables) {
						if (functionTypes.getPassedValues().containsKey(variable)) {
							continue;
						}
						output.put(
								variable.getValue(),
								new HashSet<>(matcher.guessTypes(variable, nodes))
						);
					}
					return output;
				}
		);

		if (map.hasUniquePass()) {
			return map.getUniquePassedValue();
		} else {
			// Return the intersection of the guesses
			return MapUtils.intersect(map.getPassedValues().values());
		}
	}

	private MapWithErrors<ParseTreeNode, Set<Type>> inferChildTypes(
			final List<ParseTreeNode> nodes,
			final Map<String, Set<Type>> variableTypes
	) throws TypeInferrorException {

		MapWithErrors<ParseTreeNode, Set<Type>> functionTypes;
		//noinspection unchecked
		functionTypes = new MapWithErrors<ParseTreeNode, Set<Type>>(
				nodes,
				new Pair<Testor<ParseTreeNode>, ExceptionalFunction<ParseTreeNode, Set<Type>, Exception>>(
						this::shouldWalkDownAt,
						(ParseTreeNode node) -> inferType(node.getChildren(), variableTypes)
				),
				new Pair<Testor<ParseTreeNode>, ExceptionalFunction<ParseTreeNode, Set<Type>, Exception>>(
						(ParseTreeNode node) -> variableTypes.containsKey(node.getValue()),
						(ParseTreeNode node) -> variableTypes.get(node.getValue())
				),
				new Pair<Testor<ParseTreeNode>, ExceptionalFunction<ParseTreeNode, Set<Type>, Exception>>(
						(ParseTreeNode node) -> universe.contains(node.getValue()),
						(ParseTreeNode node) -> Collections.singleton(universe.getTypeOfElement(node.getValue()))
				)
		);
		return functionTypes;
	}

	private MapWithErrors<VariableAssignerFactory, Map<String, Set<Type>>> assignVariableTypes(
			final List<ParseTreeNode> surroundedNodes,
			final MapWithErrors<ParseTreeNode, Set<Type>> functionTypes,
			final Map<String, Set<Type>> freeVariables
	) throws TypeInferrorException {

		final MapWithErrors<
				VariableAssignerFactory,
				Map<String, Set<Type>>
		> variableAssignments = new MapWithErrors<>(
				passedAssigners.get(first(surroundedNodes)),
				(VariableAssignerFactory factory)
						-> factory.assignVariableTypes(surroundedNodes, functionTypes, freeVariables)
		);
		if (variableAssignments.hasAmbiguousPasses()) {
			throw new TypeInferrorException(
					format(
							"Ambiguous assignment for {0}.  The following factories passed: {1}",
							surroundedNodes,
							variableAssignments.getPassedValues().keySet()
					)
			);
		}
		return variableAssignments;
	}

	private MapWithErrors<ParseTreeNode, Set<Type>> inferChildTypesAfterVariableAssignment(
			final List<ParseTreeNode> nodes,
			final Map<String, Set<Type>> variablesTypes,
			final MapWithErrors<ParseTreeNode, Set<Type>> functionTypes,
			final MapWithErrors<VariableAssignerFactory, Map<String, Set<Type>>> variableAssignments
	) throws TypeInferrorException {

		final MapWithErrors<ParseTreeNode, Set<Type>> functionTypesAfterAssignment;
		if (variableAssignments.hasTotallyUniquePass()) {
			Map<String, Set<Type>> assignedVariableTypes = new HashMap<>(variableAssignments.getUniquePassedValue());
			VariableAssignerFactory factory = variableAssignments.getUniquePassedKey();
			MapUtils.overlay(assignedVariableTypes, variablesTypes);
			List<ParseTreeNode> childrenNodes = new ArrayList<>();
			for (ParseTreeNode node : nodes) {
				List<ParseTreeNode> surroundedNodes = surroundWithParentNodes(nodes);
				if (factory.shouldWalkDownAt(node, surroundedNodes)) {
					childrenNodes.add(node);
				}
			}
			functionTypesAfterAssignment = inferChildTypes(childrenNodes, assignedVariableTypes);
		} else {
			functionTypesAfterAssignment = functionTypes;
		}

		if (!functionTypesAfterAssignment.allPassed()) {
			throw new TypeInferrorException(
					format(
							"Not all types could be determined from {0} because:\n{1}",
							nodes.toString(),
							StringUtils.addCharacterAfterEveryNewline(
									functionTypesAfterAssignment.concatenateErrorMessages(), '\t'
							)
					)
			);
		}
		return functionTypesAfterAssignment;
	}

	private MapWithErrors<TypeMatcher, Set<Type>> matchTypes(
			final List<ParseTreeNode> surroundedNodes,
			final MapWithErrors<ParseTreeNode, Set<Type>> functionTypesAfterAssignment
	) throws TypeInferrorException {

		final MapWithErrors<TypeMatcher, Set<Type>> matchedTypes = new MapWithErrors<>(
				passedMatchers.get(surroundedNodes.get(0)),
				(TypeMatcher matcher) -> matcher.getTypes(surroundedNodes, functionTypesAfterAssignment)
		);
		if (matchedTypes.allFailed()) {
			throw new TypeInferrorException("There were no types matched with " + surroundedNodes.toString() + ".");
		}
//		if (matchedTypes.hasTotallyAmbiguousPasses()) {
//			throw new TypeInferrorException(
//					format(
//							"{0} resolved to more than one type: {1}.",
//							surroundedNodes.toString(),
//							mapToString(
//									matchedTypes.getPassedValues(),
//									types -> CollectionUtils.simpleNames(types)
//							)
//					)
//			);
//		}
		// todo: handle ambiguity here?

		typeMap.putAll(functionTypesAfterAssignment.getPassedValues());
		return matchedTypes;
	}

	private boolean shouldWalkDownAt(ParseTreeNode n) {
		return n.getToken().isOfType(OPEN_BRACKET);
	}

	public Map<ParseTreeNode, Map<String, Set<Type>>> getBoundVariableMap() {
		return boundVariableMap;
	}
}
