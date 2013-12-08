package logic;

import logic.factory.SimpleLogicReader;
import logic.model.AbstractModel;

/**
 * @author Steven Weston
 */
public class TestClassModel extends AbstractModel<TestClass, TestClassUniverse> {

	public TestClassModel() {
		super(createUniverse());
	}

	public TestClassModel(SimpleLogicReader<TestClass> reader) {
		super(createUniverse());
		setReader(reader);
	}

	private static TestClassUniverse createUniverse() {
		return new TestClassUniverse();
	}
}
