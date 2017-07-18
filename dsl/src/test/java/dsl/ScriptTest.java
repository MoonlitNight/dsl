package dsl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import com.dsl.ast.business.BusinessFunction;
import com.dsl.ast.business.BusinessFunctionImpl;
import com.dsl.ast.parser.Parser;

public class ScriptTest {
	
	@Test
	public void test01(){
		String text = "//这是一个脚本文件 \n var flag=xx!=yy; var x = 1;\n {call(x,y); x+1.0 ==2; var yy=true&&!cel(); var x=!true; var 测试=1*2+3/(5-1); /**测试多行注释\n 1 \n 2 */ var x= \"字符串哈哈哈\"; var xx=${猜猜这个引用是什么}}";
		Parser parser = new Parser();
		parser.execute(text);
	}
	
	
	@Test
	public void test02(){
		BusinessFunction fun= new BusinessFunctionImpl();
		Method method = null;
		Method[] methods = fun.getClass().getMethods();
		for (Method method2 : methods) {
			if(method2.getName().equals(fun.getHandlerMethodName()))
				method = method2;
		}
		Class<?>[] classes = method.getParameterTypes();
		for (Class<?> class1 : classes) {
			System.out.println(class1.getSimpleName());
		}
		Class<?> returnType = method.getReturnType();
		System.out.println(returnType.getSimpleName());
		Integer xx = 12;
		try {
			Object invoke = method.invoke(fun, "xxx",xx);
			System.out.println(invoke);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void test03(){
		String text = "var xx=${变量1}-${变量2}<8; 显示(\"哈哈123\",12);var yy=1+2>4; yy?显示(\"验证成功\",33):显示(\"验证失败\",44);";
		Parser parser = new Parser();
		parser.registerReference("变量1", 20);
		parser.registerReference("变量2", 12);
		parser.registerHandler(new BusinessFunctionImpl());
		Object result = parser.execute(text);
		System.out.println(result);
		System.out.println(parser.getVariable("xx"));
		System.out.println(parser.getVariable("yy"));
	}
	
//	@Test
//	public void test04(){
//		System.out.println(1+2>5?"xx":"yy");
//	}
	
	@Test
	public void test05(){
		String text = "var x=1+2*3>10?1:2;";
		Parser parser = new Parser();
		parser.execute(text);
		System.out.println(parser.getVariable("x"));
	}
	
}
