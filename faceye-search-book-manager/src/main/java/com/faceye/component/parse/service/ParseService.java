package com.faceye.component.parse.service;

import com.faceye.component.spider.doc.CrawlResult;


public interface ParseService {
	
	
	
	/**
	 * 保存解析后的结果
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月9日
	 */
	public void saveParseResult();
	
	/**
	 * 保存单个解析结果
	 * @todo
	 * @param crawlResult
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年3月8日
	 */
	public void saveParseResult(CrawlResult crawlResult);
	
	
	
	
}
