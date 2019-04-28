package com.faceye.test.component.parse.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.faceye.component.parse.service.ParseService;
import com.faceye.component.spider.doc.CrawlResult;
import com.faceye.component.spider.service.CrawlResultService;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class SuperParseServiceTestCase extends BaseServiceTestCase {

	@Autowired
	private CrawlResultService crawlResultService=null;
	@Autowired
	@Qualifier("superParseServiceImpl")
	private ParseService parseService=null;
	
	@Test
	public void testParse() throws Exception{
		this.parseService.saveParseResult();
	}
	
	@Test
	public void testPageParse() throws Exception{
		CrawlResult crawlResult=this.crawlResultService.get(1327L);
		this.parseService.saveParseResult(crawlResult);
	}
}
