package com.faceye.test.component.spider.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.eclipse.core.runtime.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;

import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.service.LinkService;
import com.faceye.component.spider.service.SiteLinkService;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class YiibaiLinkServiceTestCase extends BaseServiceTestCase {
	@Autowired
	@Qualifier("yiibaiLinkServiceImpl")
	private SiteLinkService yiibaiLinkService = null;

	@Value("#property{['domain.yiibai.com']}")
	private String domain = "";

	@Autowired
	private LinkService linkService = null;

	@Test
	public void testSaveInitLinks() throws Exception {
		Site site = this.yiibaiLinkService.getSite();
		Map searchParams = new HashMap();
		if (site != null) {
			searchParams.put("EQ|site.id", site.getId());
		}
		this.yiibaiLinkService.saveInitLinks();
		site = this.yiibaiLinkService.getSite();
		Page<Link> links = this.linkService.getPage(searchParams, 1, 0);
		Assert.isTrue(links != null && CollectionUtils.isNotEmpty(links.getContent()));
	}
}
