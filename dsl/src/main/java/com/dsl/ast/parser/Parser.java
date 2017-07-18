package com.dsl.ast.parser;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.dsl.ast.business.BusinessFunction;
import com.dsl.ast.exception.InvokeException;
import com.dsl.ast.exception.ParseException;
import com.dsl.ast.exception.SyntaxException;
import com.dsl.ast.expression.BangExpression;
import com.dsl.ast.expression.BinaryExpression;
import com.dsl.ast.expression.BooleanExpression;
import com.dsl.ast.expression.CallExpression;
import com.dsl.ast.expression.Expression;
import com.dsl.ast.expression.NumberExpression;
import com.dsl.ast.expression.ReferenceExpression;
import com.dsl.ast.expression.Script;
import com.dsl.ast.expression.StringExpression;
import com.dsl.ast.expression.TernaryExpression;
import com.dsl.ast.expression.VarExpression;
import com.dsl.ast.expression.VariableExpression;
import com.dsl.ast.lexer.Lexer;
import com.dsl.ast.token.Token;

/**
 * 解析类
 * @author jin.wang
 *
 */
public class Parser {
	
	private static final Map<String,BusinessFunction> REGISTERCENTER = new HashMap<>();
	
	private Map<String,Object> variableMap = new LinkedHashMap<>();
	
	private  Map<String,Object> referenceMap = new HashMap<>();
	
	private Lexer lexer = null;
	private Token token= null;
	
	
	/**
	 * 脚本执行入口 <br>
	 * 默认返回最后一个表达式的执行结果<br>
	 * 如需获取更多<br>
	 * 请定义为变量<br>
	 * 从变量列表中获取<br>
	 * @param input
	 * @return
	 */
	public Object execute(String input) {
		lexer = new Lexer(input);
		Script script = createScript();
		return executeScript(script);
	}
	
	private Object executeScript(Script script) {
		List<Expression> body = script.getBody();
		if(body==null||body.isEmpty())
			return null;
		List<Expression> trace = new ArrayList<>();
		for (Expression exp : body) {
			Expression result = execute(exp);
			trace.add(result);
		}
		if(trace.isEmpty())
			return null;
		return processResult(trace.get(trace.size()-1));
	}

	private Expression execute(Expression expression) {
		if(isBasicType(expression)){
			return expression;
		}
		if(expression instanceof ReferenceExpression)
			return handleReference(expression);
		if(expression instanceof VariableExpression)
			return handleVariable(expression);
		if(expression instanceof VarExpression){
			VarExpression exp = (VarExpression)expression;
			Expression result = execute(exp.getExpression());
			variableMap.put(exp.getName(), processResult(result));
			return result;
		}
		if(expression instanceof CallExpression)
			return invoke(expression);
		if(expression instanceof BinaryExpression)
			return handleBinaryOperation(expression);
		if(expression instanceof BangExpression)
			return handleBangOperation(expression);
		if(expression instanceof TernaryExpression)
			return handleTernaryOperation(expression);
		return null;
	}
	
	private Expression handleTernaryOperation(Expression expression) {
		TernaryExpression exp = (TernaryExpression)expression;
		Expression condition = exp.getCondition();
		Expression result = execute(condition);
		if(result instanceof BooleanExpression) {
			BooleanExpression temp = (BooleanExpression)result;
			return temp.getBooleanVal()?execute(exp.getLhs()):execute(exp.getRhs());
		} else {
			throw new ParseException("expression error");
		}
	}

	private Expression handleVariable(Expression expression) {
		VariableExpression exp = (VariableExpression)expression;
		return packageResult(variableMap.get(exp.getName()));
	}

	private Expression handleBangOperation(Expression expression) {
		BangExpression exp = (BangExpression)expression;
		Expression temp = execute(exp.getExpression());
		if(temp instanceof BooleanExpression) {
			BooleanExpression result = (BooleanExpression) temp;
			Boolean bol = !result.getBooleanVal().booleanValue();
			return packageResult(bol);
		} else {
			throw new ParseException("expression error");
		}
	}

	private Expression handleReference(Expression expression) {
		ReferenceExpression re = (ReferenceExpression)expression;
		Object reference = referenceMap.get(re.getName());
		return packageResult(reference);
	}

