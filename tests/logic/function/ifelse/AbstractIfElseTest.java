package logic.function.ifelse;

import logic.TestClass;
import logic.TestClassModel;
import logic.TestClassUniverse;
import logic.function.evaluable.Evaluable;
import logic.function.identity.EvaluableIdentityFunction;
import logic.function.identity.MemberIdentityFunction;
import logic.function.voidfunction.definition.constant.MemberDefinition;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Steven Weston
 */
public class AbstractIfElseTest {

	@Test
	public void testEvaluate() throws Exception {
		TestClassModel model = new TestClassModel();
		TestClassUniverse universe = model.getUniverse();
		universe.put("0");
		universe.put("1");

		Evaluable<TestClass, ?> condition;
		MemberDefinition<TestClass> ifTrue;
		MemberDefinition<TestClass> ifFalse;
		VoidIfElse<TestClass> ifElse;

		condition = new EvaluableIdentityFunction<>(true);
		ifTrue = new MemberDefinition<>("x",  new MemberIdentityFunction<>("1"));
		ifFalse = new MemberDefinition<>("x", new MemberIdentityFunction<>("0"));
		ifElse = new VoidIfElse<>(ifTrue, condition, ifFalse);
		ifElse.evaluate(model);
		assertTrue(universe.getVariables().contains("x"));
		assertEquals(new TestClass("1"), universe.getVariables().get("x"));

		condition = new EvaluableIdentityFunction<>(false);
		ifTrue = new MemberDefinition<>("y",  new MemberIdentityFunction<>("1"));
		ifFalse = new MemberDefinition<>("y", new MemberIdentityFunction<>("0"));
		ifElse = new VoidIfElse<>(ifTrue, condition, ifFalse);
		ifElse.evaluate(model);
		assertTrue(universe.getVariables().contains("y"));
		assertEquals(new TestClass("0"), universe.getVariables().get("y"));
	}
}
