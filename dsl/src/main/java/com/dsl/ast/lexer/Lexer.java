package com.dsl.ast.lexer;

import com.dsl.ast.token.Token;

public class Lexer {
	
	protected String text = null;
    protected int			pos;
    protected char        ch;

    protected char[]      buf;
    protected int          bufPos;

    protected Token     token;

    protected String      stringVal;
    
    protected Double    doubleVal;
    
    protected Boolean   booleanVal;

    
    public Lexer(String input){
        this.text = input;
    }
    
    
    


}
