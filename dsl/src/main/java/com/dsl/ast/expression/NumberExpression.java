package com.dsl.ast.expression;

/**
 * 数字
 * @author jin.wang
 *
 */
public class NumberExpression implements Expression {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3680568051798088708L;
	
	private Double doubleVal;

	public NumberExpression(Double doubleVal) {
		super();
		this.doubleVal = doubleVal;
	}

	public Double getDoubleVal() {
		return doubleVal;
	}

	public void setDoubleVal(Double doubleVal) {
		this.doubleVal = doubleVal;
	}
	
	
}
