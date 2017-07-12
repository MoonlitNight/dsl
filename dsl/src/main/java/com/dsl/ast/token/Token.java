package com.dsl.ast.token;

public enum Token {
	
	EOF, 
	ERROR,
	IDENTIFIER,
	VARIANT,
	LITERAL_DOUBLE,
	LITERAL_STRING,
	LITERAL_BOOLEAN,
	
	FUNCTION("function"),
	REFERENCE("$"),
	COMMENT("#"),
	END(";"),
	LPAREN("("), 
	RPAREN(")"), 
	
	PLUS("+"), 
    SUB("-"),
    GT(">"), 
    LT("<"), 
    EQEQ("=="), 
    LTEQ("<="), 
    GTEQ(">="), 
    BANGEQ("!="), 
    AMPAMP("&&"), 
    BARBAR("||"),
    STAR("*"), 
    SLASH("/");
	
	public final String name;

    Token(){
        this(null);
    }

    Token(String name){
        this.name = name;
    }
}
