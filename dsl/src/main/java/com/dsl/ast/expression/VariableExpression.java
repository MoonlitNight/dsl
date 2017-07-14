package com.dsl.ast.expression;

/**
 * 
 * @author jin.wang
 *
 */
public class VariableExpression implements Expression {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6279238338993454494L;

	private String name;
	
	private Expression expression;

	public VariableExpression(String name, Expression expression) {
		super();
		this.name = name;
		this.expression = expression;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}
	
}
