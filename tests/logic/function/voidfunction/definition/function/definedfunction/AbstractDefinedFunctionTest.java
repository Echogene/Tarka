package logic.function.voidfunction.definition.function.definedfunction;

import logic.function.FunctionTest;
import logic.function.evaluable.predicate.equality.EqualityPredicateFactory;
import logic.function.identity.MemberIdentityFunction;
import logic.function.ifelse.ReflexiveIfElse;
import logic.function.maths.number.addition.Addition;
import logic.function.maths.number.subtraction.Subtraction;
import logic.function.reference.ReflexiveFunctionReference;
import maths.number.integer.Integer;
import maths.number.integer.IntegerSubtractor;
import maths.number.integer.IntegerSummor;
import maths.number.integer.model.IntegerModel;
import maths.number.integer.model.universe.IntegerUniverse;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class AbstractDefinedFunctionTest
		extends FunctionTest<Integer, IntegerUniverse, IntegerModel, AbstractDefinedFunction<Integer, ?, ?, ?>> {

	public AbstractDefinedFunctionTest() {
		super(new IntegerModel());
	}

	@Test
	public void testRecursiveDefinedFunction() throws Exception {
		ReflexiveFunctionReference<Integer> reference = new ReflexiveFunctionReference<>();
		ReflexiveIfElse<Integer> definition = new ReflexiveIfElse<>(
				EqualityPredicateFactory.createElement("a", "0"),
				new MemberIdentityFunction<Integer>("0"),
				reference
		);

		reference.setReferee(
				new Addition<Integer>(
						Arrays.asList(
								new DefinedReflexiveFunction<Integer>(
										"f",
										definition,
										Collections.singletonMap(
												"a",
												new Subtraction<Integer>(
														new MemberIdentityFunction<Integer>("a"),
														new MemberIdentityFunction<Integer>("1"),
														new IntegerSubtractor()
												)
										)
								),
								new MemberIdentityFunction<Integer>("1")
						),
						new IntegerSummor()
				)
		);

		function = new DefinedReflexiveFunction<>(
				"f",
				definition,
				Collections.singletonMap("a", new MemberIdentityFunction<Integer>("2"))
		);

		assertEquals(universe.get("2"), function.evaluate(model));
	}
}
