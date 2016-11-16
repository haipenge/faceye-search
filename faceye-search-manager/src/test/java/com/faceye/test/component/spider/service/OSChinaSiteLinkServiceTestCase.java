package com.faceye.test.component.spider.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.faceye.component.spider.service.SiteLinkService;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class OSChinaSiteLinkServiceTestCase extends BaseServiceTestCase {
    @Autowired
    @Qualifier("OSChinaLinkServiceImpl")
	private SiteLinkService oschinaSiteLinkService=null;
    @Test
    public void testIninLinks() throws Exception{
    	this.oschinaSiteLinkService.saveInitLinks();
    }
    @Test
    public void testReSetIinit() throws Exception{
    	this.oschinaSiteLinkService.reInitLinks();
    }
}
