package com.dsl.ast.expression;

/**
 * 布尔类型
 * @author jin.wang
 *
 */
public class BooleanExpression implements Expression  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5733523758806729881L;
	
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