	private Expression handleBinaryOperation(Expression expression) {
		BinaryExpression exp = (BinaryExpression)expression;
		Expression lhs = execute(exp.getLhs());
		Expression rhs = execute(exp.getRhs());
		if(lhs==null||rhs==null)
			throw new ParseException("parse error!");
		String operation = exp.getOperation();
		Object result = null;
		switch (operation) {
		case "+":
			result = addOperation(lhs,rhs);
			break;
		case "-":
			result = subtractOperation(lhs,rhs);
			break;
		case "*":
			result = multiplyOperation(lhs,rhs);
			break;
		case "/":
			result = dividedOperation(lhs,rhs);
			break;
		case ">":
			result = gtOperation(lhs,rhs);
			break;
		case ">=":
			result = gtEQOperation(lhs,rhs);
			break;
		case "<":
			result = ltOperation(lhs,rhs);
			break;
		case "<=":
			result = ltEQOperation(lhs,rhs);
			break;
		case "==":
			result = eqEQOperation(lhs,rhs);
			break;
		case "!=":
			result = bangEQOperation(lhs,rhs);
			break;
		default:
			throw new ParseException("unsupported expression!");
		}
		return packageResult(result);
	}
	
	private Object bangEQOperation(Expression lhs, Expression rhs) {
		if(lhs instanceof NumberExpression&&rhs instanceof NumberExpression){
			NumberExpression left = (NumberExpression)lhs;
			NumberExpression right = (NumberExpression)rhs;
			return left.getDoubleVal()!=right.getDoubleVal();
		} else if(lhs instanceof StringExpression&&rhs instanceof StringExpression){
			StringExpression left = (StringExpression)lhs;
			StringExpression right = (StringExpression)rhs;
			return !left.getStringVal().equals(right.getStringVal());
		} else {
			throw new ParseException("expression error");
		}
	}

	private Object eqEQOperation(Expression lhs, Expression rhs) {
		if(lhs instanceof NumberExpression&&rhs instanceof NumberExpression){
			NumberExpression left = (NumberExpression)lhs;
			NumberExpression right = (NumberExpression)rhs;
			return left.getDoubleVal()==right.getDoubleVal();
		} else if(lhs instanceof StringExpression&&rhs instanceof StringExpression){
			StringExpression left = (StringExpression)lhs;
			StringExpression right = (StringExpression)rhs;
			return left.getStringVal().equals(right.getStringVal());
		} else {
			throw new ParseException("expression error");
		}
	}

	private Object ltEQOperation(Expression lhs, Expression rhs) {
		if(lhs instanceof NumberExpression&&rhs instanceof NumberExpression){
			NumberExpression left = (NumberExpression)lhs;
			NumberExpression right = (NumberExpression)rhs;
			return left.getDoubleVal()<=right.getDoubleVal();
		} else {
			throw new ParseException("expression error");
		}
	}

	private Object ltOperation(Expression lhs, Expression rhs) {
		if(lhs instanceof NumberExpression&&rhs instanceof NumberExpression){
			NumberExpression left = (NumberExpression)lhs;
			NumberExpression right = (NumberExpression)rhs;
			return left.getDoubleVal()<right.getDoubleVal();
		} else {
			throw new ParseException("expression error");
		}
	}

	private Object gtEQOperation(Expression lhs, Expression rhs) {
		if(lhs instanceof NumberExpression&&rhs instanceof NumberExpression){
			NumberExpression left = (NumberExpression)lhs;
			NumberExpression right = (NumberExpression)rhs;
			return left.getDoubleVal()>=right.getDoubleVal();
		} else {
			throw new ParseException("expression error");
		}
	}

	private Object gtOperation(Expression lhs, Expression rhs) {
		if(lhs instanceof NumberExpression&&rhs instanceof NumberExpression){
			NumberExpression left = (NumberExpression)lhs;
			NumberExpression right = (NumberExpression)rhs;
			return left.getDoubleVal()>right.getDoubleVal();
		} else {
			throw new ParseException("expression error");
		}
	}

	private Object dividedOperation(Expression lhs, Expression rhs) {
		if(lhs instanceof NumberExpression&&rhs instanceof NumberExpression){
			NumberExpression left = (NumberExpression)lhs;
			NumberExpression right = (NumberExpression)rhs;
			return left.getDoubleVal()/right.getDoubleVal();
		} else {
			throw new ParseException("expression error");
		}
	}

	private Object multiplyOperation(Expression lhs, Expression rhs) {
		if(lhs instanceof NumberExpression&&rhs instanceof NumberExpression){
			NumberExpression left = (NumberExpression)lhs;
			NumberExpression right = (NumberExpression)rhs;
			return left.getDoubleVal()*right.getDoubleVal();
		} else {
			throw new ParseException("expression error");
		}
	}

	private Object subtractOperation(Expression lhs, Expression rhs) {
		if(lhs instanceof NumberExpression&&rhs instanceof NumberExpression){
			NumberExpression left = (NumberExpression)lhs;
			NumberExpression right = (NumberExpression)rhs;
			return left.getDoubleVal()-right.getDoubleVal();
		} else {
			throw new ParseException("expression error");
		}
	}

