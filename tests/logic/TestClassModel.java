package logic;

import logic.model.AbstractModelImpl;
import logic.set.FiniteSet;

/**
 * @author Steven Weston
 */
public class TestClassModel extends AbstractModelImpl<TestClass> {
	public TestClassModel() {
		this.universe   = new TestClassUniverse();
		this.functions  = new FiniteSet<>("functions");
		this.evaluables = new FiniteSet<>("evaluables");
	}
}
