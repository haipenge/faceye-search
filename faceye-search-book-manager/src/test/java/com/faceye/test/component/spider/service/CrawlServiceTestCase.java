package com.faceye.test.component.spider.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.faceye.component.spider.entity.Link;
import com.faceye.component.spider.service.CrawlService;
import com.faceye.component.spider.service.LinkService;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class CrawlServiceTestCase extends BaseServiceTestCase {
	@Autowired
	private CrawlService crawlService=null;
	@Autowired
	private LinkService linkService=null;
	@Test
	public void testCrawl() throws Exception {
      this.crawlService.crawl();
      Thread.sleep(50000);
	}
	
//	@Test
//	public void testCrawlSingelPage() throws Exception{
//		Link link =linkService.get(499276L);
//	    this.crawlService.crawlLink(link);
//	    Thread.sleep(30000);
//	}
}
