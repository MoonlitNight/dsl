package com.dsl.ast.expression;

public class ReferenceExpression implements Expression {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2730903801707940618L;
	
	private String name;

	public ReferenceExpression(String name) {
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
