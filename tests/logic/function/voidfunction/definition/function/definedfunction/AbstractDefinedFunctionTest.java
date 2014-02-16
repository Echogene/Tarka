package logic.function.voidfunction.definition.function.definedfunction;

import logic.function.FunctionTest;
import logic.function.evaluable.predicate.equality.EqualityPredicate;
import logic.function.evaluable.predicate.equality.EqualityPredicateFactory;
import logic.function.identity.MemberIdentityFunction;
import logic.function.ifelse.ReflexiveIfElse;
import logic.function.maths.number.addition.Addition;
import logic.function.maths.number.addition.BinaryAdditionFactory;
import logic.function.maths.number.subtraction.SubtractionFactory;
import logic.function.reference.ReflexiveFunctionReference;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.voidfunction.definition.function.ReflexiveFunctionDefinition;
import maths.number.integer.Integer;
import maths.number.integer.IntegerSubtractor;
import maths.number.integer.IntegerSummor;
import maths.number.integer.model.IntegerModel;
import maths.number.integer.model.universe.IntegerUniverse;
import org.junit.Test;
import util.MapUtils;

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

	private SubtractionFactory<Integer> subtractionFactory;
	private BinaryAdditionFactory<Integer> additionFactory;

	public AbstractDefinedFunctionTest() {
		super(new IntegerModel());
		IntegerSubtractor subtractor = new IntegerSubtractor();
		subtractionFactory = new SubtractionFactory<>(subtractor, Integer.class);
		IntegerSummor summor = new IntegerSummor();
		additionFactory = new BinaryAdditionFactory<>(summor, Integer.class);
	}

	@Test
	public void testRecursiveDefinedFunction() throws Exception {

		// (f (a - 1))
		ReflexiveFunctionReference<Integer> reference = new ReflexiveFunctionReference<>(
				"f",
				Arrays.asList(subtractionFactory.create("a", 1))
		);

		// (a = 0)
		EqualityPredicate<Integer> aEquals0 = EqualityPredicateFactory.createElement("a", "0");

		// ((f (a - 1)) + 1)
		Addition<Integer> integerAddition = additionFactory.create(reference, 1);

		// (0 if (a = 0) otherwise ((f (a - 1)) + 1))
		ReflexiveIfElse<Integer> definition = new ReflexiveIfElse<>(
				aEquals0,
				new MemberIdentityFunction<Integer>("0"),
				integerAddition
		);

		LinkedHashMap<String, Set<Type>> parameters
				= MapUtils.createLinkedHashMap("a", Collections.singleton(Integer.class));

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
