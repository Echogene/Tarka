package logic.type;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.function.evaluable.statements.binary.BinaryStatementFactory;
import logic.function.reference.FunctionReferenceFactory;
import logic.type.map.CheckorException;
import logic.type.map.MapToErrors;
import org.junit.Test;
import reading.parsing.ParseTree;
import reading.parsing.ParseTreeNode;
import util.MapUtils;

import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.*;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertTrue;
import static util.NumberUtils.ordinal;

/**
 * @author Steven Weston
 */
public class LogicTypeInferrorFunctionReferenceTest
		extends AbstractTypeInferrorTest<TestClass, TestClassUniverse, LogicTypeInferror<TestClass>> {

	public static final List<TypeMatcher> BINARY_STATEMENT_FACTORY
			= Collections.singletonList(new BinaryStatementFactory<>(TestClass.class));

	public static final List<TypeMatcher> FUNCTION_REFERENCE_FACTORY
			= Collections.singletonList(new FunctionReferenceFactory<>(TestClass.class));

	private static final TestClassUniverse UNIVERSE = new TestClassUniverse();

	public LogicTypeInferrorFunctionReferenceTest() {
		super(UNIVERSE, new LogicTypeInferror<>(UNIVERSE));
	}

	@Test
	public void testFunctionReferenceTypeGuessed() throws Exception {

		ParseTree tree = parse("(⊤ ∧ (f ⊤))");
		Map<ParseTreeNode, Set<Type>> map = inferror.inferTypes(
				tree,
				createMap(
						tree,
						Arrays.asList(0, 3),
						Arrays.asList(BINARY_STATEMENT_FACTORY, FUNCTION_REFERENCE_FACTORY)
				),
				MapUtils.emptyMap()
		);
		checkNodes(
				tree,
				map,
				asList(0, 1, 3, 5),
				Collections.singleton(Boolean.class)
		);
	}

	private void checkNodes(
			ParseTree tree,
			Map<ParseTreeNode, Set<Type>> map,
			List<Integer> notNull,
			Set<Type> expectedTypes
	) {

		MapToErrors<ParseTreeNode> errors = new MapToErrors<>(
				tree.getNodes(),
				parseTreeNode -> {

					Set<Type> types = map.get(parseTreeNode);
					int index = tree.getNodes().indexOf(parseTreeNode);
					if (notNull.contains(index)) {
						if (types == null) {
							throw new CheckorException(
									MessageFormat.format(
											"{0} element should not be null.  It should be {1}",
											ordinal(index),
											expectedTypes
									)
							);
						}
						if (!types.equals(expectedTypes)) {
							throw new CheckorException(
									MessageFormat.format(
											"{0} element should be {1} but was {2}",
											ordinal(index),
											expectedTypes,
											types
									)
							);
						}
					} else {
						if (types != null) {
							throw new CheckorException(
									MessageFormat.format(
											"{0} element should be null",
											ordinal(index)
									)
							);
						}
					}
				}
		);
		assertTrue(errors.concatenateErrorMessages(), errors.allPassed());
	}
}
