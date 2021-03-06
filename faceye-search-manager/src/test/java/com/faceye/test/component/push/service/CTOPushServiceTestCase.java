package com.faceye.test.component.push.service;

import org.eclipse.core.runtime.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.faceye.component.push.service.PushService;
import com.faceye.component.push.service.model.PushObject;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class CTOPushServiceTestCase extends BaseServiceTestCase {
	@Autowired
	@Qualifier("ctoPushService")
	private PushService ctoPushService = null;

	@Test
	public void testLogin() throws Exception {
		boolean res = this.ctoPushService.doLogin();
		Assert.isTrue(res);
	}

	@Test
	public void testPush() throws Exception {
		PushObject pushObject = new PushObject();
		pushObject.setName("Docker 与 Mac");
		pushObject.setContent("<a href=\"http://sohu.com\">a</a>Docker支持Mac OS X 10.6 Snow Leopard 及其以上版本. Docker引擎使用了Linux内核特定的特性，所以要让它运行在OS X上我们需要用一个轻量型的虚拟机(vm)。用OS X的Docker客户...");
		this.ctoPushService.push(pushObject);

	}
}
