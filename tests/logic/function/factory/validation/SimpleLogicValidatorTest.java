package logic.function.factory.validation;

import logic.TestClass;
import logic.factory.FactoryTest;
import logic.function.factory.FunctionFactory;
import logic.function.factory.ValidationException;
import logic.function.factory.validation.group.GroupValidator;
import logic.function.factory.validation.group.TokenGroup;
import logic.function.factory.validation.group.validators.DisjunctiveValidator;
import logic.function.factory.validation.group.validators.FunctionAtom;
import logic.function.factory.validation.group.validators.OperatorAtom;
import logic.function.factory.validation.results.FunctionResult;
import logic.function.factory.validation.results.StringResult;
import logic.function.factory.validation.results.ValidationResult;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.reflexive.identity.IdentityFunctionFactory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static logic.function.factory.validation.GroupValidatorWithNumber.Number.MANY;
import static logic.function.factory.validation.GroupValidatorWithNumber.Number.ONE;
import static org.junit.Assert.*;

/**
 * @author Steven Weston
 */
public class SimpleLogicValidatorTest extends FactoryTest<FunctionFactory<?, ?, ?>> {

	public SimpleLogicValidatorTest() {
		functionFactory = new IdentityFunctionFactory<TestClass>();
	}

	@Test
	public void testValidate() throws Exception {
		SimpleLogicValidator validator = new SimpleLogicValidator(
				asList("("),
				asList(")")
		);

		validator.addValidator(ONE, new VariableAtom());
		validator.addValidator(ONE, new OperatorAtom(asList("+")));
		validator.addValidator(ONE, new VariableAtom());

		setUpTokens("(2 + 3)");
		functions = new ArrayList<>();
		List<ValidationResult> results = validator.validate(
				tokens,
				functions
		);
		StringResult open = (StringResult) results.get(0);
		assertEquals("(", open.getString());
		StringResult two = (StringResult) results.get(1);
		assertEquals("2", two.getString());
		StringResult plus = (StringResult) results.get(2);
		assertEquals("+", plus.getString());
		StringResult three = (StringResult) results.get(3);
		assertEquals("3", three.getString());
		StringResult close = (StringResult) results.get(4);
		assertEquals(")", close.getString());
	}

	@Test
	public void testValidateWithFunction() throws Exception {
		SimpleLogicValidator validator = new SimpleLogicValidator(
				asList("("),
				asList(")")
		);

		validator.addValidator(ONE, new FunctionAtom(asList("("), Arrays.<Class>asList(ReflexiveFunction.class), asList(")")));
		validator.addValidator(ONE, new OperatorAtom(asList("+")));
		validator.addValidator(ONE, new VariableAtom());

		setUpTokens("(() + 3)");
		setUpFunctions("(2)");
		List<ValidationResult> results = validator.validate(
				tokens,
				functions
		);
		StringResult open = (StringResult) results.get(0);
		assertEquals("(", open.getString());
		FunctionResult two = (FunctionResult) results.get(1);
		assertEquals(functions.get(0), two.getFunction());
		StringResult plus = (StringResult) results.get(2);
		assertEquals("+", plus.getString());
		StringResult three = (StringResult) results.get(3);
		assertEquals("3", three.getString());
		StringResult close = (StringResult) results.get(4);
		assertEquals(")", close.getString());
	}

