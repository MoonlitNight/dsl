package com.dsl.ast.exception;

/**
 * 调用异常
 * @author jin.wang
 *
 */
public class InvokeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6688384537051172929L;

	public InvokeException(){
		super();
	}
	
	public InvokeException(String msg){
		super(msg);
	}
	
}
