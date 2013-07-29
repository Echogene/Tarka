package logic.evaluable.predicate;

import logic.TestClass;
import logic.evaluable.predicate.membership.MembershipPredicate;
import logic.evaluable.predicate.membership.MembershipPredicateFactory;
import logic.factory.FactoryTest;
import logic.function.Function;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.set.SetFunction;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class MembershipPredicateFactoryTest extends FactoryTest<MembershipPredicateFactory<TestClass>> {

	public MembershipPredicateFactoryTest() {
		factory = new MembershipPredicateFactory<>();
	}

	@Test
	public void testCurlyBracketsThrowException() throws Exception {
		setUpTokens("{x ∊ X}");
		setUpOneIdentityAndOneSetIdentityFunction("", "");
		expectFactoryException();

		setUpTokens("({} ∊ X)");
		setUpOneIdentityAndOneSetIdentityFunction("x", "");
		expectFactoryException();
	}

	@Test
	public void testCreateElement() throws Exception {
		Function<TestClass, Boolean> expected;
		Function<TestClass, Boolean> actual;

		expected = new MembershipPredicate<>("x", "X");
		setUpTokens("(x ∊ X)");
		actual = factory.createElement(tokens);
		assertEquals("Expected created membership predicate to be equal to the factory-built one", expected, actual);

		setUpOneIdentityAndOneSetIdentityFunction("x", "");
		expected = new MembershipPredicate<>((ReflexiveFunction<TestClass>) functions.get(0), "X");
		setUpTokens("(() ∊ X)");
		actual = factory.createElement(tokens, functions);
		assertEquals("Expected created membership predicate to be equal to the factory-built one", expected, actual);

		setUpOneIdentityAndOneSetIdentityFunction("", "X");
		expected = new MembershipPredicate<>("x", (SetFunction<TestClass>) functions.get(1));
		setUpTokens("(x ∊ ())");
		actual = factory.createElement(tokens, functions);
		assertEquals("Expected created membership predicate to be equal to the factory-built one", expected, actual);

		setUpOneIdentityAndOneSetIdentityFunction("x", "X");
		expected = new MembershipPredicate<>(
				(ReflexiveFunction<TestClass>) functions.get(0),
				(SetFunction<TestClass>) functions.get(1));
		setUpTokens("(() ∊ ())");
		actual = factory.createElement(tokens, functions);
		assertEquals("Expected created membership predicate to be equal to the factory-built one", expected, actual);
	}
}
