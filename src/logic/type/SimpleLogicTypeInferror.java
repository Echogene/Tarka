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
import util.TreeUtils;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static logic.factory.SimpleLogicLexerToken.SimpleLogicLexerTokenType.OPEN_BRACKET;

/**
 * @author Steven Weston
 */
public class SimpleLogicTypeInferror<T extends Nameable> implements TypeInferror {

	private final Collection<TypeMatcher> matchers;

	private final Collection<VariableAssignerFactory> factories;

	private final Universe<T> universe;

	public SimpleLogicTypeInferror(Collection<TypeMatcher> matchers, Collection<VariableAssignerFactory> factories, Universe<T> universe) {
		this.matchers = matchers;
		this.factories = factories;
		this.universe = universe;
	}

	@Override
	public Map<ParseTreeNode, Type> inferTypes(ParseTree tree) throws TypeInferrorException {
		if (tree == null) {
			return null;
		}
		HashMap<ParseTreeNode, Type> typeMap = new HashMap<>();
		inferType(tree.getNodes().get(0).getChildren(), new HashMap<>(), typeMap);
		return typeMap;
	}

	private Type inferType(
			final List<ParseTreeNode> nodes,
			final Map<String, Type> variablesTypes,
			Map<ParseTreeNode, Type> typeMap
	) throws TypeInferrorException {

		final MapWithErrors<ParseTreeNode, Type> functionTypes
				= inferChildTypes(nodes, variablesTypes, typeMap);

		final MapWithErrors<VariableAssignerFactory, Map<String, Type>> variableAssignments
				= assignVariableTypes(nodes, functionTypes);

		final MapWithErrors<ParseTreeNode, Type> functionTypesAfterAssignment
				= inferChildTypesAfterVariableAssignment(nodes, variablesTypes, typeMap, functionTypes, variableAssignments);

		final MapWithErrors<TypeMatcher, Type> matchedTypes
				= matchTypes(nodes, typeMap, functionTypesAfterAssignment);

		return matchedTypes.getUniquePassedValue();
	}

	private MapWithErrors<ParseTreeNode, Type> inferChildTypes(
			final List<ParseTreeNode> nodes,
			final Map<String, Type> variableTypes,
			Map<ParseTreeNode, Type> typeMap
	) throws TypeInferrorException {

		MapWithErrors<ParseTreeNode, Type> functionTypesAfterAssignment;
		//noinspection unchecked
		functionTypesAfterAssignment = new MapWithErrors<>(
				nodes,
				new Pair<Testor<ParseTreeNode>, Extractor<ParseTreeNode, Type>>(
						this::shouldWalkDownAt,
						(ParseTreeNode node) -> inferType(node.getChildren(), variableTypes, typeMap)
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
			final List<ParseTreeNode> nodes,
			final MapWithErrors<ParseTreeNode, Type> functionTypes
	) throws TypeInferrorException {

		final MapWithErrors<
				VariableAssignerFactory,
				Map<String, Type>
				> variableAssignments = new MapWithErrors<>(
				factories,
				(VariableAssignerFactory factory) -> factory.assignVariableTypes(TreeUtils.surroundWithParentNodes(nodes), functionTypes)
		);
		if (variableAssignments.hasAmbiguousPasses()) {
			throw new TypeInferrorException("Ambiguous assignment for " + nodes.toString() + ".");
		}
		return variableAssignments;
	}

	private MapWithErrors<ParseTreeNode, Type> inferChildTypesAfterVariableAssignment(
			final List<ParseTreeNode> nodes,
			final Map<String, Type> variablesTypes,
			Map<ParseTreeNode, Type> typeMap,
			final MapWithErrors<ParseTreeNode, Type> functionTypes,
			final MapWithErrors<VariableAssignerFactory, Map<String, Type>> variableAssignments
	) throws TypeInferrorException {

		final MapWithErrors<ParseTreeNode, Type> functionTypesAfterAssignment;
		if (variableAssignments.hasUniquePass()) {
			Map<String, Type> assignedVariableTypes = variableAssignments.getUniquePassedValue();
			assignedVariableTypes.putAll(variablesTypes);
			functionTypesAfterAssignment = inferChildTypes(nodes, assignedVariableTypes, typeMap);
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
			final List<ParseTreeNode> nodes,
			Map<ParseTreeNode, Type> typeMap,
			final MapWithErrors<ParseTreeNode, Type> functionTypesAfterAssignment
	) throws TypeInferrorException {

		final MapWithErrors<TypeMatcher, Type> matchedTypes = new MapWithErrors<>(
				matchers,
				(TypeMatcher matcher) -> matcher.getType(TreeUtils.surroundWithParentNodes(nodes), functionTypesAfterAssignment)
		);
		if (matchedTypes.allFailed()) {
			throw new TypeInferrorException("There were no types matched with " + nodes.toString() + ".");
		}
		if (matchedTypes.hasAmbiguousPasses()) {
			throw new TypeInferrorException(nodes.toString() + " resolved to more than one type.");
		}

		typeMap.putAll(functionTypesAfterAssignment.getPassedValues());
		return matchedTypes;
	}

	private boolean shouldWalkDownAt(ParseTreeNode n) {
		return n.getToken().isOfType(OPEN_BRACKET);
	}
}
