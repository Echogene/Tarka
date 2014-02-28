package logic.function.voidfunction.definition.function;

import logic.factory.FactoryTest;
import logic.function.evaluable.predicate.equality.EqualityPredicateFactory;
import logic.function.evaluable.statements.binary.BinaryStatementFactory;
import logic.function.maths.number.addition.BinaryAdditionFactory;
import logic.function.maths.number.subtraction.SubtractionFactory;
import logic.function.voidfunction.definition.function.definedfunction.DefinedEvaluable;
import maths.number.integer.Integer;
import maths.number.integer.IntegerSubtractor;
import maths.number.integer.IntegerSummor;
import maths.number.integer.model.universe.IntegerUniverse;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Steven Weston
 */
public class FunctionDefinitionFactoryTest extends FactoryTest<Integer, IntegerUniverse, FunctionDefinitionFactory<Integer>> {

	public FunctionDefinitionFactoryTest() {
		super(new FunctionDefinitionFactory<>(Integer.class), new IntegerUniverse());
		model.addFactory(new EqualityPredicateFactory<>(Integer.class));
		model.addFactory(new BinaryStatementFactory<>(Integer.class));
		IntegerSummor summor = new IntegerSummor();
		model.addFactory(new BinaryAdditionFactory<>(summor, Integer.class));
		IntegerSubtractor subtractor = new IntegerSubtractor();
		model.addFactory(new SubtractionFactory<>(subtractor, Integer.class));
//		model.addFactory(new FunctionReferenceFactory<>(Integer.class));
	}

	@Test
	public void testCreate() throws Exception {
		EvaluableDefinition<Integer> definition = reader.read("(f a b ≝ (a = b))");
		definition.evaluate(model);

		DefinedEvaluable<Integer> evaluable = reader.read("(f 1 2)");
		assertFalse(evaluable.evaluate(model));
	}

	@Test
	public void testCreateWithParametersAppearingInTwoPlaces() throws Exception {
		EvaluableDefinition<Integer> definition = reader.read("(f a b ≝ ((a = b) ∨ (b = a)))");
		definition.evaluate(model);

		DefinedEvaluable<Integer> evaluable = reader.read("(f 1 2)");
		assertFalse(evaluable.evaluate(model));
	}

	@Test
	public void testCreateWithUnusedParameter() throws Exception {
		EvaluableDefinition<Integer> definition = reader.read("(f a b ≝ (a = a))");
		definition.evaluate(model);

		DefinedEvaluable<Integer> evaluable = reader.read("(f 1 2)");
		assertTrue(evaluable.evaluate(model));
	}

	@Test
	public void testRecursiveDefinition() throws Exception {
		EvaluableDefinition<Integer> definition = reader.read("(f a ≝ (0 if (a = 0) otherwise ((f (a − 1)) + 1)))");
		definition.evaluate(model);
	}
}
