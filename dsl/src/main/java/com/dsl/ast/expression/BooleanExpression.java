package com.dsl.ast.expression;

public class BooleanExpression {
	
	private Boolean booleanVal;

	public BooleanExpression(Boolean booleanVal) {
		super();
		this.booleanVal = booleanVal;
	}

	public Boolean getBooleanVal() {
		return booleanVal;
	}

	public void setBooleanVal(Boolean booleanVal) {
		this.booleanVal = booleanVal;
	}
	
}
