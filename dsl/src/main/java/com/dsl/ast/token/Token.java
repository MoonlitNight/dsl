package com.dsl.ast.token;

public enum Token {
	
	EOF, 
	ERROR,
	VARIANT,
    LITERAL_INT,
    LITERAL_CHARS,
	LITERAL_DOUBLE,
	LITERAL_BOOLEAN,
	IDENTIFIER,
	CALLER,
	
	LINE_COMMENT,
	MULTI_LINE_COMMENT,
	
	TRUE("true"),
	FALSE("false"),
	VAR("var"),
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
    GTEQ(">="), 
    LT("<"), 
    LTEQ("<="), 
    EQ("="),
    EQEQ("=="), 
    BANGEQ("!="), 
    BANG("!"),
    QUES("?"),
    COLON(":"), 
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
