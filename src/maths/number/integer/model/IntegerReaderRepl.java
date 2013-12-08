package maths.number.integer.model;

import logic.function.Function;
import logic.function.evaluable.Evaluable;
import logic.function.reflexive.ReflexiveFunction;
import logic.function.set.SetFunction;
import logic.function.voidfunction.VoidFunction;
import logic.set.Set;
import logic.set.finite.FiniteSet;
import maths.number.integer.Integer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Steven Weston
 */
public class IntegerReaderRepl {

	private static final IntegerModel model = new IntegerModel();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String in;
		while (true) {
			in =  br.readLine();
			if ("exit".equals(in)) {
				return;
			}
			if (in.isEmpty()) {
				continue;
			}
			evaluateInput(in);
		}
	}

	private static void evaluateInput(String in) {
		try {
			Function<?, ?> function = model.getReader().read(in);
			if (function instanceof ReflexiveFunction) {
				ReflexiveFunction<Integer> reflexiveFunction = (ReflexiveFunction<Integer>) function;
				System.out.println(reflexiveFunction.evaluate(model));
			} else if (function instanceof Evaluable) {
				Evaluable<Integer> evaluable = (Evaluable<Integer>) function;
				System.out.println(evaluable.evaluate(model));
			} else if (function instanceof SetFunction) {
				SetFunction<Integer> setFunction = (SetFunction<Integer>) function;
				Set<Integer> set = setFunction.evaluate(model);
				if (set instanceof FiniteSet) {
					FiniteSet<Integer> finiteSet = (FiniteSet<Integer>) set;
					StringBuilder sb = new StringBuilder();
					sb.append("{");
					Boolean first = true;
					for (Integer i : finiteSet) {
						if (!first) {
							sb.append(" ");
						}
						sb.append(i);
						first = false;
					}
					sb.append("}");
					System.out.println(sb.toString());
				} else {
					System.out.println(set);
				}
			} else if (function instanceof VoidFunction) {
				VoidFunction<Integer> voidFunction = (VoidFunction<Integer>) function;
				voidFunction.evaluate(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
