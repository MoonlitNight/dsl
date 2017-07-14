package com.dsl.ast.expression;


public class BinaryExpression implements Expression {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2511151727405734501L;

	private String operation;
	
	private Expression lhs;
	
	private Expression rhs;

	public BinaryExpression(String operation, Expression lhs, Expression rhs) {
		super();
		this.operation = operation;
		this.lhs = lhs;
		this.rhs = rhs;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Expression getLhs() {
		return lhs;
	}

	public void setLhs(Expression lhs) {
		this.lhs = lhs;
	}

	public Expression getRhs() {
		return rhs;
	}

	public void setRhs(Expression rhs) {
		this.rhs = rhs;
	}

}
