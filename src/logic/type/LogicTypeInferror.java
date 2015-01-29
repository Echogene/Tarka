package logic.type;

import logic.Nameable;
import logic.model.universe.Universe;
import reading.parsing.ParseTree;
import reading.parsing.ParseTreeNode;
import util.CollectionUtils;
import util.MapUtils;

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
	final Set<ParseTreeNode> variables = new HashSet<>();

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

		findVariables();

		findVariableAssignments();

		inferTypesOfVariablesFromUniverse();

		inferTypesOfVariablesFromMatcherArguments();
		
		return typeMap;
	}

	private void inferTypesOfVariablesFromMatcherArguments() {

		for (ParseTreeNode variable : variables) {
			ParseTreeNode mother = variable.getMother();
			Collection<? extends TypeMatcher> matchers = passedMatchers.get(mother);

			for (TypeMatcher matcher : matchers) {
				MapUtils.overlay(
						typeMap,
						variable,
						matcher.guessTypes(variable, mother.getChildren()));
			}
		}
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
							this::throwIfVariablesAssignedDiffer
					)
			);
		}
	}

	private void throwIfVariablesAssignedDiffer(
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

	void findVariables() throws TypeInferrorException {

		for (Map.Entry<ParseTreeNode, ? extends Collection<? extends TypeMatcher>> entry : passedMatchers.entrySet()) {

			ParseTreeNode parent = entry.getKey();
			Collection<? extends TypeMatcher> assigners = entry.getValue();

			List<ParseTreeNode> surroundedChildren = surroundWithParentNodes(parent.getChildren());
			variables.addAll(
					CollectionUtils.extractUnique(
							assigners,
							(assigner) -> assigner.getVariables(surroundedChildren),
							this::throwIfVariablesDiffer
					)
			);
		}
	}

	private void throwIfVariablesDiffer(
			List<ParseTreeNode> expectedVariables,
			List<ParseTreeNode> variables,
			TypeMatcher matcher
	) throws TypeInferrorException {

		throw new TypeInferrorException(
				MessageFormat.format(
						"Ambiguous variables for matcher {0}.  Expected {1} but got {2}",
						matcher,
						expectedVariables,
						variables
				)
		);
	}

	void inferTypesFromMatchers() {

		for (Map.Entry<ParseTreeNode, ? extends Collection<? extends TypeMatcher>> entry : passedMatchers.entrySet()) {
			ParseTreeNode parent = entry.getKey();
			Collection<? extends TypeMatcher> matchers = entry.getValue();
			List<ParseTreeNode> surroundedChildren = surroundWithParentNodes(parent.getChildren());

			for (TypeMatcher matcher : matchers) {
				MapUtils.updateSetBasedMap(
						typeMap,
						parent,
						matcher.getPotentialReturnTypes(surroundedChildren));
			}
		}
	}

	void inferTypesOfVariablesFromUniverse() {

		for (ParseTreeNode variable : variables) {
			String value = variable.getValue();
			if (universe.contains(value)) {
				MapUtils.updateSetBasedMap(typeMap, variable, universe.getTypeOfElement(value));
			}
		}
	}

	private boolean shouldWalkDownAt(ParseTreeNode n) {
		return n.getToken().isOfType(OPEN_BRACKET);
	}


	private List<String> getStringsFromNodes(List<ParseTreeNode> variables) {

		List<String> variableSymbols = new ArrayList<>();
		for (ParseTreeNode variable : variables) {
			variableSymbols.add(variable.getValue());
		}
		return variableSymbols;
	}
}
