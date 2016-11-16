package com.faceye.component.spider.service;

import com.faceye.component.spider.doc.Link;

/**
 * 爬取数据
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年7月6日
 */
public interface CrawlService {
	/**
	 * 爬取数据
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月6日
	 */
	public void crawl();
	
	/**
	 * 爬取一个页面
	 * @todo
	 * @param link
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月25日
	 */
	public void crawlLink(Link link);
}
