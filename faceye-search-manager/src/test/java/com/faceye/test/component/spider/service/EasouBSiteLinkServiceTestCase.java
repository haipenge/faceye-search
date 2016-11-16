package com.faceye.test.component.spider.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.faceye.component.spider.service.SiteLinkService;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class EasouBSiteLinkServiceTestCase extends BaseServiceTestCase {

	
	@Autowired
	@Qualifier("easouBLinkServiceImpl")
	private SiteLinkService easouBLinkService=null;
	
	@Test
	public void testSaveInit() throws Exception{
		this.easouBLinkService.saveInitLinks();
	}
}
