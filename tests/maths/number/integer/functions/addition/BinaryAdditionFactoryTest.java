package maths.number.integer.functions.addition;

import logic.TestClass;
import logic.factory.FactoryTest;
import logic.function.identity.IdentityFunctionFactory;
import logic.function.identity.MemberIdentityFunction;
import logic.function.reflexive.ReflexiveFunction;
import maths.number.integer.Integer;
import maths.number.integer.IntegerSummor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class BinaryAdditionFactoryTest extends FactoryTest<BinaryAdditionFactory<Integer>> {
	private static IntegerSummor summor;

	public BinaryAdditionFactoryTest() {
		super();
		summor  = new IntegerSummor();
		factory = new BinaryAdditionFactory<>(summor);
		functionFactory = new IdentityFunctionFactory<TestClass>();
	}

	@Test
	public void testCurlyBracketsThrowException() throws Exception {
		setUpTokens("{2 + 3}");
		setUpFunctions("", "");
		expectFactoryException();
	}

	@Test
	public void testFactoryWithoutFunctions() throws Exception {
		ReflexiveFunction<Integer> two = new MemberIdentityFunction<>("2");
		ReflexiveFunction<Integer> three = new MemberIdentityFunction<>("3");
		List<ReflexiveFunction<Integer>> parameters = new ArrayList<>();
		parameters.add(two);
		parameters.add(three);

		Addition<Integer> expected = new Addition<>(parameters, summor);

		setUpTokens("(2+3)");
		Addition<Integer> actual = factory.createElement(tokens);

		assertEquals(expected, actual);
	}

	@Test
	public void testMultary() throws Exception {
		ReflexiveFunction<Integer> two = new MemberIdentityFunction<>("2");
		ReflexiveFunction<Integer> three = new MemberIdentityFunction<>("3");
		ReflexiveFunction<Integer> four = new MemberIdentityFunction<>("4");
		List<ReflexiveFunction<Integer>> parameters = new ArrayList<>();
		parameters.add(two);
		parameters.add(three);
		parameters.add(four);

		Addition<Integer> expected = new Addition<>(parameters, summor);

		setUpTokens("(Σ 2 3 4)");
		setUpFunctions();
		Addition<Integer> actual = factory.createElement(tokens, functions);

		assertEquals(expected, actual);
	}
}
