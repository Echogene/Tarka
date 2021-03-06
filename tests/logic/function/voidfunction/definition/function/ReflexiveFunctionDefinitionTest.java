package logic.function.voidfunction.definition.function;

import logic.TestClass;
import logic.TestClassModel;
import logic.TestClassUniverse;
import logic.factory.SimpleLogicReader;
import logic.function.FunctionTest;
import logic.function.identity.IdentityFunctionFactory;
import logic.function.identity.MemberIdentityFunction;
import logic.function.voidfunction.definition.function.definedfunction.DefinedReflexiveFunction;
import ophelia.util.MapUtils;
import org.junit.Test;
import reading.evaluating.EvaluatorException;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class ReflexiveFunctionDefinitionTest extends FunctionTest<TestClass, TestClassUniverse, TestClassModel, ReflexiveFunctionDefinition<TestClass>> {

	private final SimpleLogicReader<TestClass> reader;

	public ReflexiveFunctionDefinitionTest() {

		super(new TestClassModel());
		SimpleLogicReader<TestClass> reader;
		try {
			reader = new SimpleLogicReader<>(
					Arrays.asList(
							new IdentityFunctionFactory<>(TestClass.class)
					),
					universe
			);
		} catch (EvaluatorException e) {
			// Should not happen, we've included the identity function.
			reader = null;
		}
		this.reader = reader;
		model.setReader(this.reader);
		universe.put("x");
		universe.put("y");
	}

	@Test
	public void testEvaluate() throws Exception {
		MemberIdentityFunction<TestClass> identityFunction = new MemberIdentityFunction<>("a");
		function = new ReflexiveFunctionDefinition<TestClass>(
				"f",
				MapUtils.createLinkedHashMap("a", Collections.<Type>singleton(TestClass.class)),
				identityFunction
		);
		function.evaluate(model);

		DefinedReflexiveFunction<TestClass> definedFunction = reader.read("(f x)");
		assertEquals(new TestClass("x"), definedFunction.evaluate(model));
	}

	@Test
	public void testNestedEvaluate() throws Exception {
		MemberIdentityFunction<TestClass> identityFunction = new MemberIdentityFunction<>("a");
		function = new ReflexiveFunctionDefinition<>(
				"f",
				MapUtils.createLinkedHashMap("a", Collections.<Type>singleton(TestClass.class)),
				identityFunction
		);
		function.evaluate(model);

		DefinedReflexiveFunction<TestClass> definedFunction = reader.read("(f (f x))");
		assertEquals(new TestClass("x"), definedFunction.evaluate(model));
	}
}
