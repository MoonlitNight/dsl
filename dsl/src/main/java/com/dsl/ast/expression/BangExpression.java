package com.dsl.ast.expression;

/**
 * 取反表达式
 * @author jin.wang
 *
 */
public class BangExpression implements Expression {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4079337410558156266L;

	private String operation;
	
	private Expression expression;
	
	public BangExpression(String operation, Expression expression) {
		super();
		this.operation = operation;
		this.expression = expression;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}
}
