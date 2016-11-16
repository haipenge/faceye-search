package com.faceye.component.parse.service;

import java.util.List;

import com.faceye.component.parse.doc.FilterWord;
import com.faceye.feature.service.BaseService;

public interface FilterWordService extends BaseService<FilterWord,Long>{
   /**
    * 初始化禁词
    * @todo
    * @author:@haipenge
    * haipenge@gmail.com
    * 2014年8月16日
    */
	public void init();
	/**
	 * 是否包含禁词
	 * @todo
	 * @param content
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年8月16日
	 */
	public boolean isContainsFilterWrod(String content);
	
	/**
	 * 取得给定内容的禁词列表
	 * @todo
	 * @param content
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年9月25日
	 */
	public List<FilterWord> getFilterWords(String content);
	
	
	
	
}/**@generate-service-source@**/
