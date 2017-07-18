package com.dsl.ast.exception;

public class ParseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1057066893314546843L;

	public ParseException(){
		super();
	}
	
	public ParseException(String msg){
		super(msg);
	}
	
}
