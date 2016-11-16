package com.faceye.component.parse.service.factory;

import com.faceye.component.parse.service.document.Document;
import com.faceye.component.spider.doc.CrawlResult;

public interface ParseFilter {
	public void filter(Document document, CrawlResult crawlResult,String content);
}
