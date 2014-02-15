package logic.function.voidfunction.definition.function.definedfunction;

import logic.function.FunctionTest;
import logic.function.evaluable.predicate.equality.EqualityPredicateFactory;
import logic.function.identity.MemberIdentityFunction;
import logic.function.ifelse.ReflexiveIfElse;
import logic.function.maths.number.addition.Addition;
import logic.function.maths.number.subtraction.Subtraction;
import logic.function.reference.ReflexiveFunctionReference;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.voidfunction.definition.function.ReflexiveFunctionDefinition;
import maths.number.integer.Integer;
import maths.number.integer.IntegerSubtractor;
import maths.number.integer.IntegerSummor;
import maths.number.integer.model.IntegerModel;
import maths.number.integer.model.universe.IntegerUniverse;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;

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
		ReflexiveFunctionReference<Integer> reference = new ReflexiveFunctionReference<>(
				"f",
				Arrays.asList(new Subtraction<Integer>(
						new MemberIdentityFunction<Integer>("a"),
						new MemberIdentityFunction<Integer>("1"),
						new IntegerSubtractor()
				))
		);

		ReflexiveIfElse<Integer> definition = new ReflexiveIfElse<>(
				EqualityPredicateFactory.createElement("a", "0"),
				new MemberIdentityFunction<Integer>("0"),
				new Addition<Integer>(
						Arrays.asList(
								reference,
								new MemberIdentityFunction<Integer>("1")
						),
						new IntegerSummor()
				)
		);

		LinkedHashMap<String, Set<Type>> parameters = new LinkedHashMap<>(1);
		parameters.put("a", Collections.singleton(Integer.class));
		ReflexiveFunctionDefinition<Integer> reflexiveFunctionDefinition = new ReflexiveFunctionDefinition<>(
				"f",
				parameters,
				definition
		);
		reflexiveFunctionDefinition.evaluate(model);

		reference.setFactory(
				(AbstractDefinedFunctionFactory<Integer, Integer, DefinedReflexiveFunction<Integer>, ReflexiveFunction<Integer,?>>) model.getDefinedFunctionFactory("f")
		);

		function = new DefinedReflexiveFunction<>(
				"f",
				definition,
				Collections.singletonMap("a", new MemberIdentityFunction<Integer>("6"))
		);

		assertEquals(universe.get("6"), function.evaluate(model));
	}
}
