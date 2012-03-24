package com.krikelin.spider;

import org.keplerproject.luajava.JavaFunction;
import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaJavaAPI;
import org.keplerproject.luajava.LuaObject;
import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

public class LuaEngine implements IScriptEngine {
	LuaObject baseObject;
	LuaState L = LuaStateFactory.newLuaState();
	
	public LuaEngine() {
		
	 
	    
	   
	}
	@Override
	public String run(String scriptCode) {
		// TODO Auto-generated method stub
		 L.LdoString(scriptCode);
		return L.getLuaObject("spider").call(new Object(){});
	}

	@Override
	public Object invoke(String Function, Object[] arguments) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVariable(String variableName, Object varInstance) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFunction(String functionName, final Delegate functionPointer) {
		// TODO Auto-generated method stub
		
	}

}
