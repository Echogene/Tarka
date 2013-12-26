package logic.function.voidfunction.definition.function;

import logic.TestClass;
import logic.TestClassModel;
import logic.TestClassUniverse;
import logic.factory.SimpleLogicReader;
import logic.function.Function;
import logic.function.FunctionTest;
import logic.function.identity.IdentityFunctionFactory;
import logic.function.identity.MemberIdentityFunction;
import org.junit.Ignore;
import org.junit.Test;

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
		reader = new SimpleLogicReader<>(
				Arrays.asList(
						new IdentityFunctionFactory<>(TestClass.class)
				),
				universe
		);
		model.setReader(reader);
		universe.put("x");
		universe.put("y");
	}

	@Test
	public void testEvaluate() throws Exception {
		MemberIdentityFunction<TestClass> identityFunction = new MemberIdentityFunction<>("a");
		function = new ReflexiveFunctionDefinition<>(
				"f",
				Collections.singletonMap("a", Collections.<Type>singleton(TestClass.class)),
				identityFunction
		);
		function.evaluate(model);

		Function<TestClass, ?> definedFunction = reader.read("(f x)");
		assertEquals(new TestClass("x"), definedFunction.evaluate(model));
	}

	@Ignore("Until parameters can be redefined")
	@Test
	public void testNestedEvaluate() throws Exception {
		MemberIdentityFunction<TestClass> identityFunction = new MemberIdentityFunction<>("a");
		function = new ReflexiveFunctionDefinition<>(
				"f",
				Collections.singletonMap("a", Collections.<Type>singleton(TestClass.class)),
				identityFunction
		);
		function.evaluate(model);

		Function<TestClass, ?> definedFunction = reader.read("(f (f x))");
		assertEquals(new TestClass("x"), definedFunction.evaluate(model));
	}
}
