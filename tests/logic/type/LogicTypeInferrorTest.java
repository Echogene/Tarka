package logic.type;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.function.assignment.AssignmentFactory;
import logic.function.evaluable.predicate.equality.EqualityPredicateFactory;
import logic.function.evaluable.predicate.membership.MembershipPredicateFactory;
import logic.function.evaluable.statements.binary.BinaryStatementFactory;
import logic.function.identity.IdentityFunctionFactory;
import logic.function.voidfunction.definition.constant.DefinitionFactory;
import org.junit.Test;
import reading.parsing.ParseTree;
import reading.parsing.ParseTreeNode;
import util.CollectionUtils;

import java.lang.reflect.Type;
import java.util.*;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class LogicTypeInferrorTest
		extends AbstractTypeInferrorTest<TestClass, TestClassUniverse, LogicTypeInferror<TestClass>> {

	public static final Set<Type> BOOLEAN_ONLY = Collections.<Type>singleton(Boolean.class);
	public static final Set<Type> TESTCLASS_ONLY = Collections.<Type>singleton(TestClass.class);
	public static final Set<Type> SET_ONLY = Collections.<Type>singleton(logic.set.Set.class);
	public static final Set<Type> NON_VOID_TYPES = CollectionUtils.<Type>createSet(
			Boolean.class,
			logic.set.Set.class,
			TestClass.class
	);

	private static final BinaryStatementFactory<TestClass> BINARY_STATEMENT_FACTORY
			= new BinaryStatementFactory<>(TestClass.class);
	private static final DefinitionFactory<TestClass> DEFINITION_FACTORY
			= new DefinitionFactory<>(TestClass.class);
	private static final IdentityFunctionFactory<TestClass> IDENTITY_FUNCTION_FACTORY
			= new IdentityFunctionFactory<>(TestClass.class);
	private static final EqualityPredicateFactory<TestClass> EQUALITY_PREDICATE_FACTORY
			= new EqualityPredicateFactory<>(TestClass.class);
	private static final MembershipPredicateFactory<TestClass> MEMBERSHIP_PREDICATE_FACTORY
			= new MembershipPredicateFactory<>(TestClass.class);
	private static final AssignmentFactory<TestClass> ASSIGNMENT_FACTORY
			= new AssignmentFactory<>(TestClass.class);

	public LogicTypeInferrorTest() {
		super(new TestClassUniverse());
	}

	@Test
	public void test_single_type_inferred() throws Exception {

		ParseTree tree = parse("(a ∨ b)");
		List<TypeMatcher> matcher = Collections.<TypeMatcher>singletonList(BINARY_STATEMENT_FACTORY);
		Map<ParseTreeNode, List<TypeMatcher>> passedMatchers = createMap(
				tree,
				asList(0),
				asList(matcher)
		);
		inferror = new LogicTypeInferror<>(universe, tree, passedMatchers, null);
		inferror.inferTypesFromMatchers();

		Map<ParseTreeNode, Set<Type>> map = inferror.typeMap;
		assertEquals(1, map.size());
		assertEquals(BOOLEAN_ONLY, map.get(tree.getFirstNode()));
	}

	@Test
	public void test_more_than_one_type_inferred_from_matcher_matching_more_than_one_type() throws Exception {

		ParseTree tree = parse("(a ∨ b)");
		Map<ParseTreeNode, List<TypeMatcher>> passedMatchers = createMap(
				tree,
				asList(0),
				asList(
						Arrays.<TypeMatcher>asList(
								IDENTITY_FUNCTION_FACTORY
						)
				)
		);
		inferror = new LogicTypeInferror<>(universe, tree, passedMatchers, null);
		inferror.inferTypesFromMatchers();

		Map<ParseTreeNode, Set<Type>> map = inferror.typeMap;
		assertEquals(1, map.size());
		assertEquals(NON_VOID_TYPES, map.get(tree.getFirstNode()));
	}

	@Test
	public void test_more_than_one_type_inferred_from_ambiguous_match() throws Exception {

		ParseTree tree = parse("(a ∨ b)");
		Map<ParseTreeNode, List<TypeMatcher>> passedMatchers = createMap(
				tree,
				asList(0),
				asList(
						Arrays.<TypeMatcher>asList(
								BINARY_STATEMENT_FACTORY,
								DEFINITION_FACTORY
						)
				)
		);
		inferror = new LogicTypeInferror<>(universe, tree, passedMatchers, null);
		inferror.inferTypesFromMatchers();

		Map<ParseTreeNode, Set<Type>> map = inferror.typeMap;
		assertEquals(1, map.size());
		assertEquals(CollectionUtils.<Type>createSet(Boolean.class, Void.class), map.get(tree.getFirstNode()));
	}

	@Test
	public void test_variable_assignments_found() throws Exception {

		ParseTree tree = parse("(a where a is 2)");
		List<TypeMatcher> matcher = Collections.<TypeMatcher>singletonList(ASSIGNMENT_FACTORY);
		Map<ParseTreeNode, List<TypeMatcher>> passedMatchers = createMap(tree, asList(0), asList(matcher));
		List<VariableAssignerFactory> assigner = Collections.<VariableAssignerFactory>singletonList(ASSIGNMENT_FACTORY);
		Map<ParseTreeNode, List<VariableAssignerFactory>> passedAssigners = createMap(tree, asList(0), asList(assigner));
		inferror = new LogicTypeInferror<>(universe, tree, passedMatchers, passedAssigners);
		inferror.findVariableAssignments();

		Map<ParseTreeNode, List<String>> map = inferror.variablesAssigned;
		assertEquals(1, map.size());
		assertEquals(Arrays.asList("a"), map.get(tree.getFirstNode()));
	}

	@Test
	public void test_type_of_free_variables_guessed() throws Exception {

		ParseTree tree = parse("(a ∨ b)");
		List<TypeMatcher> matcher = Collections.<TypeMatcher>singletonList(BINARY_STATEMENT_FACTORY);
		Map<ParseTreeNode, List<TypeMatcher>> passedMatchers = createMap(
				tree,
				asList(0),
				asList(matcher)
		);
		inferror = new LogicTypeInferror<>(universe, tree, passedMatchers, null);
		inferror.guessFreeVariableTypes();

		Map<ParseTreeNode, Set<Type>> map = inferror.typeMap;
		assertEquals(2, map.size());
		assertEquals(BOOLEAN_ONLY, map.get(tree.getNodes().get(1)));
		assertEquals(BOOLEAN_ONLY, map.get(tree.getNodes().get(3)));
	}

	@Test
	public void test_multiple_types_of_free_variables_guessed() throws Exception {

		ParseTree tree = parse("(a ∨ b)");
		List<TypeMatcher> matcher = Collections.<TypeMatcher>singletonList(EQUALITY_PREDICATE_FACTORY);
		Map<ParseTreeNode, List<TypeMatcher>> passedMatchers = createMap(
				tree,
				asList(0),
				asList(matcher)
		);
		inferror = new LogicTypeInferror<>(universe, tree, passedMatchers, null);
		inferror.guessFreeVariableTypes();

		Map<ParseTreeNode, Set<Type>> map = inferror.typeMap;
		assertEquals(2, map.size());
		assertEquals(NON_VOID_TYPES, map.get(tree.getNodes().get(1)));
		assertEquals(NON_VOID_TYPES, map.get(tree.getNodes().get(3)));
	}

	@Test
	public void test_guessed_types_are_intersected() throws Exception {

		ParseTree tree = parse("(a ∨ (a = b))");
		List<TypeMatcher> binaryStatementFactory = Collections.<TypeMatcher>singletonList(BINARY_STATEMENT_FACTORY);
		List<TypeMatcher> equalityPredicateFactory = Collections.<TypeMatcher>singletonList(EQUALITY_PREDICATE_FACTORY);
		Map<ParseTreeNode, List<TypeMatcher>> passedMatchers = createMap(
				tree,
				asList(0, 3),
				asList(binaryStatementFactory, equalityPredicateFactory)
		);
		inferror = new LogicTypeInferror<>(universe, tree, passedMatchers, null);
		inferror.guessFreeVariableTypes();

		Map<ParseTreeNode, Set<Type>> map = inferror.typeMap;
		assertEquals(3, map.size());
		assertEquals(BOOLEAN_ONLY, map.get(tree.getNodes().get(1)));
		assertEquals(BOOLEAN_ONLY, map.get(tree.getNodes().get(4)));
		assertEquals(NON_VOID_TYPES, map.get(tree.getNodes().get(6)));
	}

	@Test
	public void test_guessed_types_are_intersected_the_other_way() throws Exception {

		ParseTree tree = parse("(a = (a ∨ b))");
		List<TypeMatcher> equalityPredicateFactory = Collections.<TypeMatcher>singletonList(EQUALITY_PREDICATE_FACTORY);
		List<TypeMatcher> binaryStatementFactory = Collections.<TypeMatcher>singletonList(BINARY_STATEMENT_FACTORY);
		Map<ParseTreeNode, List<TypeMatcher>> passedMatchers = createMap(
				tree,
				asList(0, 3),
				asList(equalityPredicateFactory, binaryStatementFactory)
		);
		inferror = new LogicTypeInferror<>(universe, tree, passedMatchers, null);
		inferror.guessFreeVariableTypes();

		Map<ParseTreeNode, Set<Type>> map = inferror.typeMap;
		assertEquals(3, map.size());
		assertEquals(BOOLEAN_ONLY, map.get(tree.getNodes().get(1)));
		assertEquals(BOOLEAN_ONLY, map.get(tree.getNodes().get(4)));
		assertEquals(BOOLEAN_ONLY, map.get(tree.getNodes().get(6)));
	}

	@Test
	public void test_guessed_types_are_intersected_in_different_scopes() throws Exception {

		ParseTree tree = parse("((a ∨ (a = b)) = (a ∊ (a = b)))");
		List<TypeMatcher> equalityPredicateFactory = Collections.<TypeMatcher>singletonList(EQUALITY_PREDICATE_FACTORY);
		List<TypeMatcher> binaryStatementFactory = Collections.<TypeMatcher>singletonList(BINARY_STATEMENT_FACTORY);
		List<TypeMatcher> membershipPredicateFactory = Collections.<TypeMatcher>singletonList(MEMBERSHIP_PREDICATE_FACTORY);
		Map<ParseTreeNode, List<TypeMatcher>> passedMatchers = createMap(
				tree,
				asList(0, 1, 4, 11, 14),
				asList(
						equalityPredicateFactory,
						binaryStatementFactory,
						equalityPredicateFactory,
						membershipPredicateFactory,
						equalityPredicateFactory
				)
		);
		inferror = new LogicTypeInferror<>(universe, tree, passedMatchers, null);
		inferror.guessFreeVariableTypes();

		Map<ParseTreeNode, Set<Type>> map = inferror.typeMap;
		assertEquals(6, map.size());
		assertEquals(BOOLEAN_ONLY, map.get(tree.getNodes().get(2)));
		assertEquals(BOOLEAN_ONLY, map.get(tree.getNodes().get(5)));
		assertEquals(NON_VOID_TYPES, map.get(tree.getNodes().get(7)));
		assertEquals(TESTCLASS_ONLY, map.get(tree.getNodes().get(12)));
		assertEquals(TESTCLASS_ONLY, map.get(tree.getNodes().get(15)));
		assertEquals(NON_VOID_TYPES, map.get(tree.getNodes().get(17)));
	}
	@Test
	public void test_guessed_types_are_intersected_in_different_scopes_the_other_way() throws Exception {

		ParseTree tree = parse("((a = (a ∨ b)) = (a = (a ∊ b)))");
		List<TypeMatcher> equalityPredicateFactory = Collections.<TypeMatcher>singletonList(EQUALITY_PREDICATE_FACTORY);
		List<TypeMatcher> binaryStatementFactory = Collections.<TypeMatcher>singletonList(BINARY_STATEMENT_FACTORY);
		List<TypeMatcher> membershipPredicateFactory = Collections.<TypeMatcher>singletonList(MEMBERSHIP_PREDICATE_FACTORY);
		Map<ParseTreeNode, List<TypeMatcher>> passedMatchers = createMap(
				tree,
				asList(0, 1, 4, 11, 14),
				asList(
						equalityPredicateFactory,
						equalityPredicateFactory,
						binaryStatementFactory,
						equalityPredicateFactory,
						membershipPredicateFactory
				)
		);
		inferror = new LogicTypeInferror<>(universe, tree, passedMatchers, null);
		inferror.guessFreeVariableTypes();

		Map<ParseTreeNode, Set<Type>> map = inferror.typeMap;
		assertEquals(6, map.size());
		assertEquals(BOOLEAN_ONLY, map.get(tree.getNodes().get(2)));
		assertEquals(BOOLEAN_ONLY, map.get(tree.getNodes().get(5)));
		assertEquals(BOOLEAN_ONLY, map.get(tree.getNodes().get(7)));
		assertEquals(TESTCLASS_ONLY, map.get(tree.getNodes().get(12)));
		assertEquals(TESTCLASS_ONLY, map.get(tree.getNodes().get(15)));
		assertEquals(SET_ONLY, map.get(tree.getNodes().get(17)));
	}
}
