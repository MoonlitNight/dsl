package com.dsl.ast.expression;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 脚本
 * @author jin.wang
 *
 */
public class Script implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -801482344157383804L;

	private String type = "script";
	
	private List<Expression> body = new ArrayList<>();
	
	public Script() {
		super();
	}
	
	public Script(List<Expression> body) {
		super();
		this.body = body;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Expression> getBody() {
		return body;
	}

	public void setBody(List<Expression> body) {
		this.body = body;
	}
	
	public void addExpression(Expression expression){
		body.add(expression);
	}
	
}
