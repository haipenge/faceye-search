package com.faceye.test.component.spider.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.eclipse.core.runtime.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;

import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.service.LinkService;
import com.faceye.component.spider.service.SiteLinkService;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class ITEyeLinkServiceTestCase extends BaseServiceTestCase {

	@Autowired
	@Qualifier("ITEyeLinkServiceImpl")
	private SiteLinkService iteyeLinkService=null;
	@Autowired
	private LinkService linkService=null;
	@Test
	public void testInitLinks() throws Exception{
		this.iteyeLinkService.saveInitLinks();
		Site site =this.iteyeLinkService.getSite();
		Map searchParams = new HashMap();
		searchParams.put("EQ|site.id", site.getId());
		searchParams.put("EQ|type", new Integer(1));
		Page<Link> links = this.linkService.getPage(searchParams, 1, 100);
		Assert.isTrue(null != links && CollectionUtils.isNotEmpty(links.getContent()));
	}
}
