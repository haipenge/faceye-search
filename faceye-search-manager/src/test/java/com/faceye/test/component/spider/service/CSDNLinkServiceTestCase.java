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

public class CSDNLinkServiceTestCase extends BaseServiceTestCase {

	@Autowired
	@Qualifier("CSDNLinkServiceImpl")
	private SiteLinkService csdnLinkService = null;

	@Autowired
	private LinkService linkService = null;

	/**
	 * 测试初始化链接
	 * @todo
	 * @throws Exception
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年9月14日
	 */
	@Test
	public void testSaveInitLinks() throws Exception {
		this.csdnLinkService.saveInitLinks();
		Site site = this.csdnLinkService.getSite();
//		Assert.assertTrue(null != site);
		Map searchParams = new HashMap();
		searchParams.put("EQ|site.id", site.getId());
		searchParams.put("EQ|type", new Integer(1));
		Page<Link> links = this.linkService.getPage(searchParams, 1, 100);
		Assert.isTrue(null != links && CollectionUtils.isNotEmpty(links.getContent()));

	}
}
