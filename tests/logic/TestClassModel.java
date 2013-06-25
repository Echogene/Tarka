package logic;

import logic.model.AbstractModelImpl;
import logic.set.NamedSet;

/**
 * @author Steven Weston
 */
public class TestClassModel extends AbstractModelImpl<TestClass> {
	public TestClassModel() {
		this.universe   = new TestClassUniverse();
		this.functions  = new NamedSet<>("functions");
		this.evaluables = new NamedSet<>("evaluables");
	}
}
