package logic.evaluable.statements.quantified.standard;

import logic.TestClass;
import logic.TestClassUniverse;
import logic.evaluable.Evaluable;
import logic.evaluable.constants.LogicalConstant;
import logic.evaluable.predicate.Predicate;
import logic.evaluable.predicate.equality.EqualityPredicateFactory;
import logic.set.NamedSet;
import org.junit.Test;

import static logic.evaluable.statements.quantified.standard.Quantifier.QuantifierType.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Steven Weston
 */
public class QuantifierTest {
	@Test
	public void testApply() throws Exception {
		NamedSet<TestClass> X = new NamedSet<>("X");

		TestClassUniverse universe = new TestClassUniverse();
		universe.setUniversalSet(X);

		Predicate<TestClass> predicate = EqualityPredicateFactory.createElement("x", "y");
		Evaluable<TestClass> contradiction = new LogicalConstant<>(false);

		Quantifier quantifier;
		// 100
		quantifier = new Quantifier(FOR_ALL);
		assertTrue(quantifier.apply("x", predicate, universe, universe.getValueSet()));
		quantifier = new Quantifier(NFOR_ALL);
		assertFalse(quantifier.apply("x", predicate, universe, universe.getValueSet()));
		quantifier = new Quantifier(EXISTS);
		assertFalse(quantifier.apply("x", predicate, universe, universe.getValueSet()));
		quantifier = new Quantifier(NEXISTS);
		assertTrue(quantifier.apply("x", predicate, universe, universe.getValueSet()));
		quantifier = new Quantifier(EXISTS_UNIQUE);
		assertFalse(quantifier.apply("x", predicate, universe, universe.getValueSet()));
		quantifier = new Quantifier(NEXISTS_UNIQUE);
		assertTrue(quantifier.apply("x", predicate, universe, universe.getValueSet()));

		X.put(new TestClass("y"));
		// 111
		quantifier = new Quantifier(FOR_ALL);
		assertTrue(quantifier.apply("x", predicate, universe, universe.getValueSet()));
		quantifier = new Quantifier(NFOR_ALL);
		assertFalse(quantifier.apply("x", predicate, universe, universe.getValueSet()));
		quantifier = new Quantifier(EXISTS);
		assertTrue(quantifier.apply("x", predicate, universe, universe.getValueSet()));
		quantifier = new Quantifier(NEXISTS);
		assertFalse(quantifier.apply("x", predicate, universe, universe.getValueSet()));
		quantifier = new Quantifier(EXISTS_UNIQUE);
		assertTrue(quantifier.apply("x", predicate, universe, universe.getValueSet()));
		quantifier = new Quantifier(NEXISTS_UNIQUE);
		assertFalse(quantifier.apply("x", predicate, universe, universe.getValueSet()));

		X.put(new TestClass("z"));
		// 011
		quantifier = new Quantifier(FOR_ALL);
		assertFalse(quantifier.apply("x", predicate, universe, universe.getValueSet()));
		quantifier = new Quantifier(NFOR_ALL);
		assertTrue(quantifier.apply("x", predicate, universe, universe.getValueSet()));
		quantifier = new Quantifier(EXISTS);
		assertTrue(quantifier.apply("x", predicate, universe, universe.getValueSet()));
		quantifier = new Quantifier(NEXISTS);
		assertFalse(quantifier.apply("x", predicate, universe, universe.getValueSet()));
		quantifier = new Quantifier(EXISTS_UNIQUE);
		assertTrue(quantifier.apply("x", predicate, universe, universe.getValueSet()));
		quantifier = new Quantifier(NEXISTS_UNIQUE);
		assertFalse(quantifier.apply("x", predicate, universe, universe.getValueSet()));

		X.put("w", new TestClass("y"));
		// 010
		quantifier = new Quantifier(FOR_ALL);
		assertFalse(quantifier.apply("x", predicate, universe, universe.getValueSet()));
		quantifier = new Quantifier(NFOR_ALL);
		assertTrue(quantifier.apply("x", predicate, universe, universe.getValueSet()));
		quantifier = new Quantifier(EXISTS);
		assertTrue(quantifier.apply("x", predicate, universe, universe.getValueSet()));
		quantifier = new Quantifier(NEXISTS);
		assertFalse(quantifier.apply("x", predicate, universe, universe.getValueSet()));
		quantifier = new Quantifier(EXISTS_UNIQUE);
		assertFalse(quantifier.apply("x", predicate, universe, universe.getValueSet()));
		quantifier = new Quantifier(NEXISTS_UNIQUE);
		assertTrue(quantifier.apply("x", predicate, universe, universe.getValueSet()));

		X = new NamedSet<>("universe");
		X.put(new TestClass("y"));
		X.put("z", new TestClass("y"));
		universe.setUniversalSet(X);
		// 110
		quantifier = new Quantifier(FOR_ALL);
		assertTrue(quantifier.apply("x", predicate, universe, universe.getValueSet()));
		quantifier = new Quantifier(NFOR_ALL);
		assertFalse(quantifier.apply("x", predicate, universe, universe.getValueSet()));
		quantifier = new Quantifier(EXISTS);
		assertTrue(quantifier.apply("x", predicate, universe, universe.getValueSet()));
		quantifier = new Quantifier(NEXISTS);
		assertFalse(quantifier.apply("x", predicate, universe, universe.getValueSet()));
		quantifier = new Quantifier(EXISTS_UNIQUE);
		assertFalse(quantifier.apply("x", predicate, universe, universe.getValueSet()));
		quantifier = new Quantifier(NEXISTS_UNIQUE);
		assertTrue(quantifier.apply("x", predicate, universe, universe.getValueSet()));

		X = new NamedSet<>("universe");
		X.put(new TestClass("y"));
		universe.setUniversalSet(X);
		// 000
		quantifier = new Quantifier(FOR_ALL);
		assertFalse(quantifier.apply("x", contradiction, universe, universe.getValueSet()));
		quantifier = new Quantifier(NFOR_ALL);
		assertTrue(quantifier.apply("x", contradiction, universe, universe.getValueSet()));
		quantifier = new Quantifier(EXISTS);
		assertFalse(quantifier.apply("x", contradiction, universe, universe.getValueSet()));
		quantifier = new Quantifier(NEXISTS);
		assertTrue(quantifier.apply("x", contradiction, universe, universe.getValueSet()));
		quantifier = new Quantifier(EXISTS_UNIQUE);
		assertFalse(quantifier.apply("x", contradiction, universe, universe.getValueSet()));
		quantifier = new Quantifier(NEXISTS_UNIQUE);
		assertTrue(quantifier.apply("x", contradiction, universe, universe.getValueSet()));
	}
}