	private Object addOperation(Expression lhs, Expression rhs) {
		if(lhs instanceof NumberExpression&&rhs instanceof NumberExpression){
			NumberExpression left = (NumberExpression)lhs;
			NumberExpression right = (NumberExpression)rhs;
			return left.getDoubleVal()+right.getDoubleVal();
		} else {
			throw new ParseException("expression error");
		}
	}

	private boolean isBasicType(Expression expression) {
		if(expression instanceof NumberExpression || expression instanceof StringExpression || expression instanceof BooleanExpression){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	private Object processResult(Expression expression) {
		if(expression instanceof NumberExpression) {
			NumberExpression result = (NumberExpression)expression;
			return result.getDoubleVal();
		}
		if(expression instanceof StringExpression) {
			StringExpression result = (StringExpression)expression;
			return result.getStringVal();
		}
		if(expression instanceof BooleanExpression) {
			BooleanExpression result = (BooleanExpression)expression;
			return result.getBooleanVal();
		}
		return null;	
	}
	
	private Expression packageResult(Object result) {
		if(result==null)
			return null;
		if(result instanceof Integer||result instanceof Long||result instanceof Double)
			return new NumberExpression(Double.valueOf(result.toString()));
		if(result instanceof String)
			return new StringExpression(result.toString());
		if(result instanceof Boolean)
			return new BooleanExpression(Boolean.valueOf(result.toString()));
		return null;
	}
	

	private Expression invoke(Expression expression) {
		CallExpression call = (CallExpression)expression;
		String callee = call.getCallee();
		BusinessFunction handler = REGISTERCENTER.get(callee);
		Method method = getHandleMethod(handler);
		Object[] parameters = fillArguments(method,call.getArgs());
		Object result = null;
		try {
			result = method.invoke(handler, parameters);
		} catch (Exception e) {
			e.printStackTrace();
			throw new InvokeException(String.format("invoke method error!method=%s", method.getName()));
		}
		return packageResult(result);
	}

	private Object[] fillArguments(Method method, List<Expression> args) {
		if(args==null||args.isEmpty())
			return null;
		List<Object> temp = new ArrayList<>();
		for (Expression exp : args) {
			Expression result = execute(exp);
			temp.add(processResult(result));
		}
		Class<?>[] parameterTypes = method.getParameterTypes();
		int length = parameterTypes.length;
		if(temp.size()!=length)
			throw new InvokeException("parameter mismatch");
		Object[] parameters = new Object[length];
		for (int i = 0; i < parameterTypes.length; i++) {
			Class<?> clazz = parameterTypes[i];
			String simpleName = clazz.getSimpleName();
			Object value = temp.get(i);
			if(value==null)
				continue;
			if(simpleName.equals(Type.STRING) && value instanceof String||(simpleName.equals(Type.BOOLEAN)||simpleName.equals(Type.BOOLEAN_B))&&value instanceof Boolean || (simpleName.equals(Type.DOUBLE)||simpleName.equals(Type.DOUBLE_D))&&value instanceof Double)
				parameters[i]=value;
			if((simpleName.equals(Type.INT)||simpleName.equals(Type.INTEGER))&&value instanceof Double)
				parameters[i]=Double.valueOf(value.toString()).intValue();
			if((simpleName.equals(Type.LONG)||simpleName.equals(Type.LONG_L))&&value instanceof Double)
				parameters[i]=Double.valueOf(value.toString()).longValue();
		}
		if(parameters.length!=length)
			throw new InvokeException("parameter error!");
		return parameters;
	}

	private Method getHandleMethod(BusinessFunction handler) {
		Method method= null;
		String methodName = handler.getHandlerMethodName();
		String alias = handler.getScriptMethodAlias();
		Method[] methods = handler.getClass().getMethods();
		for (Method m : methods) {
			if(m.getName().equals(methodName))
				method = m;
		}
		if(method==null)
			throw new InvokeException(String.format("method not found:name=%s,alias=%s.", methodName,alias));
		return method;
	}

	private Script createScript() {
		Script script = new Script();
		nextToken();
		while(true) {
			if(token==Token.EOF)
				break;
			if(token==Token.END||token==Token.SEMI||token==Token.LBRACE||token==Token.RBRACE) {
				nextToken();
				continue;
			}
			if(token==Token.VAR) {
				handleVar(script);
				continue;
			}
			Expression expression = parseExpression();
			if(expression==null)
				throw new ParseException("parse expression failure!");
			script.addExpression(expression);
		}
		return script;
	}
	
	private void handleVar(Script script){
		nextToken();
		checkToken(Token.IDENTIFIER);
		String varName = lexer.stringVal();
		nextToken();
		checkToken(Token.EQ);
		nextToken();
		Expression expression = parseExpression();
		VarExpression exp = new VarExpression(varName, expression);
		script.addExpression(exp);
	}

	private Expression parsePrimary() {
		if(token==Token.IDENTIFIER)
			return parseIdentifier();
		if(token==Token.LITERAL_CHARS)
			return parseString();
		if(token==Token.TRUE||token==Token.FALSE)
			return parseBoolean();
		if(token==Token.LITERAL_DOUBLE||token==Token.LITERAL_INT)
			return parseNember();
		if(token==Token.BANG)
			return parseBang();
		if(token==Token.REFERENCE)
			return parseReference();
		if(token==Token.LPAREN)
			return parseParen();
//		LOGGER.error("unknown token when expecting an expression");
		return null;
//		throw new SyntaxException("unknown token when expecting an expression");
	}
	
	private Expression parseBang() {
		String operation = token.name;
		nextToken();
		Expression expression = parseExpression();
		nextToken();
		return new BangExpression(operation, expression);
	}

	private Expression parseReference() {
		String name = lexer.stringVal();
//		if(StringUtils.isBlank(name))
		if(name==null||name.length()==0)
			new SyntaxException("reference name must not be null or empty !");
		Expression result = new ReferenceExpression(name);
		nextToken();
		return result;
	}

	private Expression parseParen(){
		nextToken();
		Expression expression = parseExpression();
		if(expression==null)
			return null;
		if(token!=Token.RPAREN) {
			throw new SyntaxException("Expected ')' ", lexer.getLine(), lexer.getPos());
		}
		nextToken();
		return expression;
	}
	
	private Expression parseIdentifier(){
		String idName = lexer.stringVal();
		nextToken();
		if(token!=Token.LPAREN)
			return new VariableExpression(idName);
		nextToken();
		List<Expression> args = new ArrayList<>();
		if(token!=Token.RPAREN) {
			while (true) {
				Expression arg = parseExpression();
				if(arg==null) 
					return null;
				args.add(arg);
				if(token==Token.RPAREN)
					break;
				if(token!=Token.COMMA) {
					throw new SyntaxException("Expected ')' or ',' in argument list", lexer.getLine(), lexer.getPos());
				}
				nextToken();
			}
		}
		nextToken();
		return new CallExpression(idName, args);
	}

	private Expression parseExpression() {
		Expression lhs = parsePrimary();
		if(lhs==null) 
			return null;
		Expression expression = parseBinOpRHS(0,lhs);
		if(token==Token.QUES) {
			expression = parseTernaryExpression(expression);
		}
		return expression;
	}
	
	private Expression parseTernaryExpression(Expression condition) {
		nextToken();
		Expression left = parseExpression();
		Expression right = null;
		if(token==Token.COLON) {
			nextToken();
			right = parseExpression();
			return new TernaryExpression(condition, left, right);
		}else{
			throw new SyntaxException("Expected ':' .", lexer.getLine(), lexer.getPos());
		}
	}
	
	private Expression parseBinOpRHS(int prec,Expression lhs){
		while(true) {
			int tokPrec = BinaryOperationPrecedence.getPrecedence(token);
			if (tokPrec < prec)
			      return lhs;
			String operation = token.name;
			nextToken();
			Expression rhs = parsePrimary();
			if(rhs==null)
				return null;
			int nextPrec = BinaryOperationPrecedence.getPrecedence(token);
			if(tokPrec<nextPrec) {
				rhs = parseBinOpRHS(tokPrec+1, rhs);
				if(rhs==null)
					return null;
			}
			lhs = new BinaryExpression(operation, lhs, rhs);
		}
	}
	
	private Expression parseNember(){
		Double num = Double.valueOf(lexer.numberString());
		Expression result = new NumberExpression(num);
		nextToken();
		return result;
	}

	private Expression parseString(){
		Expression result = new StringExpression(lexer.stringVal());
		nextToken();
		return result;
	}
	
	private Expression parseBoolean(){
		Expression result = new BooleanExpression(Boolean.valueOf(lexer.stringVal()));
		nextToken();
		return result;
	}


	private void checkToken(Token tok) {
		if(token==tok)
			return;
		syntaxError();
	}

	private void syntaxError(){
		throw new SyntaxException(lexer.getLine(), lexer.getPos());
	}
	
	private void nextToken(){
		lexer.nextToken();
		token = lexer.token();
	}

	public void addVariable(String name,Object value){
		variableMap.put(name, value);
	}

	public Object getVariable(String name){
		return variableMap.get(name);
	}
	
	/**
	 * 注册脚本中实现的方法
	 * @param function
	 */
	public void registerHandler(BusinessFunction function){
		REGISTERCENTER.put(function.getScriptMethodAlias(), function);
	}
	
	/**
	 * 注册脚本引用(支持integer、Long、Double、Boolena、String类型添加)
	 * @param name 引用名称
	 * @param reference 引用
	 */
	public void registerReference(String name,Object reference){
		referenceMap.put(name, reference);
	}
}
