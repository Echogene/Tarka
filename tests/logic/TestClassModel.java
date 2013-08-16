package logic;

import logic.model.AbstractModelImpl;
import logic.set.finite.StandardSet;

/**
 * @author Steven Weston
 */
public class TestClassModel extends AbstractModelImpl<TestClass> {
	public TestClassModel() {
		this.universe   = new TestClassUniverse();
		this.functions  = new StandardSet<>("functions");
		this.evaluables = new StandardSet<>("evaluables");
	}
}
