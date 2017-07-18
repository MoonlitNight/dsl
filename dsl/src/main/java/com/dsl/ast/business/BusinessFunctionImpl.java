package com.dsl.ast.business;

public class BusinessFunctionImpl implements BusinessFunction {

	@Override
	public String getScriptMethodAlias() {
		return "显示";
	}

	@Override
	public String getHandlerMethodName() {
		return "show";
	}
	
	public int show(String xx,int yy) {
		System.out.println("实现类方法show-"+xx);
		System.out.println("实现类方法yy-"+yy);
		return 2;
	}

}
