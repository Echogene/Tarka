package logic.function.evaluable.predicate.equality;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.factory.FactoryTest;
import logic.function.identity.EvaluableIdentityFunction;
import logic.function.identity.SetIdentityFunction;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class EqualityPredicateFactoryTest extends FactoryTest<TestClass, TestClassUniverse, EqualityPredicateFactory<TestClass>> {

	public EqualityPredicateFactoryTest() {
		super(new EqualityPredicateFactory<>(TestClass.class), new TestClassUniverse());
		universe.put("x");
		universe.put("y");
		universe.putSet("X", "x");
		universe.putSet("Y", "y");
	}

	@Test
	public void testCreateWithUniverseMembers() throws Exception {
		EqualityPredicate<TestClass> expected = EqualityPredicateFactory.createElement("x", "y");
		EqualityPredicate<TestClass> actual = (EqualityPredicate<TestClass>) reader.read("(x = y)");

		assertEquals(expected, actual);
	}

	@Test
	public void testCreateWithSets() throws Exception {
		EqualityPredicate<TestClass> expected = new EqualityPredicate<TestClass>(
				new SetIdentityFunction<TestClass>("X"),
				new SetIdentityFunction<TestClass>("Y")
		);
		EqualityPredicate<TestClass> actual = (EqualityPredicate<TestClass>) reader.read("(X = Y)");

		assertEquals(expected, actual);
	}

	@Test
	public void testCreateWithEvaluables() throws Exception {
		EqualityPredicate<TestClass> expected = new EqualityPredicate<TestClass>(
				new EvaluableIdentityFunction<TestClass>("⊤"),
				new EvaluableIdentityFunction<TestClass>("⊥")
		);
		EqualityPredicate<TestClass> actual = (EqualityPredicate<TestClass>) reader.read("(⊤ = ⊥)");

		assertEquals(expected, actual);
	}
}
