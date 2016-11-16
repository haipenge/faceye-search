package com.faceye.test.component.spider.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.eclipse.core.runtime.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;

import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.service.LinkService;
import com.faceye.component.spider.service.SiteLinkService;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class W3SchoolLinkServiceTestCase extends BaseServiceTestCase {
	
	@Autowired
	@Qualifier("w3SchoolLinkServiceImpl")
	private SiteLinkService w3SchoolLinkService=null;
	@Autowired
	private LinkService linkService=null;
	@Test
	public void testSaveInitLinks() throws Exception{
		Site site=this.w3SchoolLinkService.getSite();
		Map searchParams=new HashMap();
		searchParams.put("EQ|site.id", site.getId());
		searchParams.put("EQ|type", new Integer(1));
		Page links=this.linkService.getPage(searchParams, 1, 0);
		Assert.isTrue(CollectionUtils.isEmpty(links.getContent()));
		this.w3SchoolLinkService.saveInitLinks();
		links=this.linkService.getPage(searchParams, 1, 0);
		Assert.isTrue(CollectionUtils.isNotEmpty(links.getContent()));
	}
}
