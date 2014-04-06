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
	private ParseTree tree;

	public LogicTypeInferrorTest() {
		super(new TestClassUniverse());
	}

	@Test
	public void test_single_type_inferred() throws Exception {

		tree = parse("(a ∨ b)");
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

		tree = parse("(a ∨ b)");
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

		tree = parse("(a ∨ b)");
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
	public void test_variables_found() throws Exception {

		tree = parse("(a ∨ b)");
		List<TypeMatcher> matcher = Collections.<TypeMatcher>singletonList(BINARY_STATEMENT_FACTORY);
		Map<ParseTreeNode, List<TypeMatcher>> passedMatchers = createMap(
				tree,
				asList(0),
				asList(matcher)
		);
		inferror = new LogicTypeInferror<>(universe, tree, passedMatchers, null);
		inferror.findVariables();

		Set<ParseTreeNode> set = inferror.variables;
		assertEquals(2, set.size());
		assertEquals(CollectionUtils.createSet(getNode(1), getNode(3)), set);
	}

	@Test
	public void test_variables_found_ignoring_one_of_them() throws Exception {

		tree = parse("(a where a is 2)");
		List<TypeMatcher> matcher = Collections.<TypeMatcher>singletonList(ASSIGNMENT_FACTORY);
		Map<ParseTreeNode, List<TypeMatcher>> passedMatchers = createMap(
				tree,
				asList(0),
				asList(matcher)
		);
		inferror = new LogicTypeInferror<>(universe, tree, passedMatchers, null);
		inferror.findVariables();

		Set<ParseTreeNode> set = inferror.variables;
		assertEquals(2, set.size());
		assertEquals(CollectionUtils.createSet(getNode(1), getNode(5)), set);
	}

	@Test
	public void test_variable_types_inferred_from_universe() throws Exception {

		tree = parse("(⊤ ∨ ⊥)");
		List<TypeMatcher> matcher = Collections.<TypeMatcher>singletonList(BINARY_STATEMENT_FACTORY);
		Map<ParseTreeNode, List<TypeMatcher>> passedMatchers = createMap(
				tree,
				asList(0),
				asList(matcher)
		);
		inferror = new LogicTypeInferror<>(universe, tree, passedMatchers, null);
		inferror.inferTypesFromMatchers();
		inferror.findVariables();
		inferror.inferTypesOfVariablesFromUniverse();

		Map<ParseTreeNode, Set<Type>> map = inferror.typeMap;
		assertEquals(3, map.size());
		assertEquals(BOOLEAN_ONLY, map.get(getNode(0)));
		assertEquals(BOOLEAN_ONLY, map.get(getNode(1)));
		assertEquals(BOOLEAN_ONLY, map.get(getNode(3)));

	}

	private ParseTreeNode getNode(int index) {
		return tree.getNode(index);
	}
}
