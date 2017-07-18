package com.dsl.ast.parser;

public class CharTypes {

    public static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private final static boolean[] whitespaceFlags = new boolean[256];
    static {
        for (int i = 0; i <= 32; ++i) {
            whitespaceFlags[i] = true;
        }
        
        whitespaceFlags[LayoutCharacters.EOI] = false;
        for (int i = 0x7F; i <= 0xA0; ++i) {
            whitespaceFlags[i] = true;
        }
   
        whitespaceFlags[160] = true; // 特别处理
    }

    public static boolean isWhitespace(char c) {
        return (c <= whitespaceFlags.length && whitespaceFlags[c]) //
               || c == '　'; // Chinese space
    }
    
    public static boolean isLetterOrChinese(char ch) {
	  String regex = "[a-zA-Z\u4e00-\u9fa5]";
	  String str = new StringBuffer().append(ch).toString();
	  return str.matches(regex);
	 }

}
