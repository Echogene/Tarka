package logic.evaluable.statements.quantified.restricted;

import logic.TestClass;
import logic.evaluable.Evaluable;
import logic.evaluable.predicate.equality.EqualityPredicateFactory;
import logic.evaluable.statements.quantified.standard.QuantifierFactory;
import logic.factory.FactoryTest;
import logic.function.Function;
import logic.function.reflexiveset.identity.SetIdentityFunction;
import logic.function.reflexiveset.union.UnionFactory;
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
		setUpFunctions("", "", "(x=y)");
		actual = factory.createElement(tokens, functions);
		assertEquals(expected, actual);

		evaluable1 = EqualityPredicateFactory.createElement("x", "y");
		expected = new RestrictedQuantifiedStatement<>(
				quantifierFactory.createElement("∀"),
				"x",
				UnionFactory.<TestClass>createElement("X", "Y"),
				(Evaluable<TestClass>) evaluable1
		);
		setUpTokens("(∀x∊()())");
		setUpFunctions("", "", "(x=y)");
		functions.set(1, UnionFactory.<TestClass>createElement("X", "Y"));
		actual = factory.createElement(tokens, functions);
		assertEquals(expected, actual);
	}
}
