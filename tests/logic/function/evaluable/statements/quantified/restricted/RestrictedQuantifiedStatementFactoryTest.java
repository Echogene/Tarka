package logic.function.evaluable.statements.quantified.restricted;

import logic.TestClass;
import logic.factory.FactoryTest;
import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.function.evaluable.predicate.equality.EqualityPredicateFactory;
import logic.function.evaluable.statements.quantified.standard.QuantifierFactory;
import logic.function.set.identity.SetIdentityFunction;
import logic.function.set.union.MultaryUnionFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class RestrictedQuantifiedStatementFactoryTest extends FactoryTest<RestrictedQuantifiedStatementFactory<TestClass>> {

	private static QuantifierFactory quantifierFactory;

	public RestrictedQuantifiedStatementFactoryTest() {
		factory = new RestrictedQuantifiedStatementFactory<>();
		quantifierFactory = new QuantifierFactory();
		functionFactory = new EqualityPredicateFactory<>();
	}

	@Test
	public void testCurlyBracketsThrowException() throws Exception {
		setUpTokens("{∀x∊X()}");
		setUpFunctions("", "", "(x=y)");
		expectFactoryException();
	}

	@Test
	public void testCreateElement() throws Exception {
		Function<TestClass, Boolean> expected;
		Function<TestClass, Boolean> actual;

		Function<TestClass, Boolean> evaluable1;

		evaluable1 = EqualityPredicateFactory.createElement("x", "y");
		expected = new RestrictedQuantifiedStatement<>(
				quantifierFactory.createElement("∀"),
				"x",
				new SetIdentityFunction<>("X"),
				(Evaluable<TestClass>) evaluable1
		);
		setUpTokens("(∀x∊X())");
		setUpFunctions("(x=y)");
		actual = factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		evaluable1 = EqualityPredicateFactory.createElement("x", "y");
		expected = new RestrictedQuantifiedStatement<>(
				quantifierFactory.createElement("∀"),
				"x",
				MultaryUnionFactory.<TestClass>createElement("X", "Y"),
				(Evaluable<TestClass>) evaluable1
		);
		setUpTokens("(∀x∊()())");
		setUpFunctions("", "(x=y)");
		functions.set(0, MultaryUnionFactory.<TestClass>createElement("X", "Y"));
		actual = factory.createElement(tokens, functions);
		assertEquals(expected, actual);
	}
}
