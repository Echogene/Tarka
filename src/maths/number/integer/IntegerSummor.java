package maths.number.integer;

import maths.number.Summor;

import java.math.BigInteger;
import java.util.Collection;

/**
 * @author Steven Weston
 */
public class IntegerSummor implements Summor<Integer> {
	@Override
	public Integer add(Integer augend, Integer addend) {
		return new Integer(augend.value.add(addend.value));
	}

	@Override
	public Integer sum(Collection<Integer> summands) {
		BigInteger output = new BigInteger("0");
		for (Integer summand : summands) {
			output = output.add(summand.value);
		}
		return new Integer(output);
	}
}
