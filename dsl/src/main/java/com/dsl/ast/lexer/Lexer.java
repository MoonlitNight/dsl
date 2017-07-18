package com.dsl.ast.lexer;

import java.util.Arrays;

import com.dsl.ast.parser.CharTypes;
import com.dsl.ast.parser.Keywords;
import com.dsl.ast.parser.LayoutCharacters;
import com.dsl.ast.token.Token;

/**
 * 
 * 由阿里连接池durid提取修改
 *
 */
public class Lexer {
	
	protected String text = null;
	protected int          mark;
    protected int			pos;
    protected char        ch;
    
    protected Keywords     keywods      = Keywords.DEFAULT_KEYWORDS;

    protected char[]      buf;
    protected int          bufPos;

    protected Token     token;

    protected String      stringVal;

    protected int            line         = 0;

    protected int            lines        = 0;
    
    protected int          commentCount = 0;
    
    public Lexer(String input){
        this.text = input;
        this.pos = -1;
    }
    
    public final char charAt(int index) {
        if (index >= text.length()) {
            return LayoutCharacters.EOI;
        }
        return text.charAt(index);
    }
    
    protected final void scanChar() {
        ch = charAt(++pos);
    }
    
    public boolean isEOF() {
        return pos >= text.length();
    }
    
    public final void nextToken() {
        bufPos = 0;
        this.lines = 0;
        int startLine = line;

        while(true) {
            if (CharTypes.isWhitespace(ch)) {
                if (ch == '\n') {
                    line++;
                    lines = line - startLine;
                }
                scanChar();
                continue;
            }
            
            if (CharTypes.isLetterOrChinese(ch)) {
                scanIdentifier();
                return;
            }
            
            if (ch == '$' && charAt(pos + 1) == '{' ) {
                scanReference();
                return;
            }
            switch (ch) {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    scanNumber();
                    return;
                case ',':
                case '，':
                    scanChar();
                    token = Token.COMMA;
                    return;
                case '(':
                case '（':
                    scanChar();
                    token = Token.LPAREN;
                    return;
                case ')':
                case '）':
                    scanChar();
                    token = Token.RPAREN;
                    return;
                case '[':
                	scanLBracket();
                    return;
                case ']':
                    scanChar();
                    token = Token.RBRACKET;
                    return;
                case '{':
                    scanChar();
                    token = Token.LBRACE;
                    return;
                case '}':
                    scanChar();
                    token = Token.RBRACE;
                    return;
                case '*':
                    scanChar();
                    token = Token.STAR;
                    return;
                case '?':
                    scanChar();
                    token = Token.QUES;
                    return;
                case ';':
                    scanChar();
                    token = Token.SEMI;
                    return;
                case ':':
                    scanChar();
                    token = Token.COLON;
                    return;
                case '\"':
                    scanString();
                    return;
                case '/':
                    int nextChar = charAt(pos + 1);
                    if (nextChar == '/' || nextChar == '*') {
                        scanComment();
                        if ((token() == Token.LINE_COMMENT || token() == Token.MULTI_LINE_COMMENT)) {
                            bufPos = 0;
                            continue;
                        }
                    } else {
                        token = Token.SLASH;
                        scanChar();
                    }
                    return;
                default:
                    if (isOperator(ch)) {
                        scanOperator();
                        return;
                    }
                    if (isEOF()) { 
                        token = Token.EOF;
                    } else {
                        lexError("illegal.char", String.valueOf((int) ch));
                        scanChar();
                    }
                    return;
            }
        }

    }
    
    public void scanIdentifier() {
        final char first = ch;
        final boolean firstFlag = CharTypes.isLetterOrChinese(first);
        if (!firstFlag) {
            throw new RuntimeException("illegal identifier");
        }
        mark = pos;
        bufPos = 1;
        char ch;
        for (;;) {
            ch = charAt(++pos);
            if (!CharTypes.isLetterOrChinese(ch)) {
                break;
            }
            bufPos++;
            continue;
        }

        this.ch = charAt(pos);

        stringVal = addSymbol();
        Token tok = keywods.getKeyword(stringVal);
        if (tok != null) {
            token = tok;
        } else {
            token = Token.IDENTIFIER;
        }
    }
    
    public void scanComment() {
        if ((ch == '/' && charAt(pos + 1) == '/')) {
            scanSingleLineComment();
        } else if (ch == '/' && charAt(pos + 1) == '*') {
            scanMultiLineComment();
        } else {
            throw new IllegalStateException();
        }
    }
    
    private void scanSingleLineComment() {
        scanChar();
        scanChar();
        mark = pos;
        bufPos = 0;
        for (;;) {
            if (ch == '\r') {
                if (charAt(pos + 1) == '\n') {
                    line++;
                    scanChar();
                    break;
                }
                bufPos++;
                break;
            }
            if (ch == '\n') {
                line++;
                scanChar();
                break;
            }
			// single line comment结束符错误
			if (ch == LayoutCharacters.EOI) {
				throw new RuntimeException("syntax error at end of input.");
			}
            scanChar();
            bufPos++;
        }
        stringVal = subString(mark, bufPos);
        token = Token.LINE_COMMENT;
        commentCount++;
    }
    
    private void scanMultiLineComment() {
        scanChar();
        scanChar();
        mark = pos;
        bufPos = 0;
        for (;;) {
            if (ch == '*' && charAt(pos + 1) == '/') {
                scanChar();
                scanChar();
                break;
            }
			// multiline comment结束符错误
			if (ch == LayoutCharacters.EOI) {
				throw new RuntimeException("unterminated /* comment.");
			}
            scanChar();
            bufPos++;
        }
        stringVal = subString(mark, bufPos);
        token = Token.MULTI_LINE_COMMENT;
        commentCount++;
    }
    
