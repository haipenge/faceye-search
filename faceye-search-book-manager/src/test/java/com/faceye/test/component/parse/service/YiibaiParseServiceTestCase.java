package com.faceye.test.component.parse.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.eclipse.core.runtime.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;

import com.faceye.component.parse.service.ParseService;
import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.service.LinkService;
import com.faceye.component.spider.service.SiteService;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class YiibaiParseServiceTestCase extends BaseServiceTestCase {
	@Value("#{property['domain.yiibai.com']}")
  private String domain="";
	@Autowired
	@Qualifier("yiibaiParseServiceImpl")
	private ParseService yiibaiParseService=null;
	@Autowired
	private SiteService siteService=null;
	@Autowired
	private LinkService linkService=null;
	@Test
	public void testParse() throws Exception{
		Map searchParams=new HashMap();
		Site site=this.siteService.getSiteByName(domain);
		searchParams.put("EQ|site.id", site.getId());
		searchParams.put("EQ|type", 2);
		this.yiibaiParseService.saveParseResult();
		Page<Link> page=this.linkService.getPage(searchParams, 1, 0);
		Assert.isTrue(null!=page && CollectionUtils.isNotEmpty(page.getContent()));
	}
}
