package com.faceye.test.component.parse.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.Assert;

import com.faceye.component.parse.util.HtmlUtil;
import com.faceye.component.parse.util.RegexpConstants;
import com.faceye.component.parse.util.RegexpUtil;
import com.faceye.component.spider.util.FileUtil;
import com.faceye.component.spider.util.Http;
import com.faceye.test.feature.service.BaseServiceTestCase;

public class RegexpUtilTestCase extends BaseServiceTestCase {

	@Test
	public void testMatch() throws Exception {
		String regexp = "<a\\sclass=\"titlelnk\"\\shref=\"(.+?)\"\\starget=\"_blank\">.+?</a>";
		String path = "/work/Work/spider/crawl/20140708-21/com/cnblogs/20140708-213535-39.html";
		List<Map<String, String>> res = RegexpUtil.match(FileUtil.getInstance().read(path), regexp);
		Assert.assertTrue(CollectionUtils.isNotEmpty(res));
	}

	@Test
	public void testDistillLinks() throws Exception {
		String path = "/work/Work/spider/crawl/20140708-21/com/cnblogs/20140708-213232-3.html";
		List<Map<String, String>> res = RegexpUtil.match(FileUtil.getInstance().read(path), RegexpConstants.DISTIL_A_HREF);
		Assert.assertTrue(CollectionUtils.isNotEmpty(res));
	}

	@Test
	public void testDistillTitle() throws Exception {
		String path = "/work/Work/spider/crawl/20140708-21/com/cnblogs/20140708-213232-3.html";
		List<Map<String, String>> res = RegexpUtil.match(FileUtil.getInstance().read(path), RegexpConstants.DISTIAL_HTML_TITILE);
		Assert.assertTrue(CollectionUtils.isNotEmpty(res));
	}

	@Test
	public void testDistillBody() throws Exception {
		String path = "/work/Work/spider/crawl/20140709-23/com/cnblogs/20140709-230801-14.html";
		List<Map<String, String>> res = RegexpUtil.match(FileUtil.getInstance().read(path), RegexpConstants.DISTIL_CNBLOGS_BODY);
		Assert.assertTrue(CollectionUtils.isNotEmpty(res));
	}

	/**
	 * 提取博客园博客http://home.cnblogs.com/blog/page/2/ 链接列表
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月11日
	 */
	@Test
	public void testDistillCnblogsBestLinks() throws Exception {
		String path = "/work/Work/spider/crawl/20140711-13/com/cnblogs/20140711-134828-12.html";
		List<Map<String, String>> res = RegexpUtil.match(FileUtil.getInstance().read(path), RegexpConstants.DISTIL_CNBLOGS_LIST_LINKS);
		RegexpUtil.print(res);
		Assert.assertTrue(CollectionUtils.isNotEmpty(res));
	}

	/**
	 * 提取easou小说章节列表页章节明细
	 * @todo
	 * @throws Exception
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月23日
	 */
	@Test
	public void testDistillEasouBookSectionList() throws Exception {
		String regex = "<a[\\s]href=\"(\\/c\\/show.+?)\"\\s>.+?</a>";
		String path = "/20140723-00/com/easou/20140723-003623-1.html";
		List<Map<String, String>> res = RegexpUtil.match(FileUtil.getInstance().read(path), regex);
		RegexpUtil.print(res);
		Assert.assertTrue(CollectionUtils.isNotEmpty(res));
	}

	/**
	 * 提取章节明细
	 * @todo
	 * @throws Exception
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月23日
	 */
	@Test
	public void testDistillSectionDetail() throws Exception {
		String regex = "<p\\s\\sid=\"block1141\"\\sclass=\"easou_tit2\">(.+?)</p><div\\sid=\"block1142\"\\sclass=\"easou_con\">(.+?)</div>";
		String path = "/20140723-13/com/easou/20140723-135633-52.html";
		List<Map<String, String>> res = RegexpUtil.match(FileUtil.getInstance().read(path), regex);
		RegexpUtil.print(res);
		Assert.assertTrue(CollectionUtils.isNotEmpty(res));
	}

	@Test
	public void testDistillBookTitle() throws Exception {
		String regexp = "[\u4e00-\u9fa5]+(\\d+)[\u4e00-\u9fa5]+[\\s*]([\u4e00-\u9fa5]+?)";
		regexp="[\u4e00-\u9fa5].+";
		String title = "第51章一个篮球引发的血案（下）";
		List<Map<String, String>> res = RegexpUtil.match(title, regexp);
		RegexpUtil.print(res);
		Assert.assertTrue(CollectionUtils.isNotEmpty(res));
		title = "第51章   一个篮球引发  的血案（下）";
		res = RegexpUtil.match(title, regexp);
		RegexpUtil.print(res);
		Assert.assertTrue(CollectionUtils.isNotEmpty(res));

		
	}
	
