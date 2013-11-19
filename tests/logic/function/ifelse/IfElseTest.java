package logic.function.ifelse;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.function.evaluable.Evaluable;
import logic.function.voidfunction.definition.member.MemberDefinition;
import logic.identity.IdentityFunction;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Steven Weston
 */
public class IfElseTest {

	@Test
	public void testEvaluate() throws Exception {
		TestClassUniverse universe = new TestClassUniverse();
		universe.put("0");
		universe.put("1");

		Evaluable<TestClass> condition;
		MemberDefinition<TestClass> ifTrue;
		MemberDefinition<TestClass> ifFalse;
		VoidIfElse<TestClass> ifElse;

		condition = new LogicalConstant<>(true);
		ifTrue = new MemberDefinition<>("x", new IdentityFunction<>("1"));
		ifFalse = new MemberDefinition<>("x", new IdentityFunction<>("0"));
		ifElse = new VoidIfElse<>(condition, ifTrue, ifFalse);
		ifElse.evaluate(universe);
		assertTrue(universe.getVariables().contains("x"));
		assertEquals(new TestClass("1"), universe.getVariables().get("x"));

		condition = new LogicalConstant<>(false);
		ifTrue = new MemberDefinition<>("y", new IdentityFunction<>("1"));
		ifFalse = new MemberDefinition<>("y", new IdentityFunction<>("0"));
		ifElse = new VoidIfElse<>(condition, ifTrue, ifFalse);
		ifElse.evaluate(universe);
		assertTrue(universe.getVariables().contains("y"));
		assertEquals(new TestClass("0"), universe.getVariables().get("y"));
	}
}
