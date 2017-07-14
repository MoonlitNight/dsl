package com.dsl.ast.token;

public enum Token {
	
	EOF, 
	ERROR,
	VARIANT,
    LITERAL_INT,
    LITERAL_CHARS,
	LITERAL_DOUBLE,
	LITERAL_BOOLEAN,
	CALLER,
	
	LINE_COMMENT,
	MULTI_LINE_COMMENT,
	
	IDENTIFIER("var"),
	FUNCTION("function"),
	REFERENCE("$"),
	COMMENT("#"),
	END(";"),
	LPAREN("("), 
	RPAREN(")"), 
	LBRACE("{"), 
	RBRACE("}"), 
	LBRACKET("["), 
	RBRACKET("]"), 
	COMMA(","), 
	
	PLUS("+"), 
    SUB("-"),
    GT(">"), 
    LT("<"), 
    EQ("="),
    EQEQ("=="), 
    LTEQ("<="), 
    GTEQ(">="), 
    BANGEQ("!="), 
    BANG("!"),
    QUES("?"),
    SEMI(";"), 
    AMPAMP("&&"), 
    BARBAR("||"),
    AMP("&"), 
    BAR("|"), 
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
