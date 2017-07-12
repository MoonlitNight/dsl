package com.dsl.ast.expression.node;

import java.io.Serializable;

import com.dsl.ast.token.Token;

public class Node implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6509030724251110127L;

	private Node leftChild;
	
	private Node rightChild;
	
	private Token label;
	
	private Object data;

	public Node() {
		super();
	}

	public Node(Node leftChild, Node rightChild, Token label) {
		super();
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.label = label;
	}

	public Node getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(Node leftChild) {
		this.leftChild = leftChild;
	}

	public Node getRightChild() {
		return rightChild;
	}

	public void setRightChild(Node rightChild) {
		this.rightChild = rightChild;
	}

	public Token getLabel() {
		return label;
	}

	public void setLabel(Token label) {
		this.label = label;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
}
