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
import com.faceye.component.spider.service.LinkService;
import com.faceye.component.spider.service.SiteService;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class SegmentFaultParseServiceTestCase extends BaseServiceTestCase {
	@Value("#{property['domain.segmentfault.com']}")
	private String domain="";
	@Autowired
	@Qualifier("segmentFaultParseServiceImpl")
	private ParseService segmentFaultParseService=null;
	@Autowired
	private SiteService siteService=null;
	@Autowired
	private LinkService linkService=null;
    
	@Test
	public void testParse() throws Exception{
		Map searchParams=new HashMap();
		this.segmentFaultParseService.saveParseResult();
		searchParams.put("EQ|site.id", this.siteService.getSiteByName(domain).getId());
		searchParams.put("EQ|type", 2);
		Page<Link> page=this.linkService.getPage(searchParams, 1, 10);
		Assert.assertTrue(page!=null &&CollectionUtils.isNotEmpty(page.getContent())&&page.getContent().size()==10);
	}
}
