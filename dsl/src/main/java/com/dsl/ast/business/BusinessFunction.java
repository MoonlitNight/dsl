package com.dsl.ast.business;

/**
 * 业务接口实现类
 * 入参和返回值仅支持boolean、String、Double
 * 默认调用根据名称匹配到的第一个方法
 * @author jin.wang
 *
 */
public interface BusinessFunction {
	
	/**
	 * 脚本中调用方法别名
	 * @return
	 */
	String getScriptMethodAlias();
	
	/**
	 * 子类业务实现方法名
	 * @return
	 */
	String getHandlerMethodName();
	
}
