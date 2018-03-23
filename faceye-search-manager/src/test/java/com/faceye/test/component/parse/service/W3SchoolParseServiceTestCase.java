package com.faceye.test.component.parse.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.eclipse.core.runtime.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;

import com.faceye.component.parse.service.ParseService;
import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.service.LinkService;
import com.faceye.component.spider.service.SiteService;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class W3SchoolParseServiceTestCase extends BaseServiceTestCase {
	@Value("#{property['domain.w3school']}")
	private String domain="";
	@Autowired
	@Qualifier("w3SchoolParseServiceImpl")
	private ParseService w3schoolParseServie = null;
	@Autowired
	private SiteService siteService=null;
	@Autowired
	private LinkService linkService = null;

	@Test
	public void testParse() throws Exception {
		this.w3schoolParseServie.saveParseResult();
		Site site =this.siteService.getSiteByName(domain);
		Map searchParams=new HashMap();
		searchParams.put("EQ|site.id", site.getId());
		searchParams.put("EQ|type", new Integer(2));
		Page links=this.linkService.getPage(searchParams, 1, 0);
		Assert.assertTrue(null!=links &&CollectionUtils.isNotEmpty(links.getContent())&&links.getContent().size()>0);
	}
}
