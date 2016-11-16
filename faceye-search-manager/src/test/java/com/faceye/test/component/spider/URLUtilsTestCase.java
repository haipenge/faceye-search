package com.faceye.test.component.spider;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.runtime.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.service.SiteService;
import com.faceye.component.spider.util.URLUtils;
import com.faceye.test.feature.service.BaseServiceTestCase;


public class URLUtilsTestCase extends BaseServiceTestCase{

	@Autowired
	private SiteService siteService=null;
	@Test
	public void testGetDomain() throws Exception{
		String domain="";
		String res="segmentfault.com";
		String url="http://segmentfault.com/blog/izhuhaodev/1190000000358664";
		domain=URLUtils.getDomain(url);
		Assert.isTrue(StringUtils.equals(domain, res));
		url="http://www.segmentfault.com/blog/izhuhaodev/1190000000358664";
		domain=URLUtils.getDomain(url);
		Assert.isTrue(StringUtils.equals(domain, res));
		url="http://www.segmentfault.com/";
		domain=URLUtils.getDomain(url);
		Assert.isTrue(StringUtils.equals(domain, res));
		url="http://www.segmentfault.com";
		domain=URLUtils.getDomain(url);
		Assert.isTrue(StringUtils.equals(domain, res));
		url="http://segmentfault.com/";
		domain=URLUtils.getDomain(url);
		Assert.isTrue(StringUtils.equals(domain, res));
		url="http://segmentfault.com";
		domain=URLUtils.getDomain(url);
		Assert.isTrue(StringUtils.equals(domain, res));
	}
	@Test
	public void testGetRootDomain() throws Exception{
		List<Site> sites=this.siteService.getAll();
		if(CollectionUtils.isNotEmpty(sites)){
			for(Site site:sites){
				String rootDomain=URLUtils.getRootDomain(site.getUrl());
				logger.debug(">>FaceYe Root Domain is :"+rootDomain+",source url is :"+site.getName());
				Assert.isTrue(StringUtils.isNotEmpty(rootDomain)&&rootDomain.indexOf("\\.")==-1);
			}
		}
	}
}
