package logic.function.evaluable.statements.quantified.standard;

import logic.TestClass;
import logic.TestClassModel;
import logic.TestClassUniverse;
import logic.function.evaluable.Evaluable;
import logic.function.evaluable.predicate.Predicate;
import logic.function.evaluable.predicate.equality.EqualityPredicateFactory;
import logic.function.identity.EvaluableIdentityFunction;
import logic.set.finite.StandardSet;
import org.junit.Test;

import static logic.function.evaluable.statements.quantified.standard.Quantifier.QuantifierType.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Steven Weston
 */
public class QuantifierTest {

	@Test
	public void testApply() throws Exception {
		StandardSet<TestClass> X = new StandardSet<>("X");

		TestClassModel model = new TestClassModel();
		TestClassUniverse universe = model.getUniverse();
		universe.setUniversalSet(X);

		Predicate<TestClass, ?> predicate = EqualityPredicateFactory.createElement("x", "y");
		Evaluable<TestClass, ?> contradiction = new EvaluableIdentityFunction<>(false);

		Quantifier quantifier;
		// 100
		quantifier = new Quantifier(FOR_ALL);
		assertTrue(quantifier.apply("x", predicate, model, universe.getUniversalSet()));
		quantifier = new Quantifier(NFOR_ALL);
		assertFalse(quantifier.apply("x", predicate, model, universe.getUniversalSet()));
		quantifier = new Quantifier(EXISTS);
		assertFalse(quantifier.apply("x", predicate, model, universe.getUniversalSet()));
		quantifier = new Quantifier(NEXISTS);
		assertTrue(quantifier.apply("x", predicate, model, universe.getUniversalSet()));
		quantifier = new Quantifier(EXISTS_UNIQUE);
		assertFalse(quantifier.apply("x", predicate, model, universe.getUniversalSet()));
		quantifier = new Quantifier(NEXISTS_UNIQUE);
		assertTrue(quantifier.apply("x", predicate, model, universe.getUniversalSet()));

		X.put(new TestClass("y"));
		// 111
		quantifier = new Quantifier(FOR_ALL);
		assertTrue(quantifier.apply("x", predicate, model, universe.getUniversalSet()));
		quantifier = new Quantifier(NFOR_ALL);
		assertFalse(quantifier.apply("x", predicate, model, universe.getUniversalSet()));
		quantifier = new Quantifier(EXISTS);
		assertTrue(quantifier.apply("x", predicate, model, universe.getUniversalSet()));
		quantifier = new Quantifier(NEXISTS);
		assertFalse(quantifier.apply("x", predicate, model, universe.getUniversalSet()));
		quantifier = new Quantifier(EXISTS_UNIQUE);
		assertTrue(quantifier.apply("x", predicate, model, universe.getUniversalSet()));
		quantifier = new Quantifier(NEXISTS_UNIQUE);
		assertFalse(quantifier.apply("x", predicate, model, universe.getUniversalSet()));

		X.put(new TestClass("z"));
		// 011
		quantifier = new Quantifier(FOR_ALL);
		assertFalse(quantifier.apply("x", predicate, model, universe.getUniversalSet()));
		quantifier = new Quantifier(NFOR_ALL);
		assertTrue(quantifier.apply("x", predicate, model, universe.getUniversalSet()));
		quantifier = new Quantifier(EXISTS);
		assertTrue(quantifier.apply("x", predicate, model, universe.getUniversalSet()));
		quantifier = new Quantifier(NEXISTS);
		assertFalse(quantifier.apply("x", predicate, model, universe.getUniversalSet()));
		quantifier = new Quantifier(EXISTS_UNIQUE);
		assertTrue(quantifier.apply("x", predicate, model, universe.getUniversalSet()));
		quantifier = new Quantifier(NEXISTS_UNIQUE);
		assertFalse(quantifier.apply("x", predicate, model, universe.getUniversalSet()));

		X.put("w", new TestClass("y"));
		// 010
		quantifier = new Quantifier(FOR_ALL);
		assertFalse(quantifier.apply("x", predicate, model, universe.getUniversalSet()));
		quantifier = new Quantifier(NFOR_ALL);
		assertTrue(quantifier.apply("x", predicate, model, universe.getUniversalSet()));
		quantifier = new Quantifier(EXISTS);
		assertTrue(quantifier.apply("x", predicate, model, universe.getUniversalSet()));
		quantifier = new Quantifier(NEXISTS);
		assertFalse(quantifier.apply("x", predicate, model, universe.getUniversalSet()));
		quantifier = new Quantifier(EXISTS_UNIQUE);
		assertFalse(quantifier.apply("x", predicate, model, universe.getUniversalSet()));
		quantifier = new Quantifier(NEXISTS_UNIQUE);
		assertTrue(quantifier.apply("x", predicate, model, universe.getUniversalSet()));

		X = new StandardSet<>("universe");
		X.put(new TestClass("y"));
		X.put("z", new TestClass("y"));
		universe.setUniversalSet(X);
		// 110
		quantifier = new Quantifier(FOR_ALL);
		assertTrue(quantifier.apply("x", predicate, model, universe.getUniversalSet()));
		quantifier = new Quantifier(NFOR_ALL);
		assertFalse(quantifier.apply("x", predicate, model, universe.getUniversalSet()));
		quantifier = new Quantifier(EXISTS);
		assertTrue(quantifier.apply("x", predicate, model, universe.getUniversalSet()));
		quantifier = new Quantifier(NEXISTS);
		assertFalse(quantifier.apply("x", predicate, model, universe.getUniversalSet()));
		quantifier = new Quantifier(EXISTS_UNIQUE);
		assertFalse(quantifier.apply("x", predicate, model, universe.getUniversalSet()));
		quantifier = new Quantifier(NEXISTS_UNIQUE);
		assertTrue(quantifier.apply("x", predicate, model, universe.getUniversalSet()));

		X = new StandardSet<>("universe");
		X.put(new TestClass("y"));
		universe.setUniversalSet(X);
		// 000
		quantifier = new Quantifier(FOR_ALL);
		assertFalse(quantifier.apply("x", contradiction, model, universe.getUniversalSet()));
		quantifier = new Quantifier(NFOR_ALL);
		assertTrue(quantifier.apply("x", contradiction, model, universe.getUniversalSet()));
		quantifier = new Quantifier(EXISTS);
		assertFalse(quantifier.apply("x", contradiction, model, universe.getUniversalSet()));
		quantifier = new Quantifier(NEXISTS);
		assertTrue(quantifier.apply("x", contradiction, model, universe.getUniversalSet()));
		quantifier = new Quantifier(EXISTS_UNIQUE);
		assertFalse(quantifier.apply("x", contradiction, model, universe.getUniversalSet()));
		quantifier = new Quantifier(NEXISTS_UNIQUE);
		assertTrue(quantifier.apply("x", contradiction, model, universe.getUniversalSet()));
	}
}
