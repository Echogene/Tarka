package maths.number.integer.functions.multiplication;

import logic.factory.FactoryTest;
import logic.function.Function;
import logic.function.identity.MemberIdentityFunction;
import logic.function.reflexive.ReflexiveFunction;
import maths.number.integer.Integer;
import maths.number.integer.IntegerMultiplior;
import maths.number.integer.model.universe.IntegerUniverse;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class MultaryMultiplicationFactoryTest extends FactoryTest<Integer, IntegerUniverse, MultaryMultiplicationFactory<Integer>> {

	private static final IntegerMultiplior multiplior = new IntegerMultiplior();

	public MultaryMultiplicationFactoryTest() {
		super(new MultaryMultiplicationFactory<>(multiplior, Integer.class), new IntegerUniverse());
	}

	@Test
	public void testEmptyCreate() throws Exception {
		Multiplication<Integer> expected = new Multiplication<>(
				Arrays.<ReflexiveFunction<Integer, ?>>asList(),
				multiplior
		);
		Function<?, ?, ?> actual = reader.read("(Π)");

		assertEquals(expected, actual);
	}

	@Test
	public void testCreate() throws Exception {
		Multiplication<Integer> expected = new Multiplication<>(
				Arrays.<ReflexiveFunction<Integer, ?>>asList(
						new MemberIdentityFunction<Integer>("2"),
						new MemberIdentityFunction<Integer>("3")
				),
				multiplior
		);
		Function<?, ?, ?> actual = reader.read("(Π 2 3)");

		assertEquals(expected, actual);
	}

	@Test
	public void testCreateWithThreeParameters() throws Exception {
		Multiplication<Integer> expected = new Multiplication<>(
				Arrays.<ReflexiveFunction<Integer, ?>>asList(
						new MemberIdentityFunction<Integer>("2"),
						new MemberIdentityFunction<Integer>("3"),
						new MemberIdentityFunction<Integer>("4")
				),
				multiplior
		);
		Function<?, ?, ?> actual = reader.read("(Π 2 3 4)");

		assertEquals(expected, actual);
	}
}
