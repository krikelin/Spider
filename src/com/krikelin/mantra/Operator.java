package com.krikelin.mantra;

/***
 * An operation in the mantra
 * @author Alexander
 *
 */
public interface Operator {
	/**
	 * Operate of the operation
	 * @param i1
	 * @param i2
	 * @return
	 */
	public Object operate(Object i1, Object i2);

	
}
