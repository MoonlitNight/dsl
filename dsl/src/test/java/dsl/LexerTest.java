package dsl;

import org.junit.Test;

import com.dsl.ast.lexer.Lexer;
import com.dsl.ast.token.Token;

public class LexerTest {
		
	@Test
	public void test(){
		String text = "//这是一个脚本文件 \n var x = 1;\n {call(x,y); x+1=2; /**测试多行注释\n 1 \n 2 */ var x= \"字符串哈哈哈\"; var xx=${猜猜这个引用是什么}}";
		Lexer lexer = new Lexer(text);
		while(true) {
			lexer.nextToken();
			Token token = lexer.token();
			 if (token == Token.LITERAL_CHARS) {
	                System.out.println(token.name() + "\t\t" + lexer.stringVal());
	            } else if (token == Token.LITERAL_BOOLEAN || token == Token.LITERAL_INT) {
	                System.out.println(token.name() + "\t\t" + lexer.numberString());
	            }else if (token == Token.REFERENCE) {
	                System.out.println(token.name() + "\t\t" + lexer.stringVal());
	            }else if (token == Token.IDENTIFIER) {
	                System.out.println(token.name() + "\t\t" + lexer.stringVal());
	            }else if (token == Token.VARIANT) {
	                System.out.println(token.name() + "\t\t" + lexer.stringVal());
	            }
	            else {
	                System.out.println(token.name() + "\t\t\t" + token.name);
	            }
			if(token==Token.EOF)
				break;
		}
	
	}
}
