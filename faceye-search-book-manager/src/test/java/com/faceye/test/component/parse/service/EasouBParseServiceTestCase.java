package com.faceye.test.component.parse.service;

import org.eclipse.core.runtime.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.faceye.component.parse.service.ParseService;
import com.faceye.component.spider.service.CrawlResultService;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class EasouBParseServiceTestCase extends BaseServiceTestCase {

	@Autowired
	@Qualifier("easouBParseServiceImpl")
	private ParseService parseService = null;
	@Autowired
	private CrawlResultService crawlResultService = null;

	@Test
	public void testParse() throws Exception {
		this.parseService.saveParseResult();
	}

	/**
	 * 测试按类型进行解析
	 * @todo
	 * @throws Exception
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年11月11日
	 */
	public void testSaveParse() throws Exception {
		Boolean isFinished = Boolean.FALSE;
		while (!isFinished) {
//			isFinished = this.parseService.saveParseResult(5);
		}
		Assert.assertTrue(isFinished);
	}

	/**
	 * 解析小说封面页
	 * @todo
	 * @throws Exception
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月25日
	 */
	// @Test
	// public void testParseCrawlResult() throws Exception{
	// CrawlResult crawlResult=this.crawlResultService.get(116352L);
	// this.parseService.saveAndParse(crawlResult, 5);
	// }
}
