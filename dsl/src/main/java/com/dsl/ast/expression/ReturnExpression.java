package com.dsl.ast.expression;

public class ReturnExpression implements Expression {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8151792987807146033L;
	

	private Expression expression;

	public ReturnExpression(Expression expression) {
		super();
		this.expression = expression;
	}


	public Expression getExpression() {
		return expression;
	}


	public void setExpression(Expression expression) {
		this.expression = expression;
	}
	
	
}
