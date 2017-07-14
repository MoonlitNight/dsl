package com.dsl.ast.expression;

import java.util.List;

public class CallExpression implements Expression {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2739273477452135581L;

	private String callee;
	
	private List<Expression> args;

	public CallExpression(String callee, List<Expression> args) {
		super();
		this.callee = callee;
		this.args = args;
	}

	public String getCallee() {
		return callee;
	}

	public void setCallee(String callee) {
		this.callee = callee;
	}

	public List<Expression> getArgs() {
		return args;
	}

	public void setArgs(List<Expression> args) {
		this.args = args;
	}
	
	
}
