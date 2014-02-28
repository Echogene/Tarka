package logic.type;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.function.evaluable.statements.binary.BinaryStatementFactory;
import org.junit.Test;
import reading.parsing.ParseTree;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Steven Weston
 */
public class LogicTypeInferrorTest
		extends AbstractTypeInferrorTest<TestClass, TestClassUniverse, LogicTypeInferror<TestClass>> {

	public static final List<TypeMatcher> BINARY_STATEMENT_FACTORY
			= Collections.singletonList(new BinaryStatementFactory<>(TestClass.class));

	private static final TestClassUniverse UNIVERSE = new TestClassUniverse();

	public LogicTypeInferrorTest() {
		super(UNIVERSE, new LogicTypeInferror<>(UNIVERSE));
	}

	@Test
	public void test_inferTypesFromMatchers() throws Exception {

		ParseTree tree = parse("(a ∨ b)");
		inferror.inferTypesFromMatchers(
				tree,
				createMap(
						tree,
						Arrays.asList(0),
						Arrays.asList(BINARY_STATEMENT_FACTORY)
				)
		);
	}
}
