package com.dsl.ast.expression;

import java.io.Serializable;
import java.util.List;

public class Function implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6933745176085001709L;

	private Prototype prototype;
	
	private List<Expression> body;

	public Function(Prototype prototype, List<Expression> body) {
		super();
		this.prototype = prototype;
		this.body = body;
	}

	public Prototype getPrototype() {
		return prototype;
	}

	public void setPrototype(Prototype prototype) {
		this.prototype = prototype;
	}

	public List<Expression> getBody() {
		return body;
	}

	public void setBody(List<Expression> body) {
		this.body = body;
	}
	
}
