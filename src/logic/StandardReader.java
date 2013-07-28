package logic;

import logic.evaluable.constants.LogicalConstantFactory;
import logic.evaluable.predicate.equality.EqualityPredicateFactory;
import logic.evaluable.predicate.membership.MembershipPredicateFactory;
import logic.evaluable.statements.binary.BinaryStatementFactory;
import logic.evaluable.statements.quantified.restricted.RestrictedQuantifiedStatementFactory;
import logic.evaluable.statements.quantified.standard.QuantifiedStatementFactory;
import logic.evaluable.statements.unary.UnaryStatementFactory;
import logic.factory.SimpleLogicReaderImpl;
import logic.function.factory.FunctionFactory;
import logic.function.reflexive.assignment.ReflexiveAssignmentFactory;
import logic.function.reflexive.identity.IdentityFunctionFactory;
import logic.function.set.identity.SetIdentityFunctionFactory;
import logic.function.set.union.UnionFactory;
import logic.function.voidfunction.definition.member.MemberDefinitionFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Steven Weston
 */
public class StandardReader {
	public static <T extends Nameable> SimpleLogicReaderImpl<T> createStandardReader() {
		List<FunctionFactory<?, ?>> factories = getStandardFunctionFactories();
		return new SimpleLogicReaderImpl<>(factories);
	}

	public static <T extends Nameable> List<FunctionFactory<?, ?>> getStandardFunctionFactories() {
		List<FunctionFactory<?, ?>> output = new ArrayList<>();
		output.add(new EqualityPredicateFactory<>());
		output.add(new MembershipPredicateFactory<>());
		output.add(new SetIdentityFunctionFactory<>());
		output.add(new IdentityFunctionFactory<T>());
		output.add(new BinaryStatementFactory<>());
		output.add(new LogicalConstantFactory<>());
		output.add(new UnaryStatementFactory<>());
		output.add(new QuantifiedStatementFactory<>());
		output.add(new UnionFactory<>());
		output.add(new ReflexiveAssignmentFactory<T>());
		output.add(new RestrictedQuantifiedStatementFactory<>());
		output.add(new MemberDefinitionFactory<>());
		return output;
	}
}
