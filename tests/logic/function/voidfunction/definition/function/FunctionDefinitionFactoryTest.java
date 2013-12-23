package logic.function.voidfunction.definition.function;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.factory.FactoryTest;
import logic.function.evaluable.predicate.equality.EqualityPredicateFactory;
import logic.function.voidfunction.definition.function.definedfunction.DefinedEvaluableFunction;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * @author Steven Weston
 */
public class FunctionDefinitionFactoryTest extends FactoryTest<TestClass, TestClassUniverse, FunctionDefinitionFactory<TestClass>> {

	public FunctionDefinitionFactoryTest() {
		super(new FunctionDefinitionFactory<>(TestClass.class), new TestClassUniverse());
		model.addFactory(new EqualityPredicateFactory<>(TestClass.class));
		universe.put("x");
		universe.put("y");
	}

	@Test
	public void testCreate() throws Exception {
		EvaluableDefinition<TestClass> definition = (EvaluableDefinition<TestClass>) reader.read("(f a b ≝ (a = b))");
		definition.evaluate(model);

		DefinedEvaluableFunction<TestClass> evaluable = (DefinedEvaluableFunction<TestClass>) reader.read("(f x y)");
		assertFalse(evaluable.evaluate(model));
	}
}
