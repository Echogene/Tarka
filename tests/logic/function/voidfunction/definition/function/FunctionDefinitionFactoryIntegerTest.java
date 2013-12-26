package logic.function.voidfunction.definition.function;

import logic.factory.SimpleLogicReader;
import logic.function.voidfunction.definition.function.definedfunction.AbstractDefinedFunction;
import maths.number.integer.Integer;
import maths.number.integer.model.IntegerModel;
import maths.number.integer.model.universe.IntegerReader;
import maths.number.integer.model.universe.IntegerUniverse;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Steven Weston
 */
public class FunctionDefinitionFactoryIntegerTest {

	private final IntegerModel model;
	private final IntegerUniverse universe;
	private final SimpleLogicReader<Integer> reader;

	public FunctionDefinitionFactoryIntegerTest() {
		model = new IntegerModel();
		universe = model.getUniverse();
		reader = IntegerReader.createStandardReader(universe);
	}

	@Test
	@Ignore("Until multitype returns are implemented")
	public void testDefineIdentityFunction() throws Exception {
		FunctionDefinition<Integer, ?> definition = reader.read("(f x ≝ x)");
		definition.evaluate(model);

		AbstractDefinedFunction<Integer, ?> function = reader.read("(f 1)");
		assertEquals(universe.get("1"), function.evaluate(model));
	}

	@Test
	@Ignore("Until multitype returns are implemented")
	public void testDefineAssignment() throws Exception {
		FunctionDefinition<Integer, ?> definition = reader.read("(f x ≝ (y where y is x))");
		definition.evaluate(model);

		AbstractDefinedFunction<Integer, ?> function = reader.read("(f 1)");
		assertEquals(universe.get("1"), function.evaluate(model));
	}
}
