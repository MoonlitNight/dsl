package com.dsl.ast.expression;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author jin.wang
 *
 */
public class Prototype implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1159594650343482084L;
	
	/**
	 * function name
	 */
	private String name;
	
	/**
	 * function arguments name
	 */
	private List<String> args;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getArgs() {
		return args;
	}

	public void setArgs(List<String> args) {
		this.args = args;
	} 
	
}
