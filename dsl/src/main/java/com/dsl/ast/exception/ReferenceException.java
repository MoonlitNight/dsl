package com.dsl.ast.exception;

public class ReferenceException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4532083406854635822L;
	
	public static final String REFERENCE_NOT_EXIST="reference %s not exist!";

	public ReferenceException(){
		 super();
	}
	
	public ReferenceException(String message){
		 super(message);
	}
}
