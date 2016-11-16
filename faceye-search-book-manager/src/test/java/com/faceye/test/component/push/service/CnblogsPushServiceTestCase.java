package com.faceye.test.component.push.service;

import org.eclipse.core.runtime.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.faceye.component.push.service.PushService;
import com.faceye.component.push.service.impl.CnblogsPushServiceImpl;
import com.faceye.component.push.service.model.PushObject;
import com.faceye.component.push.service.model.PushObjectBuilder;
import com.faceye.component.push.util.PushHttp;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class CnblogsPushServiceTestCase extends BaseServiceTestCase {

	@Autowired
	@Qualifier("cnblogsPushService")
	private PushService cnblogsPushService = null;

	@Test
	public void testDoLogin() throws Exception {
       boolean res=this.cnblogsPushService.doLogin();
//       String afterLoginPageTest=Http.getInstance().get("http://www.cnblogs.com/", "utf-8");
//       http://i.cnblogs.com/EditPosts.aspx?opt=1
       Thread.sleep(4000L);
       String afterLoginPageTest=PushHttp.getInstance(CnblogsPushServiceImpl.class.getName()).get("http://i.cnblogs.com/EditPosts.aspx?opt=1", "utf-8");
       logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
       logger.debug(afterLoginPageTest);
       res=afterLoginPageTest.indexOf("添加新随笔")!=-1;
       Assert.isTrue(res);
	}
	
	/**
	 * 测试推送文章
	 * @todo
	 * @throws Exception
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月22日
	 */
	@Test
	public void testPush() throws Exception{
      String name="Hello word!";
      String content="This is my First blog in cnblogs,hello!everyone!";
      PushObject pushObject =PushObjectBuilder.builder(name, content);
      this.cnblogsPushService.push(pushObject);
	}
}
