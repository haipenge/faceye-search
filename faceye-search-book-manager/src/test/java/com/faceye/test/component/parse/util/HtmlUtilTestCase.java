package com.faceye.test.component.parse.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Assert;

import com.faceye.component.parse.util.HtmlUtil;
import com.faceye.component.parse.util.RegexpConstants;
import com.faceye.component.parse.util.RegexpUtil;
import com.faceye.component.spider.util.FileUtil;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class HtmlUtilTestCase extends BaseServiceTestCase {
  private Logger logger=LoggerFactory.getLogger(getClass());
  @Test
  public void testReplace() throws Exception{
	  String path="/work/Work/spider/crawl/20140709-23/com/cnblogs/20140709-230801-14.html";
	  String content=FileUtil.getInstance().read(path);
	  List<Map<String,String>> distill=RegexpUtil.match(content,RegexpConstants.DISTIL_CNBLOGS_BODY);
	  if(CollectionUtils.isNotEmpty(distill)){
		  content=distill.get(0).values().iterator().next();
	  }
	  logger.debug(">>Distill Body is:"+content);
      String res=HtmlUtil.getInstance().replaceHtml(content);
      logger.debug("Res is:"+res);
      Assert.assertTrue(StringUtils.isNotEmpty(res));
  }
  @Test
  public void testReplaceAllHtml() throws Exception{
	  String path="/work/Work/spider/crawl/20140726-20/com/cnblogs/20140709-230801-14.html";
	  String content=FileUtil.getInstance().read(path);
	  content=HtmlUtil.getInstance().replace(content, RegexpConstants.HTML_ALL);
	  logger.debug(">>After replace all content is:"+content);
	  Assert.assertTrue(StringUtils.indexOf(content, "<")==-1);
  }
  @Test
  public void testDistillMeta() throws Exception{
	  String path="/20140726-20/com/easou/20140726-202114-2610.html";
	  String content=FileUtil.getInstance().read(path);
	  List<Map<String,String>> matches=RegexpUtil.match(content, RegexpConstants.DISTILL_HTML_META);
	  RegexpUtil.print(matches);
	  Assert.assertTrue(CollectionUtils.isNotEmpty(matches));
  }
  
  
  
}
