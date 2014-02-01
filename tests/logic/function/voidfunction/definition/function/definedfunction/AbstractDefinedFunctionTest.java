package logic.function.voidfunction.definition.function.definedfunction;

import logic.function.FunctionTest;
import logic.function.evaluable.predicate.equality.EqualityPredicateFactory;
import logic.function.identity.MemberIdentityFunction;
import logic.function.ifelse.AbstractIfElse;
import logic.function.ifelse.ReflexiveIfElse;
import maths.number.integer.Integer;
import maths.number.integer.IntegerSubtractor;
import maths.number.integer.IntegerSummor;
import maths.number.integer.functions.addition.Addition;
import maths.number.integer.functions.subtraction.Subtraction;
import maths.number.integer.model.IntegerModel;
import maths.number.integer.model.universe.IntegerUniverse;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Field;
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
	@Ignore("Fix the stack overflow")
	public void testRecursiveDefinedFunction() throws Exception {
		ReflexiveIfElse<Integer> definition = new ReflexiveIfElse<>(
				EqualityPredicateFactory.createElement("a", "0"),
				new MemberIdentityFunction<Integer>("0"),
				null
		);

		Field field = AbstractIfElse.class.getDeclaredField("ifFalse");
		field.setAccessible(true);
		field.set(definition,
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
