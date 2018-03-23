package com.faceye.test.component.spider.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.eclipse.core.runtime.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.faceye.component.parse.service.factory.model.csj.Result;
import com.faceye.component.spider.util.Http;
import com.faceye.feature.util.FileUtil;
import com.faceye.feature.util.Json;
import com.faceye.feature.util.regexp.RegexpUtil;


@RunWith(JUnit4.class)
public class CsjParseTestCase {
   private Logger logger=LoggerFactory.getLogger(getClass());
	@Test
	public void testParse() throws Exception{
		String content=Http.getInstance().get("http://wx.dm15.com/toggleQuestion.php?t=1440&type=2", "UTF-8");
		logger.debug(">>Content is:"+content);
		Result result=Json.toObject(content, Result.class);
		Assert.isNotNull(result);
		logger.debug(result.getMessage()+":"+result.getStatus()+":"+result.getQuestion().size()+":"+result.getQuestion().get(0).getDatalist().get(0).getTitle());
		Assert.assertTrue(result.getQuestion().size()>0);
	}
	
	@Test
	public void testRegexp() throws Exception{
		String content=FileUtil.readTextFile("/work/tmp/csj-detail.html");
		String paramsRegexp = "t: \"([\\d]+)\", type:\"([\\d]+)\"";
		String t = "";
		String type = "";
		List<Map<String, String>> paramsMatchers =null;
		try {
			paramsMatchers = RegexpUtil.match(content, paramsRegexp);
			if (CollectionUtils.isNotEmpty(paramsMatchers)) {
				t = paramsMatchers.get(0).get("1");
				type = paramsMatchers.get(0).get("2");
			}
		} catch (Exception e) {
			logger.error(">>FaceYe Throws Exception:", e);
		}
		Assert.isNotNull(paramsMatchers);
	}
}
