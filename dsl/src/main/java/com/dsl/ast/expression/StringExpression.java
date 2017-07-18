package com.dsl.ast.expression;

public class StringExpression implements Expression {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -266462524732823342L;
	
	private String stringVal;

	public StringExpression(String stringVal) {
		super();
		this.stringVal = stringVal;
	}

	public String getStringVal() {
		return stringVal;
	}

	public void setStringVal(String stringVal) {
		this.stringVal = stringVal;
	}
	
	
}
