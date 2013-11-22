package logic.type;

import javafx.util.Pair;
import logic.Nameable;
import logic.model.universe.Universe;
import logic.type.map.Extractor;
import logic.type.map.MapWithErrors;
import logic.type.map.Testor;
import reading.parsing.ParseTree;
import reading.parsing.ParseTreeNode;
import util.StringUtils;

import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.*;

import static java.text.MessageFormat.format;
import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.OPEN_BRACKET;
import static util.ClassUtils.safeSimpleName;
import static util.CollectionUtils.first;
import static util.CollectionUtils.printMap;
import static util.TreeUtils.surroundWithParentNodes;

/**
 * @author Steven Weston
 */
public class SimpleLogicTypeInferror<T extends Nameable> implements TypeInferror {

	private final Universe<T> universe;

	private HashMap<ParseTreeNode,Type> typeMap;
	private Map<ParseTreeNode, ? extends Collection<? extends TypeMatcher>> passedMatchers;
	private Map<ParseTreeNode, ? extends Collection<? extends VariableAssignerFactory>> passedAssigners;

	public SimpleLogicTypeInferror(Universe<T> universe) {
		this.universe = universe;
	}

	@Override
	public synchronized Map<ParseTreeNode, Type> inferTypes(
			ParseTree tree,
			Map<ParseTreeNode, ? extends Collection<? extends TypeMatcher>> passedMatchers,
			Map<ParseTreeNode, ? extends Collection<? extends VariableAssignerFactory>> passedAssigners
	) throws TypeInferrorException {
		if (tree == null) {
			return null;
		}
		typeMap = new HashMap<>();
		this.passedMatchers = passedMatchers;
		this.passedAssigners = passedAssigners;
		ParseTreeNode firstNode = tree.getFirstNode();
		Type type = inferType(firstNode.getChildren(), new HashMap<>());
		typeMap.put(firstNode, type);
		return typeMap;
	}

	private Type inferType(
			final List<ParseTreeNode> nodes,
			final Map<String, Type> variablesTypes
	) throws TypeInferrorException {

		final MapWithErrors<ParseTreeNode, Type> functionTypes
				= inferChildTypes(nodes, variablesTypes);

		final MapWithErrors<VariableAssignerFactory, Map<String, Type>> variableAssignments
				= assignVariableTypes(surroundWithParentNodes(nodes), functionTypes);

		final MapWithErrors<ParseTreeNode, Type> functionTypesAfterAssignment
				= inferChildTypesAfterVariableAssignment(nodes, variablesTypes, functionTypes, variableAssignments);

		final MapWithErrors<TypeMatcher, Type> matchedTypes
				= matchTypes(surroundWithParentNodes(nodes), typeMap, functionTypesAfterAssignment);

		return matchedTypes.getUniquePassedValue();
	}

	private MapWithErrors<ParseTreeNode, Type> inferChildTypes(
			final List<ParseTreeNode> nodes,
			final Map<String, Type> variableTypes
	) throws TypeInferrorException {

		MapWithErrors<ParseTreeNode, Type> functionTypesAfterAssignment;
		//noinspection unchecked
		functionTypesAfterAssignment = new MapWithErrors<>(
				nodes,
				new Pair<Testor<ParseTreeNode>, Extractor<ParseTreeNode, Type>>(
						this::shouldWalkDownAt,
						(ParseTreeNode node) -> inferType(node.getChildren(), variableTypes)
				),
				new Pair<Testor<ParseTreeNode>, Extractor<ParseTreeNode, Type>>(
						(ParseTreeNode node) -> variableTypes.containsKey(node.getToken().getValue()),
						(ParseTreeNode node) -> variableTypes.get(node.getToken().getValue())
				),
				new Pair<Testor<ParseTreeNode>, Extractor<ParseTreeNode, Type>>(
						(ParseTreeNode node) -> universe.contains(node.getToken().getValue()),
						(ParseTreeNode node) -> universe.getTypeOfElement(node.getToken().getValue())
				)
		);
		return functionTypesAfterAssignment;
	}