    protected void lexError(String key, Object... args) {
        token = Token.ERROR;
    }
    
    public void scanReference() {
        if (ch != '$') {
            throw new RuntimeException("illegal variable");
        }
        ++pos;
        ++pos;
        mark = pos;
        bufPos = 1;
        
        pos++;
        bufPos++;
        for (;;) {
            ch = charAt(++pos);
            if (ch == '}') {
                break;
            }
            bufPos++;
            continue;
        }
        if (ch != '}') {
            throw new RuntimeException("syntax error");
        }
        ++pos;
        this.ch = charAt(pos);
        stringVal = addSymbol();
        token = Token.REFERENCE;
    }
    
    public final String addSymbol() {
        return subString(mark, bufPos);
    }

    public final String subString(int offset, int count) {
        return text.substring(offset, offset + count);
    }
    
    protected void scanString() {
        mark = pos;
        boolean hasSpecial = false;
        for (;;) {
            if (isEOF()) {
                lexError("unclosed.str.lit");
                return;
            }
            ch = charAt(++pos);
            if (ch == '\"') {
                scanChar();
                if (ch != '\"') {
                    token = Token.LITERAL_CHARS;
                    break;
                } else {
                    if (!hasSpecial) {
                        initBuff(bufPos);
                        arraycopy(mark + 1, buf, 0, bufPos);
                        hasSpecial = true;
                    }
                    putChar('\'');
                    continue;
                }
            }
            if (!hasSpecial) {
                bufPos++;
                continue;
            }
            if (bufPos == buf.length) {
                putChar(ch);
            } else {
                buf[bufPos++] = ch;
            }
        }

        if (!hasSpecial) {
            stringVal = subString(mark + 1, bufPos);
        } else {
            stringVal = new String(buf, 0, bufPos);
        }
    }
    
    public void arraycopy(int srcPos, char[] dest, int destPos, int length) {
        text.getChars(srcPos, srcPos + length, dest, destPos);
    }
    
    protected final void putChar(char ch) {
        if (bufPos == buf.length) {
            char[] newsbuf = new char[buf.length * 2];
            System.arraycopy(buf, 0, newsbuf, 0, buf.length);
            buf = newsbuf;
        }
        buf[bufPos++] = ch;
    }
    
    protected void initBuff(int size) {
        if (buf == null) {
            if (size < 32) {
                buf = new char[32];
            } else {
                buf = new char[size + 32];
            }
        } else if (buf.length < size) {
            buf = Arrays.copyOf(buf, size);
        }
    }
    
    private boolean isOperator(char ch) {
        switch (ch) {
            case '!':
            case '&':
            case '*':
            case '+':
            case '-':
            case '<':
            case '=':
            case '>':
            case '|':
            case ';':
                return true;
            default:
                return false;
        }
    }
    
    private final void scanOperator() {
        switch (ch) {
            case '+':
                scanChar();
                token = Token.PLUS;
                break;
            case '-':
                scanChar();
                token = Token.SUB;    
                break;
            case '*':
                scanChar();
                token = Token.STAR;
                break;
            case '/':
                scanChar();
                token = Token.SLASH;
                break;
            case '&':
                scanChar();
                if (ch == '&') {
                    scanChar();
                    token = Token.AMPAMP;
                } else {
                    token = Token.AMP;
                }
                break;
            case '|':
                scanChar();
                if (ch == '|') {
                    scanChar();
                    token = Token.BARBAR;
                }else {
                    token = Token.BAR;
                }
                break;
            case '=':
                scanChar();
                if (ch == '=') {
                    scanChar();
                    token = Token.EQEQ;
                } else {
                    token = Token.EQ;
                }
                break;
            case '>':
                scanChar();
                if (ch == '=') {
                    scanChar();
                    token = Token.GTEQ;
                } else {
                    token = Token.GT;
                }
                break;
            case '<':
                scanChar();
                if (ch == '=') {
                    scanChar();
                    token = Token.LTEQ;
                }else {
                    token = Token.LT;
                }
                break;
            case '!':
                scanChar();
                if (ch == '=') {
                    scanChar();
                    token = Token.BANGEQ;
                } else {
                    token = Token.BANG;
                }
                break;
            case '?':
                scanChar();
                token = Token.QUES;
                break;
            default:
                throw new RuntimeException("TODO");
        }
    }
    
    public final boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }
    
    public void scanNumber() {
        mark = pos;
        
        for (;;) {
            if (ch >= '0' && ch <= '9') {
                bufPos++;
            } else {
                break;
            }
            ch = charAt(++pos);
        }

        boolean isDouble = false;

        if (ch == '.') {
            bufPos++;
            ch = charAt(++pos);
            isDouble = true;

            for (;;) {
                if (ch >= '0' && ch <= '9') {
                    bufPos++;
                } else {
                    break;
                }
                ch = charAt(++pos);
            }
        }

        if (isDouble) {
            token = Token.LITERAL_DOUBLE;
        } else {
            token = Token.LITERAL_INT;
        }
    }
    
    protected void scanLBracket() {
        scanChar();
        token = Token.LBRACKET;
    }
    
    public final Token token() {
        return token;
    }
    
    public final String numberString() {
        return subString(mark, bufPos);
    }
    
    public final String stringVal() {
        return stringVal;
    }

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public int getPos() {
		return pos;
	}

	public int getLine() {
		return line;
	}

}
