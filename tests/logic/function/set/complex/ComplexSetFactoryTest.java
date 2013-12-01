package logic.function.set.complex;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.factory.FactoryTest;
import logic.function.Function;
import logic.function.evaluable.predicate.membership.MembershipPredicateFactory;
import logic.function.identity.EvaluableIdentityFunction;
import logic.function.identity.SetIdentityFunction;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class ComplexSetFactoryTest extends FactoryTest<TestClass, TestClassUniverse, ComplexSetFactory<TestClass>> {

	public ComplexSetFactoryTest() {
		super(new ComplexSetFactory<>(TestClass.class), new TestClassUniverse());
		reader.addFactory(new MembershipPredicateFactory<>(TestClass.class));
		universe.put("x");
		universe.put("y");
		universe.putSet("X", "x", "y");
		universe.putSet("Y", "y");
	}

	@Test
	public void testCreate() throws Exception {
		ComplexSet<TestClass> expected = new ComplexSet<TestClass>(
				"a",
				new SetIdentityFunction<>("X"),
				new EvaluableIdentityFunction<>("⊤")
		);

		Function<?,?> actual = reader.read("{a ∊ X | ⊤}");

		assertEquals(expected, actual);
	}

	@Test
	public void testCreateWithOtherFunction() throws Exception {
		ComplexSet<TestClass> expected = new ComplexSet<TestClass>(
				"a",
				new SetIdentityFunction<>("X"),
				MembershipPredicateFactory.createElement("a", "Y")
		);

		Function<?,?> actual = reader.read("{a ∊ X | (a ∊ Y)}");

		assertEquals(expected, actual);
	}
}