	private MapWithErrors<VariableAssignerFactory, Map<String, Type>> assignVariableTypes(
			final List<ParseTreeNode> surroundedNodes,
			final MapWithErrors<ParseTreeNode, Type> functionTypes
	) throws TypeInferrorException {

		final MapWithErrors<
				VariableAssignerFactory,
				Map<String, Type>
		> variableAssignments = new MapWithErrors<>(
				passedAssigners.get(first(surroundedNodes)),
				(VariableAssignerFactory factory) -> factory.assignVariableTypes(surroundedNodes, functionTypes)
		);
		if (variableAssignments.hasAmbiguousPasses()) {
			throw new TypeInferrorException(
					MessageFormat.format(
							"Ambiguous assignment for {0}.",
							surroundedNodes.toString()
					)
			);
		}
		return variableAssignments;
	}

	private MapWithErrors<ParseTreeNode, Type> inferChildTypesAfterVariableAssignment(
			final List<ParseTreeNode> nodes,
			final Map<String, Type> variablesTypes,
			final MapWithErrors<ParseTreeNode, Type> functionTypes,
			final MapWithErrors<VariableAssignerFactory, Map<String, Type>> variableAssignments
	) throws TypeInferrorException {

		final MapWithErrors<ParseTreeNode, Type> functionTypesAfterAssignment;
		if (variableAssignments.hasTotallyUniquePass()) {
			Map<String, Type> assignedVariableTypes = new HashMap<>(variableAssignments.getUniquePassedValue());
			VariableAssignerFactory factory = variableAssignments.getUniquePassedKey();
			assignedVariableTypes.putAll(variablesTypes);
			List<ParseTreeNode> childrenNodes = new ArrayList<>();
			for (ParseTreeNode node : nodes) {
				if (factory.shouldWalkDownAt(node, surroundWithParentNodes(nodes))) {
					childrenNodes.add(node);
				}
			}
			functionTypesAfterAssignment = inferChildTypes(childrenNodes, assignedVariableTypes);
		} else {
			functionTypesAfterAssignment = functionTypes;
		}

		if (!functionTypesAfterAssignment.allPassed()) {
			throw new TypeInferrorException(
					"Not all types could be determined from "
							+ nodes.toString()
							+ " because:\n"
							+ StringUtils.addCharacterAfterEveryNewline(functionTypesAfterAssignment.concatenateErrorMessages(), '\t')
			);
		}
		return functionTypesAfterAssignment;
	}

	private MapWithErrors<TypeMatcher, Type> matchTypes(
			final List<ParseTreeNode> surroundedNodes,
			Map<ParseTreeNode, Type> typeMap,
			final MapWithErrors<ParseTreeNode, Type> functionTypesAfterAssignment
	) throws TypeInferrorException {

		final MapWithErrors<TypeMatcher, Type> matchedTypes = new MapWithErrors<>(
				passedMatchers.get(surroundedNodes.get(0)),
				(TypeMatcher matcher) -> matcher.getType(surroundedNodes, functionTypesAfterAssignment)
		);
		if (matchedTypes.allFailed()) {
			throw new TypeInferrorException("There were no types matched with " + surroundedNodes.toString() + ".");
		}
		if (matchedTypes.hasTotallyAmbiguousPasses()) {
			throw new TypeInferrorException(
					format(
							"{0} resolved to more than one type: {1}.",
							surroundedNodes.toString(),
							printMap(
									matchedTypes.getPassedValues(),
									type -> safeSimpleName((Class) type)
							)
					)
			);
		}

		typeMap.putAll(functionTypesAfterAssignment.getPassedValues());
		return matchedTypes;
	}

	private boolean shouldWalkDownAt(ParseTreeNode n) {
		return n.getToken().isOfType(OPEN_BRACKET);
	}
}
