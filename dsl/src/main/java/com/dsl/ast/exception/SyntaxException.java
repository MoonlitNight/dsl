package com.dsl.ast.exception;

public class SyntaxException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4331579928128455492L;
	
	private static final String MESSAGE_FORMAT = "Syntax error! line=%d,pos=%d.";

	public SyntaxException(){
		 super();
	}
	
	public SyntaxException(Integer line,Integer pos){
		super(String.format(MESSAGE_FORMAT, line,pos));
	}
	
	public SyntaxException(String msg,Integer line,Integer pos){
		super(msg.concat("\n"+String.format(MESSAGE_FORMAT, line,pos)));
	}
	
	public SyntaxException(String msg) {
		super(msg);
	}
	
	
}
