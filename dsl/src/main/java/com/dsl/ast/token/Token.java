package com.dsl.ast.token;

public enum Token {
	
	EOF, 
	ERROR,
	
	COMMENT("#"),
	END(";"),
	LPAREN("("), 
	RPAREN(")"), 
	
	PLUS("加"), 
    SUB("减"),
    GT("大于"), 
    LT("小于"), 
    EQEQ("等于"), 
    LTEQ("小于等于"), 
    GTEQ("大于等于"), 
    BANGEQ("不等于"), 
    AMPAMP("与"), 
    BARBAR("或"),
    STAR("乘"), 
    SLASH("除"), 
	;
	
	public final String name;

    Token(){
        this(null);
    }

    Token(String name){
        this.name = name;
    }
}
