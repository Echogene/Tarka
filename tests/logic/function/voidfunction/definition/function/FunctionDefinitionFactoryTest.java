package logic.function.voidfunction.definition.function;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.factory.FactoryTest;
import logic.function.evaluable.predicate.equality.EqualityPredicateFactory;
import logic.function.evaluable.statements.binary.BinaryStatementFactory;
import logic.function.voidfunction.definition.function.definedfunction.DefinedEvaluableFunction;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Steven Weston
 */
public class FunctionDefinitionFactoryTest extends FactoryTest<TestClass, TestClassUniverse, FunctionDefinitionFactory<TestClass>> {

	public FunctionDefinitionFactoryTest() {
		super(new FunctionDefinitionFactory<>(TestClass.class), new TestClassUniverse());
		model.addFactory(new EqualityPredicateFactory<>(TestClass.class));
		model.addFactory(new BinaryStatementFactory<>(TestClass.class));
		universe.put("x");
		universe.put("y");
	}

	@Test
	public void testCreate() throws Exception {
		EvaluableDefinition<TestClass> definition = reader.read("(f a b ≝ (a = b))");
		definition.evaluate(model);

		DefinedEvaluableFunction<TestClass> evaluable = reader.read("(f x y)");
		assertFalse(evaluable.evaluate(model));
	}

	@Test
	public void testCreateWithParametersAppearingInTwoPlaces() throws Exception {
		EvaluableDefinition<TestClass> definition = reader.read("(f a b ≝ ((a = b) ∨ (b = a)))");
		definition.evaluate(model);

		DefinedEvaluableFunction<TestClass> evaluable = reader.read("(f x y)");
		assertFalse(evaluable.evaluate(model));
	}

	@Test
	public void testCreateWithUnusedParameter() throws Exception {
		EvaluableDefinition<TestClass> definition = reader.read("(f a b ≝ (a = a))");
		definition.evaluate(model);

		DefinedEvaluableFunction<TestClass> evaluable = reader.read("(f x y)");
		assertTrue(evaluable.evaluate(model));
	}
}
