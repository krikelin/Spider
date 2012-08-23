package com.krikelin.spider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import org.keplerproject.luajava.JavaFunction;
import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaObject;
import org.keplerproject.luajava.LuaState; 
import org.keplerproject.luajava.LuaStateFactory;


public class LuaEngine implements IScriptEngine {
	private MakoEngine context;
	

	LuaState L =LuaStateFactory.newLuaState();
	private String output = "";
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	
	public LuaEngine(MakoEngine context) {
		this.context = context;
		L.newTable();
		L.openLibs();
		
		try {
			JavaFunction func = new JavaFunction(L) {
				
				@Override
				public int execute() throws LuaException {
					StringBuilder sb = new StringBuilder();
			        for (int i = 2; i <= L.getTop(); i++) {
			            int type = L.type(i);
			            String val = L.toString(i);
			            if (val == null)
			                val = L.typeName(type);
			            sb.append(val);
			            sb.append("\t");
			        }
			        sb.append("\n");
			       LuaEngine.this.output += (sb.toString());
					return 0;
				}
			};
			func.register("__printx");
		} catch (LuaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Writer writer = new Writer() {

		@Override
		public void close() throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void flush() throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void write(char[] arg0, int arg1, int arg2) throws IOException {
			// TODO Auto-generated method stub
			LuaEngine.this.output+=arg0;
		}
		
	};
	ScriptContext c = new SimpleScriptContext() {
		@Override
		public Writer getWriter() {
			return LuaEngine.this.writer;
		}	
	};
	
	
	@Override
	public String run(String scriptCode) {
		// TODO Auto-generated method stub
		try {
			final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
			System.setOut(new PrintStream(myOut));
			scriptCode = "sys = luajava.bindClass(\"java.lang.System\")\n" + scriptCode;
			int x = L.LdoString(scriptCode);
	
			 String f =  output.replace("%BR%","\n").replace("¤","\"");
			 
			 output = "";
			 f.replace("¤Ä","\"").replace("%BR%", "\n");
			
			return f;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			
			e1.printStackTrace();
			return "";
		}
		
		 
	}

	@Override
	public Object invoke(String Function, Object[] arguments) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVariable(String variableName, Object varInstance) {
		// TODO Auto-generated method stub
		
		try {
			L.setGlobal(variableName);
		
			L.pushObjectValue(varInstance);
		} catch (LuaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setFunction(String functionName, final Delegate functionPointer) {
		// TODO Auto-generated method stub
		try {
			
			L.pushJavaFunction(new JavaFunction(L) {
				
				@Override
				public int execute() throws LuaException {
					// TODO Auto-generated method stub
					functionPointer.invoke(getParam(0));
					return 1;
				}
			});
		} catch (SecurityException e1) {
		
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (LuaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
