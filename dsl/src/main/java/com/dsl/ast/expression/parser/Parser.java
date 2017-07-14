package com.dsl.ast.expression.parser;

import java.util.HashMap;
import java.util.Map;

import com.dsl.ast.lexer.Lexer;

public class Parser {
	
	protected static Map<String,Object> referenceMap = new HashMap<>();
	
	protected static Map<String,Object> variableMap = new HashMap<>();
	
	protected static Lexer lexer;
	
	public static Object execute(String input) {
		lexer = new Lexer(input);
		
		return null;
	}
	
	public static void addVariable(String name,Object value){
		variableMap.put(name, value);
	}

	public static Object getVariable(String name){
		return variableMap.get(name);
	}
	
	public static void addReference(String name,Object value){
		referenceMap.put(name, value);
	}
}
