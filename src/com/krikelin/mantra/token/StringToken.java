package com.krikelin.mantra.token;

public class StringToken implements Token {

	@Override
	public Object getData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isOfToken(String c) {
		// TODO Auto-generated method stub
		if (c.matches("a-zA-Z")){
			return true;
		}
		return false;
	}

}
