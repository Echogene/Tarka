package logic.function.voidfunction.definition.constant;

import logic.function.evaluable.predicate.equality.EqualityPredicate;
import logic.function.evaluable.predicate.equality.EqualityPredicateFactory;
import logic.function.identity.MemberIdentityFunction;
import maths.number.integer.Integer;
import maths.number.integer.model.universe.IntegerUniverse;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Steven Weston
 */
public class MemberDefinitionTest {
	@Test
	public void testEvaluate() throws Exception {
		MemberDefinition<Integer> definition = new MemberDefinition<>("x", new MemberIdentityFunction<>("2"));

		IntegerUniverse universe = new IntegerUniverse();

		definition.evaluate(universe);

		EqualityPredicate<Integer> xEquals2 = EqualityPredicateFactory.createElement("x", "2");
		assertTrue(xEquals2.evaluate(universe));
	}
}
