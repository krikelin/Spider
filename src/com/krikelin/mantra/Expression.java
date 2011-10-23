package com.krikelin.mantra;

import java.util.ArrayList;

import com.krikelin.mantra.token.Token;

public class Expression {
	public static ArrayList<Token> tokenTypes = new ArrayList<Token>();
	private Expression mExpression;
	private Expression mExpression2;
	private Operator mOperator;
	public Expression getExpression() {
		return mExpression;
	}
	public void setExpression(Expression mExpression) {
		this.mExpression = mExpression;
	}
	public Expression getExpression2() {
		return mExpression2;
	}
	public void setExpression2(Expression mExpression2) {
		this.mExpression2 = mExpression2;
	}
	public Operator getOperator() {
		return mOperator;
	}
	public void setOperator(Operator mOperator) {
		this.mOperator = mOperator;
	}
	/***
	 * Creates an new expression
	 * @param input
	 */
	public Expression(String input)
	{
		int x=0;
		Token currentToken = null;
		int switches = 0;
		for(int i=0; i < input.length(); i++)
		{
			String c = input.substring(i,i+1);
			
		}
	}
	
}