	/**
	 * 提取小说分类列表页中的翻页URL及数字
	 * @todo
	 * @throws Exception
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月23日
	 */
	
	@Test
	public void testDistillPageLink() throws Exception{
		String content="<a href=\"/c/s.m?q=zhentan&amp;f=3&amp;sty=0&amp;esid=fcsZm6XD48XbX5-XD2&amp;s=0&amp;attb=0&amp;tpg=500&amp;p=2&amp;fr=3.4.4.0\">下页</a>";
		String regexp="<a[\\s]href=\"(\\/c\\/s.m\\?q=.+?)\">.+?</a>";
		String path = "/20140723-23/com/easou/20140723-232551-112.html";
		List<Map<String, String>> res = RegexpUtil.match(FileUtil.getInstance().read(path), regexp);
		RegexpUtil.print(res);
		String url=res.get(0).values().iterator().next();
		regexp=".+?tpg=(\\d+).+?";
		 res = RegexpUtil.match(url, regexp);
		RegexpUtil.print(res);
		url=url.replaceAll("&amp;p=\\d", "");
		logger.debug(url);
		Assert.assertTrue(CollectionUtils.isNotEmpty(res));
	}
	
	/**
	 * 提取小说列表页中中每部小说首页的链接
	 * @todo
	 * 如：http://b.easou.com/c/s.m?f=3&q=yanqing&esid=frHdkCXD4jXbX5-XD2&fr=3.0.3.3
	 * @throws Exception
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月24日p
	 */
	@Test
	public void testDistillBookList() throws Exception{
		String regexp = "<a[\\s]href=\"(\\/c\\/novel.+?)\">.+?</a>";
		String path = "/20140724-02/com/easou/20140724-020017-4634.html";
		List<Map<String, String>> res = RegexpUtil.match(FileUtil.getInstance().read(path), regexp);
		RegexpUtil.print(res);
		Assert.assertTrue(CollectionUtils.isNotEmpty(res));
	}
	
	@Test
	public void testDistillYiibaiMainContent() throws Exception{
		String regexp="<div\\sclass=\"content-body\">([\\W\\w]*?)<div\\sclass=\"footer\">";
		String path="/20150107-01/com/yiibai/20150107-015929-3751.html";
		List<Map<String, String>> res = RegexpUtil.match(FileUtil.getInstance().read(path), regexp);
//		RegexpUtil.print(res);
		logger.debug("-----------------------------------------------------------------");
		String html=HtmlUtil.getInstance().replaceHtml(res.get(0).values().iterator().next().toString());
		logger.debug(html);
		Assert.assertTrue(CollectionUtils.isNotEmpty(res));
	}
	
	/**
	 * 匹配segmentfault 翻页的前10页
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月17日
	 */
	@Test
	public void testMatchSegmentFaultPageUrl(){
		String regexp="^[\\w].+page=[\\d]{1}$";
		String url="http://segmentfault.com/blogs/newest?page=580";
		boolean isMatch=RegexpUtil.isMatch(url, regexp);
		Assert.assertTrue(!isMatch);
		 url="http://segmentfault.com/blogs/newest?page=5";
		isMatch=RegexpUtil.isMatch(url, regexp);
		Assert.assertTrue(isMatch);
	}
	
	@Test
	public void testPatternUrl(){
		String content=Http.getInstance().get("http://www.faceye.net/search/142520.html", "UTF-8");
		logger.debug(">>FaceYe -->网页内容是:\n"+content);
		int length=content.length();
		logger.debug(">>FaceYe -->原始长度是:"+length);
		boolean isMatch=RegexpUtil.isMatch(content, RegexpConstants.PATTERN_URL);
		content=HtmlUtil.getInstance().replace(content, RegexpConstants.PATTERN_URL);
		length=content.length();
		logger.debug(">>FaceYe -->替换后的长度是:"+length);
		logger.debug(">>FaceYe -->替换后的网页内容是:\n"+content);
		logger.debug(">>FaceYe -->URL pattern is:"+RegexpConstants.PATTERN_URL);
		Assert.assertTrue(isMatch);
	}
}
