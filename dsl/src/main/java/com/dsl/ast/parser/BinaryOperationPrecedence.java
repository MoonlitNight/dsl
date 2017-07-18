package com.dsl.ast.parser;

import java.util.HashMap;
import java.util.Map;

import com.dsl.ast.token.Token;

public class BinaryOperationPrecedence {
	
	private static final Map<Token,Integer> binopPrecedence = new HashMap<>();
	
	static {
		binopPrecedence.put(Token.BARBAR,10);
		binopPrecedence.put(Token.AMPAMP,20);
		binopPrecedence.put(Token.BANGEQ,30);
		binopPrecedence.put(Token.EQEQ,30);
		binopPrecedence.put(Token.LTEQ,40);
		binopPrecedence.put(Token.LT,40);
		binopPrecedence.put(Token.GTEQ,40);
		binopPrecedence.put(Token.GT,40);
		binopPrecedence.put(Token.PLUS,50);
		binopPrecedence.put(Token.SUB,50);
		binopPrecedence.put(Token.STAR,60);
		binopPrecedence.put(Token.SLASH,60);
		binopPrecedence.put(Token.BANG,70);
	}
	
	public static int getPrecedence(Token token) {
		Integer tokPrec  = binopPrecedence.get(token);
		if(tokPrec==null)
			return -1;
		return tokPrec;
		
	}
	
}
