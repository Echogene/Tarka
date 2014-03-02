package logic.type;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.function.evaluable.statements.binary.BinaryStatementFactory;
import logic.function.identity.IdentityFunctionFactory;
import logic.function.voidfunction.definition.constant.DefinitionFactory;
import org.junit.Test;
import reading.lexing.LexerException;
import reading.parsing.ParseTree;
import reading.parsing.ParseTreeNode;
import reading.parsing.ParserException;
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

	public LogicTypeInferrorTest() {
		super(new TestClassUniverse());
	}

	@Test
	public void test_single_type_inferred() throws Exception {

		ParseTree tree = parse("(a ∨ b)");
		List<TypeMatcher> matcher = Collections.<TypeMatcher>singletonList(BINARY_STATEMENT_FACTORY);
		inferror.inferTypesFromMatchers(
				createMap(
						tree,
						Arrays.asList(0),
						Arrays.asList(matcher)
				)
		);
		Map<ParseTreeNode, Set<Type>> map = inferror.typeMap;
		assertEquals(map.get(tree.getFirstNode()), Collections.<Type>singleton(Boolean.class));
	}

	@Test
	public void test_more_than_one_type_inferred_from_matcher_matching_more_than_one_type() throws Exception {

		ParseTree tree = parse("(a ∨ b)");
		inferror.inferTypesFromMatchers(
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
		Map<ParseTreeNode, Set<Type>> map = inferror.typeMap;
		assertEquals(
				map.get(tree.getFirstNode()),
				CollectionUtils.<Type>createSet(Boolean.class, logic.set.Set.class, TestClass.class)
		);
	}

	@Test
	public void test_more_than_one_type_inferred_from_ambiguous_match() throws Exception {

		ParseTree tree = parse("(a ∨ b)");
		inferror.inferTypesFromMatchers(
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
		Map<ParseTreeNode, Set<Type>> map = inferror.typeMap;
		assertEquals(map.get(tree.getFirstNode()), CollectionUtils.<Type>createSet(Boolean.class, Void.class));
	}

	@Override
	protected ParseTree parse(String string) throws LexerException, ParserException {
		ParseTree tree = super.parse(string);
		inferror = new LogicTypeInferror<>(universe, tree);
		return tree;
	}
}
