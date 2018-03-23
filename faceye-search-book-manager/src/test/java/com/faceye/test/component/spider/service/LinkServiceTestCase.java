package com.faceye.test.component.spider.service;

import java.util.Date;

import org.eclipse.core.runtime.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.service.LinkService;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class LinkServiceTestCase extends BaseServiceTestCase {

	@Autowired
	private LinkService linkService = null;

	@Test
	public void testIsLinkExist() throws Exception {
		Link link=new Link();
		
		String url = "http://home.cnblogs.com/blog/page/1/";
		link.setUrl(url);
		link.setCreateDate(new Date());
		link.setIsCrawled(false);
		link.setLastCrawlDate(null);
		link.setMimeType(1);
		link.setSiteId(1L);
		link.setType(1);
		this.linkService.save(link);
		boolean isExist = this.linkService.isLinkExist(url);
		Assert.assertTrue(isExist);
		url = "http://home.cnblogs.com/blog/page/1/ssf";
		isExist = this.linkService.isLinkExist(url);
		Assert.assertTrue(!isExist);
	}
}
