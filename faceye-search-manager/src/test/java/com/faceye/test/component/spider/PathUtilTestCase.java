package com.faceye.test.component.spider;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.faceye.component.parse.util.RegexpUtil;
import com.faceye.component.spider.util.PathUtil;

@RunWith(JUnit4.class)
public class PathUtilTestCase {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void testGeneratePath() throws Exception {
		String url = "http://www.cnblogs.com/zhuocheng/archive/2011/12/12/2285290.html";
		url="http://segmentfault.com/blog/sequoiadb/1190000002492514";
		String res = PathUtil.generatePath(url);
		logger.debug(res);
		Assert.isTrue(StringUtils.isNotEmpty(res));
	}

	@Test
	public void testEndWith() throws Exception {
		String url = "http://home.cnblogs.com/blog/page/5/";
		boolean res = url.endsWith("/5/");
		Assert.isTrue(res);
	}

	@Test
	public void testEndWithRegexp() throws Exception {
		String url = "http://www.cnblogs.com/cate/java/";
		String regexp = "[a-z]*?";
		boolean res = url.endsWith(regexp);
		Assert.isTrue(res);
	}
	@Test
	public void testGetSubjectFromUrl() throws Exception{
		String sourceUrl="http://www.yiibai.com/tika/tika_extracting_html_document.html";
		sourceUrl=sourceUrl.replace("http://www.yiibai.com", "");
		String subejctName=sourceUrl.substring(1, sourceUrl.lastIndexOf("/"));
		Assert.isTrue(StringUtils.equals("tika", subejctName));
	}
	/**
	 * 测试翻页是否匹配
	 * @todo
	 * @throws Exception
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月19日
	 */
	@Test
	public void testSegmentUrlMatch() throws Exception{
		String regexp="^[\\w].+page=[\\d]{1}$";
		String url="http://segmentfault.com/blogs/recommend?page=5";
		boolean isMatch=RegexpUtil.isMatch(url, regexp);
		Assert.isTrue(isMatch);
		url="http://segmentfault.com/blogs/recommend?page=50";
		isMatch=RegexpUtil.isMatch(url, regexp);
		Assert.isTrue(!isMatch);
	}
	@Test
	public void testReplace() throws Exception{
		//String url="http:\\/\\/image.faceye.com\\.\\.\\/\\.\\.\\/\\.\\.\\/upload\\/image\\/20150203\\/1422929840556055005.png";
		String url="/../../../upload/image/20150203/1422929840556055005.png";
		//String replaceWith="\\.\\.\\/\\.\\.\\/\\.\\.";
		String replaceWith="../../..";
		String replacedUrl="/upload/image/20150203/1422929840556055005.png";
		//String testUrl=url.replaceAll(replaceWith, "");
		String testUrl=url.replace(replaceWith, "");
		Assert.isTrue(StringUtils.equals(replacedUrl, testUrl));
		
	}
	
	@Test
	public void testEquals(){
		String s=".";
		boolean res=StringUtils.equals(s, ".");
		Assert.isTrue(res);
	}
	@Test
	public void testStartWith(){
		String url = "http://www.cnblogs.com/zhuocheng/archive/2011/12/12/2285290.html";
		int start=StringUtils.lastIndexOf(url, "http");
		logger.debug("start:"+start);
		Assert.isTrue(start==0);
		
	}
}
