package logic.function.voidfunction.definition.function;

import logic.factory.SimpleLogicReader;
import logic.function.voidfunction.definition.function.definedfunction.AbstractDefinedFunction;
import logic.function.voidfunction.definition.function.definedfunction.DefinedEvaluable;
import maths.number.integer.Integer;
import maths.number.integer.model.IntegerModel;
import maths.number.integer.model.universe.IntegerUniverse;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Steven Weston
 */
public class FunctionDefinitionFactoryIntegerTest {

	private final IntegerModel model;
	private final IntegerUniverse universe;
	private final SimpleLogicReader<Integer> reader;

	public FunctionDefinitionFactoryIntegerTest() {
		model = new IntegerModel();
		universe = model.getUniverse();
		reader = model.getReader();
	}

	@Test
	@Ignore("Until multitype returns are implemented")
	public void testDefineIdentityFunction() throws Exception {
		FunctionDefinition<Integer, ?> definition = reader.read("(f x ≝ x)");
		definition.evaluate(model);

		AbstractDefinedFunction<Integer, ?> function = reader.read("(f 1)");
		assertEquals(universe.get("1"), function.evaluate(model));
	}

	@Test
	@Ignore("Until multitype returns are implemented")
	public void testDefineNestedIdentityFunction() throws Exception {
		FunctionDefinition<Integer, ?> definition = reader.read("(f x ≝ (x))");
		definition.evaluate(model);

		AbstractDefinedFunction<Integer, ?> function = reader.read("(f 1)");
		assertEquals(universe.get("1"), function.evaluate(model));
	}

	@Test
	public void testDefineEqualityPredicate() throws Exception {
		EvaluableDefinition<Integer> definition = reader.read("(f x y ≝ (x = y))");
		definition.evaluate(model);

		DefinedEvaluable<Integer> function;
		function = reader.read("(f 1 1)");
		assertTrue(function.evaluate(model));
		function = reader.read("(f [0 1] [0 1])");
		assertTrue(function.evaluate(model));
		function = reader.read("(f [0 1] {0 1})");
		assertTrue(function.evaluate(model));
		function = reader.read("(f ⊤ ⊤)");
		assertTrue(function.evaluate(model));
		// Sanity check it doesn't always return true
		function = reader.read("(f 1 2)");
		assertFalse(function.evaluate(model));
	}

	@Test
	public void testDefineMembershipPredicate() throws Exception {
		EvaluableDefinition<Integer> definition = reader.read("(f x X ≝ (x ∊ X))");
		definition.evaluate(model);

		DefinedEvaluable<Integer> function;
		function = reader.read("(f 1 {1})");
		assertTrue(function.evaluate(model));
	}

	@Test
	public void testDefineBinaryStatement() throws Exception {
		EvaluableDefinition<Integer> definition = reader.read("(f x X ≝ ((x ∊ X) ∧ ((x + 1) ∊ X)))");
		definition.evaluate(model);

		DefinedEvaluable<Integer> function;
		function = reader.read("(f 1 {1})");
		assertFalse(function.evaluate(model));
		function = reader.read("(f 1 {1 2})");
		assertTrue(function.evaluate(model));
	}

	@Test
	public void testDefineUnaryStatement() throws Exception {
		EvaluableDefinition<Integer> definition = reader.read("(f x X ≝ (¬(x ∊ X)))");
		definition.evaluate(model);

		DefinedEvaluable<Integer> function;
		function = reader.read("(f 1 {1})");
		assertFalse(function.evaluate(model));
	}

	@Test
	public void testDefineQuantifiedStatement() throws Exception {
		EvaluableDefinition<Integer> definition = reader.read("(f X ≝ (∀y (y ∊ X)))");
		definition.evaluate(model);

		reader.read("(f {1})");
	}

	@Test
	@Ignore("Until multitype returns are implemented")
	public void testDefineAssignment() throws Exception {
		FunctionDefinition<Integer, ?> definition = reader.read("(f x ≝ (y where y is x))");
		definition.evaluate(model);

		AbstractDefinedFunction<Integer, ?> function = reader.read("(f 1)");
		assertEquals(universe.get("1"), function.evaluate(model));
	}
}
