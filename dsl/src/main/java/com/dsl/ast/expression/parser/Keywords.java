package com.dsl.ast.expression.parser;

import java.util.HashMap;
import java.util.Map;

import com.dsl.ast.token.Token;

public class Keywords {

    private final Map<String, Token> keywords;

    public final static Keywords     DEFAULT_KEYWORDS;

    static {
        Map<String, Token> map = new HashMap<String, Token>();
        map.put("var", Token.IDENTIFIER);
        map.put("function", Token.FUNCTION);
        DEFAULT_KEYWORDS = new Keywords(map);
    }

    public boolean containsValue(Token token) {
        return this.keywords.containsValue(token);
    }

    public Keywords(Map<String, Token> keywords){
        this.keywords = keywords;
    }

    public Token getKeyword(String key) {
        return keywords.get(key);
    }

    public Map<String, Token> getKeywords() {
        return keywords;
    }

}
