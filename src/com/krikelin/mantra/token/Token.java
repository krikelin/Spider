package com.krikelin.mantra.token;
/**
 * An token
 * @author Alexander
 *
 */
public interface Token {
	public static final int TYPE_NUMBER = 0;
	public static final int TYPE_STRING = 1;
	public static final int TYPE_OBJECT = 2;
	
	/**
	 * The data of the token
	 * @return
	 */
	public Object getData();
	public boolean isOfToken(String c);
	
}
