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
public class BinaryMultiplicationFactoryTest extends FactoryTest<Integer, IntegerUniverse, BinaryMultiplicationFactory<Integer>> {

	private static final IntegerMultiplior multiplior = new IntegerMultiplior();

	public BinaryMultiplicationFactoryTest() {
		super(new BinaryMultiplicationFactory<>(multiplior, Integer.class), new IntegerUniverse());
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
		Function<?, ?, ?> actual = reader.read("(2 × 3)");

		assertEquals(expected, actual);
	}
}
