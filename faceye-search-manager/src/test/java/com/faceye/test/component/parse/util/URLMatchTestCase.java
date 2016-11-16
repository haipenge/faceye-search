package com.faceye.test.component.parse.util;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.util.Assert;

@RunWith(JUnit4.class)
public class URLMatchTestCase {
	@Test
	public void testUrlMatch() throws Exception {
		int res=0;
		String url="http:/image.faceye.net/kindle/img/2016/8/12/2016-08-12-00-06-14213.jpg";
		String s1="http:/image.faceye.net";
		res=StringUtils.indexOf(url,s1);
		Assert.isTrue(res!=-1);
	}
}
