package logic;

import logic.factory.SimpleLogicReaderImpl;
import logic.function.evaluable.constants.LogicalConstantFactory;
import logic.function.evaluable.predicate.equality.EqualityPredicateFactory;
import logic.function.evaluable.predicate.membership.MembershipPredicateFactory;
import logic.function.evaluable.statements.binary.BinaryStatementFactory;
import logic.function.evaluable.statements.quantified.restricted.RestrictedQuantifiedStatementFactory;
import logic.function.evaluable.statements.quantified.standard.QuantifiedStatementFactory;
import logic.function.evaluable.statements.unary.UnaryStatementFactory;
import logic.function.factory.FunctionFactory;
import logic.function.ifelse.IfElseFactory;
import logic.function.reflexive.assignment.ReflexiveAssignmentFactory;
import logic.function.reflexive.identity.IdentityFunctionFactory;
import logic.function.set.simple.SimpleSetFactory;
import logic.function.set.union.BinaryUnionFactory;
import logic.function.set.union.MultaryUnionFactory;
import logic.function.voidfunction.definition.member.MemberDefinitionFactory;
import logic.model.universe.Universe;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Steven Weston
 */
public class StandardReader {
	public static <T extends Nameable> SimpleLogicReaderImpl<T> createStandardReader(Universe<T> universe) {
		List<FunctionFactory<T, ?, ?>> factories = getStandardFunctionFactories();
		return new SimpleLogicReaderImpl<>(factories, universe);
	}

	public static <T extends Nameable> List<FunctionFactory<T, ?, ?>> getStandardFunctionFactories() {
		List<FunctionFactory<T, ?, ?>> output = new ArrayList<>();
		output.add(new IdentityFunctionFactory<>());
		output.add(new EqualityPredicateFactory<>());
		output.add(new MembershipPredicateFactory<>());
		output.add(new BinaryStatementFactory<>());
		output.add(new LogicalConstantFactory<>());
		output.add(new UnaryStatementFactory<>());
		output.add(new QuantifiedStatementFactory<>());
		output.add(new BinaryUnionFactory<>());
		output.add(new MultaryUnionFactory<>());
		output.add(new ReflexiveAssignmentFactory<>());
		output.add(new RestrictedQuantifiedStatementFactory<>());
		output.add(new MemberDefinitionFactory<>());
		output.add(new SimpleSetFactory<>());
		output.add(new IfElseFactory<>());
		return output;
	}
}
