package com.faceye.test.component.push.service;

import org.eclipse.core.runtime.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.faceye.component.push.service.PushService;
import com.faceye.component.push.service.impl.PushServiceFactory;
import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.service.SiteService;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class PushServiceFactoryTestCase extends BaseServiceTestCase {
	@Autowired
	private PushServiceFactory pushServiceFactory = null;
	@Autowired
	private SiteService siteService=null;
	
	@Test
	public void getPushService(){
		Site site=this.siteService.get(1L);
		PushService pushService=this.pushServiceFactory.getPushService(site);
		Assert.assertTrue(null!=pushService);
	}
}
