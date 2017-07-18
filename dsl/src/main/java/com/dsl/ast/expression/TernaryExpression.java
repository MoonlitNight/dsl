package com.dsl.ast.expression;

public class TernaryExpression implements Expression {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3796977698236747221L;

	
	private Expression condition;
	
	private Expression lhs;
	
	private Expression rhs;

	public TernaryExpression(Expression condition, Expression lhs, Expression rhs) {
		super();
		this.condition = condition;
		this.lhs = lhs;
		this.rhs = rhs;
	}

	public Expression getCondition() {
		return condition;
	}

	public void setCondition(Expression condition) {
		this.condition = condition;
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
