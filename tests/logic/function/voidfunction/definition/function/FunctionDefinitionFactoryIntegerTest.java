package logic.function.voidfunction.definition.function;

import logic.factory.SimpleLogicReader;
import logic.function.voidfunction.definition.function.definedfunction.*;
import logic.set.finite.FiniteSet;
import maths.number.integer.Integer;
import maths.number.integer.model.IntegerModel;
import maths.number.integer.model.universe.IntegerSet;
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
	private final IntegerSet ℤ;

	public FunctionDefinitionFactoryIntegerTest() {
		model = new IntegerModel();
		universe = model.getUniverse();
		ℤ = (IntegerSet) universe.getUniversalSet();
		reader = model.getReader();
	}

	@Test
	public void testDefineWithNoParameters() throws Exception {
		ReflexiveFunctionDefinition<Integer> definition = reader.read("(f ≝ 1)");
		definition.evaluate(model);

		AbstractDefinedFunction<Integer, ?> function = reader.read("(f)");
		assertEquals(universe.get("1"), function.evaluate(model));
	}

	@Test
	@Ignore("Until multitype returns are implemented")
	public void testDefineMultitypeIdentityFunction() throws Exception {
		FunctionDefinition<Integer, ?> definition = reader.read("(f x ≝ x)");
		definition.evaluate(model);

		AbstractDefinedFunction<Integer, ?> function = reader.read("(f 1)");
		assertEquals(universe.get("1"), function.evaluate(model));
	}

	@Test
	@Ignore("Until multitype returns are implemented")
	public void testDefineMultitypeNestedIdentityFunction() throws Exception {
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
		function = reader.read("(f 1 {1})");
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
	public void testDefineBinaryUnion() throws Exception {
		SetFunctionDefinition<Integer> definition = reader.read("(f X ≝ ({0} ∪ X))");
		definition.evaluate(model);

		DefinedSetFunction<Integer> function;
		function = reader.read("(f {1})");
		assertTrue(function.evaluate(model).containsValue(ℤ.get("0")));
		assertTrue(function.evaluate(model).containsValue(ℤ.get("1")));
	}

	@Test
	public void testDefineMultaryUnion() throws Exception {
		SetFunctionDefinition<Integer> definition = reader.read("(f X ≝ (⋃ {0} X))");
		definition.evaluate(model);

		DefinedSetFunction<Integer> function;
		function = reader.read("(f {1})");
		assertTrue(function.evaluate(model).containsValue(ℤ.get("0")));
		assertTrue(function.evaluate(model).containsValue(ℤ.get("1")));
	}

	@Test
	public void testDefineBinaryIntersection() throws Exception {
		SetFunctionDefinition<Integer> definition = reader.read("(f X ≝ ({0} ∩ X))");
		definition.evaluate(model);

		DefinedSetFunction<Integer> function;
		function = reader.read("(f {1})");
		assertFalse(function.evaluate(model).containsValue(ℤ.get("0")));
		assertFalse(function.evaluate(model).containsValue(ℤ.get("1")));
	}

	@Test
	public void testDefineMultaryIntersection() throws Exception {
		SetFunctionDefinition<Integer> definition = reader.read("(f X ≝ (⋂ {0} X))");
		definition.evaluate(model);

		DefinedSetFunction<Integer> function;
		function = reader.read("(f {1})");
		assertFalse(function.evaluate(model).containsValue(ℤ.get("0")));
		assertFalse(function.evaluate(model).containsValue(ℤ.get("1")));
	}

	@Test
	public void testDefineReflexiveAssignment() throws Exception {
		ReflexiveFunctionDefinition<Integer> definition = reader.read("(f x ≝ (y where y is (x + 2)))");
		definition.evaluate(model);

		DefinedReflexiveFunction<Integer> function;
		function = reader.read("(f 1)");
		assertEquals(ℤ.get("3"), function.evaluate(model));
	}

	@Test
	public void testDefineEvaluableAssignment() throws Exception {
		EvaluableDefinition<Integer> definition = reader.read("(f x ≝ (y where y is (x ∨ ⊤)))");
		definition.evaluate(model);

		DefinedEvaluable<Integer> function;
		function = reader.read("(f ⊥)");
		assertTrue(function.evaluate(model));
	}

	@Test
	public void testDefineSetAssignment() throws Exception {
		SetFunctionDefinition<Integer> definition = reader.read("(f x ≝ (y where y is (x ∪ {0})))");
		definition.evaluate(model);

		DefinedSetFunction<Integer> function;
		function = reader.read("(f {1})");
		assertTrue(function.evaluate(model).containsValue(ℤ.get("0")));
		assertTrue(function.evaluate(model).containsValue(ℤ.get("1")));
	}

	@Test
	@Ignore("Until multitype returns are implemented")
	public void testDefineMultitypeAssignment() throws Exception {
		FunctionDefinition<Integer, ?> definition = reader.read("(f x ≝ (y where y is x))");
		definition.evaluate(model);

		AbstractDefinedFunction<Integer, ?> function = reader.read("(f 1)");
		assertEquals(universe.get("1"), function.evaluate(model));
	}

	@Test
	public void testDefineRestrictedQuantifiedStatement() throws Exception {
		EvaluableDefinition<Integer> definition = reader.read("(f X ≝ (∀y ∊ [0 10) (y ∊ X)))");
		definition.evaluate(model);

		DefinedEvaluable<Integer> function;
		function = reader.read("(f [0 11])");
		assertTrue(function.evaluate(model));
	}

	@Test
	public void testDefineDefinition() throws Exception {
		VoidFunctionDefinition<Integer> definition = reader.read("(f x ≝ (y ≔ x))");
		definition.evaluate(model);

		DefinedVoidFunction<Integer> function;
		function = reader.read("(f [0 11])");
		assertFalse(universe.contains("y"));
		function.evaluate(model);
		assertTrue(universe.contains("y"));
		assertEquals(reader.read("[0 11]").evaluate(model), reader.read("(y)").evaluate(model));
	}

	@Test
	public void testDefineSimpleSet() throws Exception {
		SetFunctionDefinition<Integer> definition = reader.read("(f x ≝ {x 1})");
		definition.evaluate(model);

		DefinedSetFunction<Integer> function;
		function = reader.read("(f 0)");
		assertTrue(function.evaluate(model).containsValue(ℤ.get("0")));
		assertTrue(function.evaluate(model).containsValue(ℤ.get("1")));
	}

	@Test
	public void testDefineReflexiveIfElse() throws Exception {
		ReflexiveFunctionDefinition<Integer> definition = reader.read("(f x ≝ ((x + 2) if (x = 2) otherwise (x + 1)))");
		definition.evaluate(model);

		DefinedReflexiveFunction<Integer> function;
		function = reader.read("(f 0)");
		assertEquals(ℤ.get("1"), function.evaluate(model));
		function = reader.read("(f 2)");
		assertEquals(ℤ.get("4"), function.evaluate(model));
	}

	@Test
	public void testDefineSetIfElse() throws Exception {
		SetFunctionDefinition<Integer> definition = reader.read("(f x ≝ ({x 1} if (x = 2) otherwise {x 2}))");
		definition.evaluate(model);

		DefinedSetFunction<Integer> function;
		function = reader.read("(f 0)");
		assertTrue(function.evaluate(model).containsValue(ℤ.get("0")));
		assertTrue(function.evaluate(model).containsValue(ℤ.get("2")));
		function = reader.read("(f 2)");
		assertTrue(function.evaluate(model).containsValue(ℤ.get("2")));
		assertTrue(function.evaluate(model).containsValue(ℤ.get("1")));
	}

	@Test
	public void testDefineEvaluableIfElse() throws Exception {
		EvaluableDefinition<Integer> definition = reader.read("(f x ≝ ((x = 1) if (x = 2) otherwise (x = 2)))");
		definition.evaluate(model);

		DefinedEvaluable<Integer> function;
		function = reader.read("(f 0)");
		assertFalse(function.evaluate(model));
	}

	@Test
	@Ignore("Until multitype returns are implemented")
	public void testDefineMultitypeIfElse() throws Exception {
		EvaluableDefinition<Integer> definition = reader.read("(f x ≝ (x if ⊤ otherwise x))");
		definition.evaluate(model);

		DefinedEvaluable<Integer> function;
		function = reader.read("(f 0)");
		assertFalse(function.evaluate(model));
	}

	@Test
	public void testDefineComplexSetWithSetParameter() throws Exception {
		SetFunctionDefinition<Integer> definition = reader.read("(f X ≝ {y ∊ X | (y ∊ [5 10])})");
		definition.evaluate(model);

		DefinedSetFunction<Integer> function;
		function = reader.read("(f [0 5])");
		assertFalse(function.evaluate(model).containsValue(ℤ.get("0")));
		assertTrue(function.evaluate(model).containsValue(ℤ.get("5")));
		assertFalse(function.evaluate(model).containsValue(ℤ.get("10")));
	}

	@Test
	@Ignore("Until the bug with complex sets is fixed")
	public void testDefineComplexSetWithBooleanParameter() throws Exception {
		SetFunctionDefinition<Integer> definition = reader.read("(f x ≝ {y ∊ [0 5] | x})");
		definition.evaluate(model);

		DefinedSetFunction<Integer> function;
		function = reader.read("(f ⊤)");
		// This is not working because by the time the filtered set is asked whether it contains 5, the parameter
		// (variable) x containing ⊤ has already been unassigned
		assertTrue(function.evaluate(model).containsValue(ℤ.get("5")));
	}

	@Test
	@Ignore("Until the ugly unsafe map is removed from FunctionDefinitionFactory")
	public void testDefineFunctionDefinition() throws Exception {
		VoidFunctionDefinition<Integer> definition = reader.read("(f X ≝ (g Y ≝ (Y = X)))");
		definition.evaluate(model);

		DefinedVoidFunction<Integer> definition2;
		definition2 = reader.read("(f [0 5])");
		definition2.evaluate(model);
		DefinedEvaluable<Integer> function = reader.read("(g {0 1 2 3 4 5})");
		assertTrue(function.evaluate(model));
	}

	@Test
	public void testDefineBinaryAddition() throws Exception {
		ReflexiveFunctionDefinition<Integer> definition = reader.read("(f x y ≝ (x + y))");
		definition.evaluate(model);

		DefinedReflexiveFunction<Integer> function;
		function = reader.read("(f 1 2)");
		assertEquals(ℤ.get("3"), function.evaluate(model));
	}

	@Test
	public void testDefineMultaryAddition() throws Exception {
		ReflexiveFunctionDefinition<Integer> definition = reader.read("(f x y ≝ (Σ x y))");
		definition.evaluate(model);

		DefinedReflexiveFunction<Integer> function;
		function = reader.read("(f 1 2)");
		assertEquals(ℤ.get("3"), function.evaluate(model));
	}

	@Test
	public void testDefineBinaryMultiplication() throws Exception {
		ReflexiveFunctionDefinition<Integer> definition = reader.read("(f x y ≝ (x × y))");
		definition.evaluate(model);

		DefinedReflexiveFunction<Integer> function;
		function = reader.read("(f 1 2)");
		assertEquals(ℤ.get("2"), function.evaluate(model));
	}

	@Test
	public void testDefineMultaryMultiplication() throws Exception {
		ReflexiveFunctionDefinition<Integer> definition = reader.read("(f x y ≝ (Π x y))");
		definition.evaluate(model);

		DefinedReflexiveFunction<Integer> function;
		function = reader.read("(f 1 2)");
		assertEquals(ℤ.get("2"), function.evaluate(model));
	}

	@Test
	public void testDefineSetTotal() throws Exception {
		ReflexiveFunctionDefinition<Integer> definition = reader.read("(f x y ≝ (Σ / {x y}))");
		definition.evaluate(model);

		DefinedReflexiveFunction<Integer> function;
		function = reader.read("(f 1 2)");
		assertEquals(ℤ.get("3"), function.evaluate(model));
	}

	@Test
	public void testDefineSubtraction() throws Exception {
		ReflexiveFunctionDefinition<Integer> definition = reader.read("(f x y ≝ (x − y))");
		definition.evaluate(model);

		DefinedReflexiveFunction<Integer> function;
		function = reader.read("(f 1 2)");
		assertEquals(ℤ.get("-1"), function.evaluate(model));
	}

	@Test
	public void testDefineIntervalFunction() throws Exception {
		SetFunctionDefinition<Integer> definition = reader.read("(f x y ≝ (x y))");
		definition.evaluate(model);

		DefinedSetFunction<Integer> function;
		function = reader.read("(f 1 2)");
		assertEquals(ℤ.get("0"), ((FiniteSet<Integer>) function.evaluate(model)).size());
	}
}
