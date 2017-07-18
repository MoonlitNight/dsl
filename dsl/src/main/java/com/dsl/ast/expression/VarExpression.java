package com.dsl.ast.expression;

/**
 * 变量
 * @author jin.wang
 *
 */
public class VarExpression implements Expression {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8747386496930976635L;

	private String name;
	
	private Expression expression;

	public VarExpression(String name) {
		super();
		this.name = name;
	}

	public VarExpression(String name, Expression expression) {
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
