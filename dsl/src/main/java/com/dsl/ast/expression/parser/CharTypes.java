package com.dsl.ast.expression.parser;

public class CharTypes {

    private final static boolean[] hexFlags = new boolean[256];
    static {
        for (char c = 0; c < hexFlags.length; ++c) {
            if (c >= 'A' && c <= 'F') {
                hexFlags[c] = true;
            } else if (c >= 'a' && c <= 'f') {
                hexFlags[c] = true;
            } else if (c >= '0' && c <= '9') {
                hexFlags[c] = true;
            }
        }
    }

    public static boolean isHex(char c) {
        return c < 256 && hexFlags[c];
    }

    public static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }
    
    private final static boolean[] identifierFlags = new boolean[256];
    static {
        for (char c = 0; c < identifierFlags.length; ++c) {
            if (c >= 'A' && c <= 'Z') {
                identifierFlags[c] = true;
            } else if (c >= 'a' && c <= 'z') {
                identifierFlags[c] = true;
            } else if (c >= '0' && c <= '9') {
                identifierFlags[c] = true;
            }
        }
        // identifierFlags['`'] = true;
        identifierFlags['_'] = true;
        identifierFlags['$'] = true;
        identifierFlags['#'] = true;
    }

    public static boolean isIdentifierChar(char c) {
        if (c <= identifierFlags.length) {
            return identifierFlags[c];
        }
        return c != '　' && c != '，';
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

    /**
     * @return false if {@link LayoutCharacters#EOI}
     */
    public static boolean isWhitespace(char c) {
        return (c <= whitespaceFlags.length && whitespaceFlags[c]) //
               || c == '　'; // Chinese space
    }
    
    public static boolean isLetterDigitOrChinese(char ch) {
	  String regex = "[a-z0-9A-Z\u4e00-\u9fa5]";
	  String str = new StringBuffer().append(ch).toString();
	  return str.matches(regex);
	 }

}
