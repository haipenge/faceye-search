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
import com.faceye.component.spider.service.SiteService;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class SegmentFaultLinkServiceTestCase extends BaseServiceTestCase {
	@Value("#{property['domain.segmentfault.com']}")
	private String domain="";
	
	@Autowired
	@Qualifier("segmentFaultLinkServiceImpl")
	private SiteLinkService segmentFaultLinkService=null;
	
	@Autowired
	private LinkService linkService=null;
	
	@Autowired
	private SiteService siteService=null;
	@Test
	public void testInitLinks() throws Exception{
		Site site=this.siteService.getSiteByName(domain);
		this.segmentFaultLinkService.saveInitLinks();
        Map searchParams=new HashMap();
        Assert.isTrue(null!=site);
        searchParams.put("EQ|site.id", site.getId());
        Page<Link> page=this.linkService.getPage(searchParams, 1, 100);
        Assert.isTrue(page!=null &&CollectionUtils.isNotEmpty(page.getContent())&&page.getContent().size()==100);
        
        
	}
}
