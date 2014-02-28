package logic.type;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.function.evaluable.statements.binary.BinaryStatementFactory;
import logic.function.identity.IdentityFunctionFactory;
import logic.function.voidfunction.definition.constant.DefinitionFactory;
import org.junit.Test;
import reading.parsing.ParseTree;
import reading.parsing.ParseTreeNode;
import util.CollectionUtils;

import java.lang.reflect.Type;
import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class LogicTypeInferrorTest
		extends AbstractTypeInferrorTest<TestClass, TestClassUniverse, LogicTypeInferror<TestClass>> {

	private static final BinaryStatementFactory<TestClass> BINARY_STATEMENT_FACTORY
			= new BinaryStatementFactory<>(TestClass.class);
	private static final DefinitionFactory<TestClass> DEFINITION_FACTORY
			= new DefinitionFactory<>(TestClass.class);
	private static final IdentityFunctionFactory<TestClass> IDENTITY_FUNCTION_FACTORY
			= new IdentityFunctionFactory<>(TestClass.class);

	private static final TestClassUniverse UNIVERSE = new TestClassUniverse();

	public LogicTypeInferrorTest() {
		super(UNIVERSE, new LogicTypeInferror<>(UNIVERSE));
	}

	@Test
	public void test_single_type_inferred() throws Exception {

		ParseTree tree = parse("(a ∨ b)");
		List<TypeMatcher> matcher = Collections.<TypeMatcher>singletonList(BINARY_STATEMENT_FACTORY);
		Map<ParseTreeNode, Set<Type>> map = inferror.inferTypesFromMatchers(
				tree,
				createMap(
						tree,
						Arrays.asList(0),
						Arrays.asList(matcher)
				)
		);
		assertEquals(map.get(tree.getFirstNode()), Collections.<Type>singleton(Boolean.class));
	}

	@Test
	public void test_more_than_one_type_inferred_from_ambiguous_match() throws Exception {

		ParseTree tree = parse("(a ∨ b)");
		Map<ParseTreeNode, Set<Type>> map = inferror.inferTypesFromMatchers(
				tree,
				createMap(
						tree,
						Arrays.asList(0),
						Arrays.asList(
								Arrays.<TypeMatcher>asList(
										IDENTITY_FUNCTION_FACTORY
								)
						)
				)
		);
		assertEquals(
				map.get(tree.getFirstNode()),
				CollectionUtils.<Type>createSet(Boolean.class, logic.set.Set.class, TestClass.class)
		);
	}

	@Test
	public void test_more_than_one_type_inferred() throws Exception {

		ParseTree tree = parse("(a ∨ b)");
		Map<ParseTreeNode, Set<Type>> map = inferror.inferTypesFromMatchers(
				tree,
				createMap(
						tree,
						Arrays.asList(0),
						Arrays.asList(
								Arrays.<TypeMatcher>asList(
										BINARY_STATEMENT_FACTORY,
										DEFINITION_FACTORY
								)
						)
				)
		);
		assertEquals(map.get(tree.getFirstNode()), CollectionUtils.<Type>createSet(Boolean.class, Void.class));
	}
}
