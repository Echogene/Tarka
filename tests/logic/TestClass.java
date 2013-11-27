package logic;

/**
 * @author Steven Weston
 */
public class TestClass implements Nameable {

	private final String name;

	public TestClass(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public boolean equals(Object o) {
		if (!(o instanceof TestClass)) {
			return false;
		}
		TestClass other = (TestClass) o;
		return getName().equals(other.getName());
	}

	public String toString() {
		return getName();
	}
}
