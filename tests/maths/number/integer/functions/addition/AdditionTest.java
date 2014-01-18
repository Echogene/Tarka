package maths.number.integer.functions.addition;

import logic.function.identity.MemberIdentityFunction;
import logic.function.reflexive.ReflexiveFunction;
import maths.number.integer.Integer;
import maths.number.integer.IntegerSummor;
import maths.number.integer.model.IntegerModel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class AdditionTest {
	@Test
	public void test() throws Exception {
	ReflexiveFunction<Integer, ?> two = new MemberIdentityFunction<>("2");
		List<ReflexiveFunction<Integer, ?>> parameters = new ArrayList<>();
		parameters.add(two);
		parameters.add(two);

		IntegerSummor summor = new IntegerSummor();
		Addition<Integer> sum = new Addition<>(parameters, summor);

		assertEquals("(2 + 2)", sum.toString());

		IntegerModel model = new IntegerModel();
		assertEquals("2 + 2 = 4", new Integer(4), sum.evaluate(model));

		parameters.clear();
		parameters.add(new MemberIdentityFunction<>("123456789"));
		parameters.add(new MemberIdentityFunction<>("987654321"));

		sum = new Addition<>(parameters, summor);
		assertEquals("123456789 + 987654321 = 1111111110", new Integer(1111111110), sum.evaluate(model));

		parameters.clear();
		parameters.add(new MemberIdentityFunction<>("111111111111111111111111111111111111111111111111111111111111111111111"));
		parameters.add(new MemberIdentityFunction<>("111111111111111111111111111111111111111111111111111111111111111111111"));

		sum = new Addition<>(parameters, summor);
		assertEquals(new Integer("222222222222222222222222222222222222222222222222222222222222222222222"), sum.evaluate(model));

		parameters.clear();
		parameters.add(new MemberIdentityFunction<>("1"));
		parameters.add(new MemberIdentityFunction<>("1"));
		parameters.add(new MemberIdentityFunction<>("1"));
		parameters.add(new MemberIdentityFunction<>("1"));

		assertEquals("(Σ 1 1 1 1)", sum.toString());

		sum = new Addition<>(parameters, summor);
		assertEquals("1 + 1 + 1 + 1 = 4", new Integer(4), sum.evaluate(model));

		parameters.clear();
		parameters.add(new MemberIdentityFunction<>("1"));
		List<ReflexiveFunction<Integer, ?>> subParameters = new ArrayList<>();
		subParameters.add(new MemberIdentityFunction<>("2"));
		subParameters.add(new MemberIdentityFunction<>("3"));
		parameters.add(new Addition<>(subParameters, summor));

		assertEquals("(1 + (2 + 3))", sum.toString());

		sum = new Addition<>(parameters, summor);
		assertEquals("1 + 2 + 3 = 6", new Integer(6), sum.evaluate(model));
	}
}
