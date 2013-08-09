package maths.number.integer.functions.addition;

import logic.TestClass;
import logic.factory.FactoryTest;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.identity.IdentityFunction;
import logic.function.reflexive.identity.IdentityFunctionFactory;
import maths.number.integer.Integer;
import maths.number.integer.IntegerSummor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class AdditionFactoryTest extends FactoryTest<AdditionFactory<Integer>> {
	private static IntegerSummor summor;

	public AdditionFactoryTest() {
		super();
		summor  = new IntegerSummor();
		factory = new AdditionFactory<>(summor);
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
		ReflexiveFunction<Integer> two = new IdentityFunction<>("2");
		ReflexiveFunction<Integer> three = new IdentityFunction<>("3");
		List<ReflexiveFunction<Integer>> parameters = new ArrayList<>();
		parameters.add(two);
		parameters.add(three);

		Addition<Integer> expected = new Addition<>(parameters, summor);

		setUpTokens("(2+3)");
		Addition<Integer> actual = (Addition<Integer>) factory.createElement(tokens);

		assertEquals(expected, actual);
	}

	@Test
	public void testMultary() throws Exception {
		ReflexiveFunction<Integer> two = new IdentityFunction<>("2");
		ReflexiveFunction<Integer> three = new IdentityFunction<>("3");
		ReflexiveFunction<Integer> four = new IdentityFunction<>("4");
		List<ReflexiveFunction<Integer>> parameters = new ArrayList<>();
		parameters.add(two);
		parameters.add(three);
		parameters.add(four);

		Addition<Integer> expected = new Addition<>(parameters, summor);

		setUpTokens("(Σ 2 3 4)");
		setUpFunctions();
		Addition<Integer> actual = (Addition<Integer>) factory.createElement(tokens, functions);

		assertEquals(expected, actual);
	}
}