	@Test
	public void testValidateWithFunctions() throws Exception {
		SimpleLogicValidator validator = new SimpleLogicValidator(
				asList("("),
				asList(")")
		);

		validator.addValidator(
				ONE, new FunctionAtom(
						asList("("),
						Arrays.<Class>asList(ReflexiveFunction.class),
						asList(")")
				)
		);
		validator.addValidator(ONE, new OperatorAtom(asList("+")));
		validator.addValidator(
				ONE, new FunctionAtom(
						asList("("),
						Arrays.<Class>asList(ReflexiveFunction.class),
						asList(")")
				)
		);

		setUpTokens("(() + ())");
		setUpFunctions("(2)", "(3)");
		List<ValidationResult> results = validator.validate(
				tokens,
				functions
		);
		StringResult open = (StringResult) results.get(0);
		assertEquals("(", open.getString());
		FunctionResult two = (FunctionResult) results.get(1);
		assertEquals(functions.get(0), two.getFunction());
		StringResult plus = (StringResult) results.get(2);
		assertEquals("+", plus.getString());
		FunctionResult three = (FunctionResult) results.get(3);
		assertEquals(functions.get(1), three.getFunction());
		StringResult close = (StringResult) results.get(4);
		assertEquals(")", close.getString());
	}@Test
	public void testValidateWithManyFunctions() throws Exception {
		SimpleLogicValidator validator = new SimpleLogicValidator(
				asList("("),
				asList(")")
		);

		validator.addValidator(ONE, new OperatorAtom(asList("Σ")));
		validator.addValidator(
				MANY, new DisjunctiveValidator(
						Arrays.<GroupValidator>asList(
								new VariableAtom(),
								new FunctionAtom(
										asList("("),
										Arrays.<Class>asList(ReflexiveFunction.class),
										asList(")")
								)
						)
				)
		);

		setUpTokens("(Σ 1 2 3 () 5 ())");
		setUpFunctions("(4)", "(6)");
		List<ValidationResult> results = validator.validate(
				tokens,
				functions
		);
		StringResult open = (StringResult) results.get(0);
		assertEquals("(", open.getString());
		StringResult sigma = (StringResult) results.get(1);
		assertEquals("Σ", sigma.getString());
		StringResult one = (StringResult) results.get(2);
		assertEquals("1", one.getString());
		StringResult two = (StringResult) results.get(3);
		assertEquals("2", two.getString());
		StringResult three = (StringResult) results.get(4);
		assertEquals("3", three.getString());
		FunctionResult four = (FunctionResult) results.get(5);
		assertEquals(functions.get(0), four.getFunction());
		StringResult five = (StringResult) results.get(6);
		assertEquals("5", five.getString());
		FunctionResult six = (FunctionResult) results.get(7);
		assertEquals(functions.get(1), six.getFunction());
		StringResult close = (StringResult) results.get(8);
		assertEquals(")", close.getString());
	}

	@Test
	public void testValidateOpeningAndClosingBrackets() throws Exception {
		SimpleLogicValidator validator = new SimpleLogicValidator(
				asList("(", "["),
				asList(")", "]")
		);
		setUpTokens("()");
		functions = new ArrayList<>();
		validator.validate(tokens, functions);
		setUpTokens("[]");
		functions = new ArrayList<>();
		validator.validate(tokens, functions);
		setUpTokens("[)");
		functions = new ArrayList<>();
		try {
			validator.validate(tokens, functions);
			fail();
		} catch (ValidationException e) {}
	}

		@Test
	public void testGroupTokens() throws Exception {
		SimpleLogicValidator validator = new SimpleLogicValidator(null, null);
		List<TokenGroup> groups;

		setUpTokens("x () ∨ ∀");
		groups = validator.groupTokens(tokens);
		assertEquals(4, groups.size());
		assertTrue(groups.get(0).representsName());
		assertTrue(groups.get(1).representsFunction());
		assertTrue(groups.get(2).representsOperator());
		assertTrue(groups.get(3).representsQuantifier());

		setUpTokens("x [) ∨ ∀");
		groups = validator.groupTokens(tokens);
		assertEquals(4, groups.size());
		assertTrue(groups.get(0).representsName());
		assertTrue(groups.get(1).representsFunction());
		assertTrue(groups.get(2).representsOperator());
		assertTrue(groups.get(3).representsQuantifier());

		setUpTokens("x {} ∨ ∀");
		groups = validator.groupTokens(tokens);
		assertEquals(4, groups.size());
		assertTrue(groups.get(0).representsName());
		assertTrue(groups.get(1).representsFunction());
		assertTrue(groups.get(2).representsOperator());
		assertTrue(groups.get(3).representsQuantifier());
	}
}
