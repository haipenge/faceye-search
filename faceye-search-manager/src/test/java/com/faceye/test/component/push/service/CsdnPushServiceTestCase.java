package com.faceye.test.component.push.service;

import org.eclipse.core.runtime.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.faceye.component.push.service.PushService;
import com.faceye.component.push.service.model.PushObject;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class CsdnPushServiceTestCase extends BaseServiceTestCase {
	@Autowired
	@Qualifier("csdnPushService")
	private PushService csdnPushService = null;

	@Test
	public void testDoLogin() throws Exception {
      boolean isLoginSuccess=this.csdnPushService.doLogin();
      Assert.assertTrue(isLoginSuccess);
	}
	@Test
	public void testPush() throws Exception{
		this.csdnPushService.doLogin();
		PushObject pushObject=new PushObject();
	    pushObject.setName("Docker 与 Mac");
	    pushObject.setContent("Docker支持Mac OS X 10.6 Snow Leopard 及其以上版本. Docker引擎使用了Linux内核特定的特性，所以要让它运行在OS X上我们需要用一个轻量型的虚拟机(vm)。用OS X的Docker客户...");
	    this.csdnPushService.push(pushObject);
	}
}
