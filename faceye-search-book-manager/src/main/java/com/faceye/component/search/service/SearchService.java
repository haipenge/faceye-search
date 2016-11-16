package com.faceye.component.search.service;

import org.springframework.data.domain.Page;

import com.faceye.component.index.service.impl.WordContainer;
import com.faceye.component.search.service.impl.SearchResult;

/**
 * 搜索服务类
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年7月14日
 */
public interface SearchService {

	/**
	 * 根据KEY进行查询 
	 * @todo
	 * @param key
	 * @param page
	 * @param size
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月20日
	 */
	public Page<SearchResult> search(String key,Integer page,Integer size);
	
	/**
	 * 对字符串进行分词
	 * @todo
	 * @param str
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月20日
	 */
	public String [] getAnalyzerResult(String str);
	
	/**
	 * 从给定的内容中提取关键字
	 * @todo
	 * @param content
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年8月10日
	 */
	public WordContainer getKeywords(String content);
	
}
