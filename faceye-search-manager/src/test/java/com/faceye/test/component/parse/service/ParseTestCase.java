package com.faceye.test.component.parse.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.faceye.component.parse.service.document.Document;
import com.faceye.component.parse.service.factory.Parse;
import com.faceye.component.parse.service.factory.filter.SuperBodyParseFilter;
import com.faceye.component.parse.service.factory.filter.SuperLinkParseFilter;
import com.faceye.component.spider.doc.CrawlResult;
import com.faceye.component.spider.service.CrawlResultService;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class ParseTestCase extends BaseServiceTestCase {

	@Autowired
	private Parse parse=null;
	@Autowired
	private CrawlResultService crawlResultService=null;
	@Test
	public void testSupperParse() throws Exception{
		Class [] clazzs=new Class[]{SuperLinkParseFilter.class,SuperBodyParseFilter.class};
		CrawlResult crawlResult=this.crawlResultService.get(211608L);
		Document document=this.parse.parse(crawlResult, clazzs);
//		Assert.isTrue(CollectionUtils.isNotEmpty(document.getLinks()));
//		crawlResult=this.crawlResultService.get(211608L);
//		document=this.parse.parse(crawlResult, clazzs);
		Assert.isTrue(StringUtils.isNotEmpty(document.getBody())&&StringUtils.isNotEmpty(document.getTitle()));
	}
}
