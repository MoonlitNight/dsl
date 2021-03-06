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

	public VariableExpression(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
