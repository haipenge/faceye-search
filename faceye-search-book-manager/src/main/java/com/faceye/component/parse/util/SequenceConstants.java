package com.faceye.component.parse.util;
/**
 * 序列常量类
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年8月16日
 */
public class SequenceConstants {

	/**
	 * 公用序列，所有内部业务，使用这个序列
	 */
	public final static String SEQ_PUBLIC="SEQ_PUBLIC";
	
	/**
	 * 对外访问文章的序列定义，最终会被爬虫发现，除Article Doc外，其它地方不可以使用本序列。
	 */
	public final static String SEQ_SEARCH_ARTICLE="SEQ_SEARCH_ARTICLE";
}
