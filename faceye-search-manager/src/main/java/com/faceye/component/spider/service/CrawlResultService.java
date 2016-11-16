package com.faceye.component.spider.service;

import com.faceye.component.spider.doc.CrawlResult;
import com.faceye.component.spider.doc.Link;
import com.faceye.feature.service.BaseService;

public interface CrawlResultService extends BaseService<CrawlResult, Long> {

	/**
	 * 保存爬取结果
	 * @todo
	 * @param link
	 * @param crawlResult
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年3月27日
	 */
	public void saveCrawlResult(Link link, byte[] content,String charset);
	
	/**
	 * 保存爬取结果
	 * @todo
	 * @param link
	 * @param content
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年3月27日
	 */
	public void saveCrawlResult(Link link,String content);
}
/**@generate-service-source@**/
