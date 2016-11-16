package com.faceye.component.parse.service.factory;

import com.faceye.component.parse.service.document.Document;
import com.faceye.component.spider.doc.CrawlResult;
/**
 * 网页解析
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2015年3月7日
 */
public interface Parse {
	public Document parse(CrawlResult crawlResult,Class [] filters);
}
