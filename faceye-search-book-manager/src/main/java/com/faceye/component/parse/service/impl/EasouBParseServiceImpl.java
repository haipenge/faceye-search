package com.faceye.component.parse.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.doc.ParseResult;
import com.faceye.component.parse.service.ParseService;
import com.faceye.component.parse.util.RegexpConstants;
import com.faceye.component.parse.util.RegexpUtil;
import com.faceye.component.search.entity.Book;
import com.faceye.component.search.entity.Category;
import com.faceye.component.search.entity.Section;
import com.faceye.component.search.service.BookService;
import com.faceye.component.search.service.CategoryService;
import com.faceye.component.search.service.SectionService;
import com.faceye.component.spider.doc.CrawlResult;
import com.faceye.component.spider.doc.Link;
import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.service.CrawlResultService;
import com.faceye.component.spider.service.LinkService;
import com.faceye.component.spider.service.SiteService;
import com.faceye.component.spider.util.FileUtil;

/**
 * Easou 小说解析:
 * --
 * 小说分类手页:type=0
 * 如：http://b.easou.com/c/cn.m?esid=fcsZm6XD48XbX5-XD2&fr=3.0.3.35
 * --
 * 小说分类列表页:type=3,小说名字列表
 * 如：http://b.easou.com/c/s.m?f=3&q=yanqing&esid=fcsZm6XD48XbX5-XD2
 * --
 * 章节列表页:type=1
 * --
 * 章节明细页:type=2
 * --
 * 小说按分类列表页:type=4,变换的为参数p 所有小说列表
 * 如:http://b.easou.com/c/s.m?q=yanqing&f=3&sty=0&esid=fcsZm6XD48XbX5-XD2&s=0&attb=0&tpg=500&p=2&fr=3.4.4.0
 * --
 * 每部小说封面页:type=5
 * 如:http://b.easou.com/c/novel.m?gid=3217447&nid=12529641&p=&count=6&esid=frHdkCXD4jXbX5-XD2&fr=3.4.2.0
 * --
 * 小说简介页：type=6
 * 如：http://b.easou.com/c/novel.m?esid=fcthfiXD4bXbX5-XD2&gid=3217447&type=1&fr=3.1.2.10
 * --
 * 小说章节列表页第一页：type=7
 * http://b.easou.com/c/clist.m?esid=fcthfiXD4bXbX5-XD2&gid=3217447&nid=1003217447&p=1&gst=1&fr=3.1.2.2
 * 用于取得章节总数，翻页页码，推测章节列表
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年7月23日
 */
@Service
public class EasouBParseServiceImpl extends BaseParseServiceImpl implements ParseService {
	@Autowired
	private CrawlResultService crawlResultService = null;
	@Autowired
	private SiteService siteService = null;
	@Autowired
	private LinkService linkService = null;
	@Autowired
	private BookService bookService = null;
	@Autowired
	private SectionService sectionService = null;

	@Autowired
	private CategoryService categoryService = null;

	public void saveAndParse(CrawlResult crawlResult, Integer type) {
		// 解析小说章节列表页URL
		if (type.intValue() == 1) {
			this.saveBookSectionListPageParseResult(crawlResult);
		}
		// 存储章节明细
		if (type.intValue() == 2) {
			this.saveParseSectionDetail(crawlResult);
		}
		// 解析分类瘦页,生成链接， 存储type=3
		if (type.intValue() == 0) {
			this.saveParseBookCategoryHome(crawlResult);
		}
		// 解析分类列表页首页，生成分类列表页翻页链接(type=4)
		if (type.intValue() == 3) {
			this.saveParseCategoryBookListPage(crawlResult);
		}
		// 解析小说列表页(type=4),取得小说封面页(type=5)
		if (type.intValue() == 4) {
			this.saveParseBookList(crawlResult);
		}
		// 解析小说封面
		if (type.intValue() == 5) {
			this.saveParseNovelHome(crawlResult);
		}
		// 解析小说章节首页
		if (type.intValue() == 7) {
			this.saveParseSectionHomePage(crawlResult);
		}
	}

	@Override
	public void saveParseResult() {
		Boolean isContinue = Boolean.TRUE;
		int loopCount = 0;
		while (isContinue) {
			int emptyCount = 0;
			loopCount++;
			logger.debug(">>FaceYe -------Parse Loop (" + loopCount + ") start");
			Map<String, Object> searchParams = new HashMap<String, Object>();
			for (int i = 1; i <= 7; i++) {
				Boolean isFinished = this.saveParseResult(i);
				if (isFinished) {
					emptyCount++;
				}
			}
//			// 1. 解析小说章节列表页
//			searchParams.put("ISFALSE|isParse", "0");
//			searchParams.put("EQ|link.type", new Integer(1));
//			searchParams.put("EQ|link.site.id", new Long(2));
//			searchParams.put("ISTRUE|isParseSuccess", "1");
//			Page<CrawlResult> page = this.crawlResultService.getPage(searchParams, 1, 1000);
//			if (page == null || CollectionUtils.isEmpty(page.getContent())) {
//				emptyCount++;
//			}
//			this.saveParseResult(page, 1);
//			// 2. 解析章节明细
//			searchParams.put("EQ|link.type", new Integer(2));
//			page = this.crawlResultService.getPage(searchParams, 1, 1000);
//			if (page == null || CollectionUtils.isEmpty(page.getContent())) {
//				emptyCount++;
//			}
//			this.saveParseResult(page, new Integer(2));
//			// 3.解析小说分类首页
//			searchParams.put("EQ|link.type", new Integer(0));
//			page = this.crawlResultService.getPage(searchParams, 1, 1000);
//
//			if (page == null || CollectionUtils.isEmpty(page.getContent())) {
//				emptyCount++;
//			}
//			this.saveParseResult(page, new Integer(0));
//			// 4.解析小说分类列表页，生成type=4,所有小说列表
//			searchParams.put("EQ|link.type", new Integer(3));
//			page = this.crawlResultService.getPage(searchParams, 1, 1000);
//			this.saveParseResult(page, new Integer(3));
//			if (page == null || CollectionUtils.isEmpty(page.getContent())) {
//				emptyCount++;
//			}
//			// 5.解析所有小说列表页(type=4)，取得小说封面页type=5
//			searchParams.put("EQ|link.type", new Integer(4));
//			page = this.crawlResultService.getPage(searchParams, 1, 1000);
//			this.saveParseResult(page, new Integer(4));
//			if (page == null || CollectionUtils.isEmpty(page.getContent())) {
//				emptyCount++;
//			}
//			// 6.解析小说封面页(type=5),取得小说名称，作者，章节列表第一页(7),小说简介页(6)
//			searchParams.put("EQ|link.type", new Integer(5));
//			page = this.crawlResultService.getPage(searchParams, 1, 1000);
//			this.saveParseResult(page, new Integer(5));
//			if (page == null || CollectionUtils.isEmpty(page.getContent())) {
//				emptyCount++;
//			}
//			// 7.解析小说章节首页，取得小说所有章节列表页URL
//			searchParams.put("EQ|link.type", new Integer(7));
//			page = this.crawlResultService.getPage(searchParams, 1, 1000);
//			this.saveParseResult(page, new Integer(7));
//			if (page == null || CollectionUtils.isEmpty(page.getContent())) {
//				emptyCount++;
//			}
			if (emptyCount == 7) {
				isContinue = Boolean.FALSE;
			}
		}
	}

	/**
	 * 保存解析结果
	 * @todo
	 * @param type
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年11月11日
	 */
	public boolean saveParseResult(Integer type) {
		Boolean isFinished = Boolean.FALSE;
		Map<String, Object> searchParams = new HashMap<String, Object>();
		// 1. 解析小说章节列表页
		searchParams.put("ISFALSE|isParse", "0");
		searchParams.put("EQ|link.type", type);
		searchParams.put("EQ|link.site.id", new Long(2));
		searchParams.put("ISFALSE|isParseSuccess", "0");
		Page<CrawlResult> page = this.crawlResultService.getPage(searchParams, 1, 1000);
		this.saveParseResult(page, type);
		if (null == page || CollectionUtils.isEmpty(page.getContent())) {
			isFinished = Boolean.TRUE;
		}
		return isFinished;
	}

	/**
	 * 批量保存解析结果
	 * @todo
	 * @param page
	 * @param type
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月23日
	 */
	private void saveParseResult(Page<CrawlResult> page, Integer type) {

		if (page != null && CollectionUtils.isNotEmpty(page.getContent())) {
			logger.debug(">>FacYe --> Parse Page Type is:" + page + ",page size is:" + page.getContent().size());
			for (CrawlResult crawlResult : page.getContent()) {
				this.saveAndParse(crawlResult, type);
			}
		}
	}

	/**
	 * 解析小说分类页首页
	 * 取得小说分类列表页（每条记录一部小说)，type=3
	 * @todo
	 * @param crawlResult
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月23日
	 */
	private void saveParseBookCategoryHome(CrawlResult crawlResult) {
		String path = crawlResult.getStorePath();
		String content = "";
		try {
			content = FileUtil.getInstance().read(path);
		} catch (IOException e1) {
			logger.error(">>--->" + e1.getMessage());
		}
		String regex = "<a[\\s]href=\"(\\/c\\/s.+?)\">.+?</a>";
		try {
			if (StringUtils.isNotEmpty(content)) {
				List<Map<String, String>> matchers = RegexpUtil.match(content, regex);
				if (CollectionUtils.isNotEmpty(matchers)) {
					Site site = siteService.get(2L);
					for (Map<String, String> match : matchers) {
						String url = match.values().iterator().next();
						// 存储分类列表页
						// 如
						this.saveLink(site, url, 3, crawlResult);
					}
				}
				crawlResult.setIsParse(Boolean.TRUE);
				// this.crawlResultService.save(crawlResult);
			} else {
				crawlResult.setIsParseSuccess(Boolean.FALSE);
				// this.crawlResultService.save(crawlResult);
				// this.crawlResultService.remove(crawlResult);
			}
		} catch (Exception e) {
			crawlResult.setIsParseSuccess(Boolean.FALSE);
			logger.error(">>--->" + e.getMessage());
		} finally {
			this.crawlResultService.save(crawlResult);
		}
	}

	/**
	 * 解析分类列表页，取得所有小说列表页
	 * 如:http://b.easou.com/c/s.m?q=%E7%88%B1%E6%83%85&f=8&sty=0&esid=fYaXeAXD4kXbX5-XD2&s=0&attb=0&tpg=500&p=2&fr=3.4.4.0
	 * 解析后:type=4
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月23日
	 */

	private void saveParseCategoryBookListPage(CrawlResult crawlResult) {
		String path = crawlResult.getStorePath();
		String content = "";
		try {
			content = FileUtil.getInstance().read(path);
		} catch (IOException e1) {
			logger.error(">>--->" + e1.getMessage());
		}
		// <a href="/c/s.m?q=yanqing&amp;f=3&amp;sty=0&amp;esid=fcsZm6XD48XbX5-XD2&amp;s=0&amp;attb=0&amp;tpg=500&amp;p=2&amp;fr=3.4.4.0">下页</a>
		// 提取页面中的下页，最后一页链接
		String regexp = "<a[\\s]href=\"(\\/c\\/s.m\\?q=.+?)\">.+?</a>";
		try {
			if (StringUtils.isNotEmpty(content)) {
				List<Map<String, String>> matchers = RegexpUtil.match(content, regexp);
				if (CollectionUtils.isNotEmpty(matchers)) {
					Site site = siteService.get(2L);
					String url = matchers.get(0).values().iterator().next();
					// 提取出URL中的分页总数:
					regexp = ".+?tpg=(\\d{1,9}).+?";
					List<Map<String, String>> totalPageMatcher = RegexpUtil.match(url, regexp);
					int totalPage = 0;
					if (CollectionUtils.isNotEmpty(totalPageMatcher)) {
						String pageNum = totalPageMatcher.get(0).values().iterator().next();
						if (StringUtils.isNotEmpty(pageNum)) {
							totalPage = Integer.parseInt(pageNum);
						}
					}
					// 去除URL中的分页参数
					url = url.replaceAll("&amp;p=\\d{1,9}", "");
					if (totalPage != 0) {
						for (int i = 1; i <= totalPage; i++) {
							String _url = url + "&amp;p=" + i;
							this.saveLink(site, _url, 4, crawlResult);
						}
					} else {
						logger.error(">>>>Total Page is zero,path is:" + path);
					}

				}
				crawlResult.setIsParse(Boolean.TRUE);
				// this.crawlResultService.save(crawlResult);
			} else {
				// this.crawlResultService.remove(crawlResult);
				crawlResult.setIsParseSuccess(Boolean.FALSE);
				// this.crawlResultService.save(crawlResult);
			}
		} catch (Exception e) {
			logger.error(">>--->" + e.getMessage());
			crawlResult.setIsParseSuccess(Boolean.FALSE);
		} finally {
			this.crawlResultService.save(crawlResult);
		}
	}

	/**
	 * 解析小说列表页（type=4),取得每部小说的首页链接（type=5)
	 * @todo
	 * @param crawlResult
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月24日
	 */
	private void saveParseBookList(CrawlResult crawlResult) {
		String path = crawlResult.getStorePath();
		String content = "";
		try {
			content = FileUtil.getInstance().read(path);
		} catch (IOException e1) {
			logger.error(">>--->" + e1.getMessage());
		}
		if (StringUtils.isEmpty(content)) {
			this.crawlResultService.remove(crawlResult);
			return;
		}
		String regex = "<a[\\s]href=\"(\\/c\\/novel.+?)\">.+?</a>";
		try {
			List<Map<String, String>> matchers = RegexpUtil.match(content, regex);
			if (CollectionUtils.isNotEmpty(matchers)) {
				Site site = this.siteService.get(2L);
				for (Map<String, String> match : matchers) {
					String url = match.values().iterator().next();
					this.saveLink(site, url, new Integer(5), crawlResult);
				}
				crawlResult.setIsParse(Boolean.TRUE);
				// this.crawlResultService.save(crawlResult);
			} else {
				// this.crawlResultService.remove(crawlResult);
				crawlResult.setIsParseSuccess(Boolean.FALSE);
				// this.crawlResultService.save(crawlResult);
			}

		} catch (Exception e) {
			logger.error(">>--->" + e.getMessage());
			crawlResult.setIsParseSuccess(Boolean.FALSE);
		} finally {
			this.crawlResultService.save(crawlResult);
		}

	}

	/**
	 * 解析小说封面页 type=5.取得章节列表页第一页（type=7)，作者，简介页(type=6)
	 * @todo
	 * @param crawlResult
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月25日
	 */
	private void saveParseNovelHome(CrawlResult crawlResult) {
		String path = crawlResult.getStorePath();
		String content = "";
		try {
			content = FileUtil.getInstance().read(path);
		} catch (IOException e1) {
			logger.error(">>FaceYe ---> io excetoin ,crawl result id "+crawlResult.getId()+",store path:"+crawlResult.getStorePath() + e1.getMessage());
		}
		if (StringUtils.isNotEmpty(content)) {
			try {

				// 提取书名
				List<Map<String, String>> titleDistill = RegexpUtil.match(content, RegexpConstants.DISTIAL_HTML_TITILE);
				String bookName = "";
				String title = "";
				if (CollectionUtils.isNotEmpty(titleDistill)) {
					title = titleDistill.get(0).values().iterator().next();
					String[] tArray = title.split("_");
					if (tArray != null && tArray.length > 0) {
						bookName = tArray[0];
					}
				}
				if (StringUtils.isEmpty(bookName)) {
					logger.debug(">>FaceYe--> Book name is empty of  crawl result id:"+crawlResult.getId()+",store path:"+crawlResult.getStorePath());
					crawlResult.setIsParseSuccess(Boolean.FALSE);
					crawlResult.setIsParse(Boolean.TRUE);
					this.crawlResultService.save(crawlResult);
					return;
				}
				Book book = this.bookService.getBookByName(bookName);
				String regexp = "";
				if (book == null) {
					book = new Book();
					// 提取小说分类：
					// /.<a href="/c/s.m?esid=f-OazgXD4mXbX5-XD2&amp;f=3&amp;q=kehuan&amp;fr=3.1.2.5">科幻</a></p>
					regexp = "\\.<a[\\s]href=\"\\/c\\/s\\.m.+?\">(.+?)</a>";
					String categoryName = "";
					List<Map<String, String>> categoryMatch = RegexpUtil.match(content, regexp);
					if (CollectionUtils.isNotEmpty(categoryMatch)) {
						categoryName = categoryMatch.get(0).values().iterator().next();
					}
					Category category = this.categoryService.getCategoryByName(categoryName);
					if (category == null && StringUtils.isNotEmpty(categoryName)) {
						category = new Category();
						category.setName(categoryName);
						this.categoryService.save(category);
					}else{
						logger.debug(">>FaceYe have not got book category,crawl result id is:"+crawlResult.getId()+",store path:"+crawlResult.getStorePath());
					}
					book.setCategory(category);

					// 提取作者：
					// <a href="/c/s.m?esid=f-OazgXD4mXbX5-XD2&amp;q=车辙背影&amp;r=16&amp;f=2&amp;fr=3.1.2.3">车辙背影</a>
					regexp = ":<a[\\s]href=\"\\/c\\/s\\.m.+?\">(.+?)</a>";
					List<Map<String, String>> autherMatch = RegexpUtil.match(content, regexp);
					String author = "";
					if (CollectionUtils.isNotEmpty(autherMatch)) {
						author = autherMatch.get(0).values().iterator().next();
					}else{
						logger.debug(">>FaceYe --> have not got book author,crawl result id is :"+crawlResult.getId()+",store path:"+crawlResult.getStorePath());
					}
					book.setAuthor(author);
					book.setName(bookName);
//					book.setCrawlResult(crawlResult);
					this.bookService.save(book);

					// 提取简介明细页 type=6
					String descriptionUrl = "";
					regexp = "<a[\\s]href=\"(\\/c\\/novel\\.m.+?)\">.+?</a>";
					List<Map<String, String>> descriptionDetail = RegexpUtil.match(content, regexp);
					if (CollectionUtils.isNotEmpty(descriptionDetail)) {
						descriptionUrl = descriptionDetail.get(0).values().iterator().next().toString();
					}
					if (StringUtils.isNotEmpty(descriptionUrl)) {
						Site site = this.siteService.get(2L);
						this.saveLink(site, descriptionUrl, 6, crawlResult);
					}else{
						logger.debug(">>FaceYe --> have not got book description url,crawl result id is :"+crawlResult.getId()+",store path:"+crawlResult.getStorePath());
					}
				}
				// 提取章节列表页首页
				// |<a href="/c/clist.m?esid=f-OazgXD4mXbX5-XD2&amp;gid=14088460&amp;nid=14190172&amp;p=1&amp;fr=3.1.2.2">查看目录</a>|
				regexp = "\\|<a[\\s]href=\"(\\/c\\/clist\\.m.+?)\">.+?</a>\\|";
				List<Map<String, String>> sectionFirstListPageMatch = RegexpUtil.match(content, regexp);
				String sectionFirstListPageUrl = "";
				if (CollectionUtils.isNotEmpty(sectionFirstListPageMatch)) {
					sectionFirstListPageUrl = sectionFirstListPageMatch.get(0).values().iterator().next().toString();
				}else{
					logger.debug(">>FaceYe --> have not got book firset section url ,crawl result id is :"+crawlResult.getId()+",store path:"+crawlResult.getStorePath());
				}
				if (StringUtils.isNotEmpty(sectionFirstListPageUrl)) {
					Site site = this.siteService.get(2L);
					this.saveLink(site, sectionFirstListPageUrl, 7, crawlResult);
				}

				crawlResult.setIsParse(Boolean.TRUE);
				// this.crawlResultService.save(crawlResult);
				// 提取封面图片URL

			} catch (Exception e) {
				logger.error(">>--->" + e.getMessage());
				crawlResult.setIsParseSuccess(Boolean.FALSE);
			} finally {
				// crawlResult.setIsParseSuccess(Boolean);
				this.crawlResultService.save(crawlResult);
			}
		} else {
			// this.crawlResultService.remove(crawlResult);
			crawlResult.setIsParseSuccess(Boolean.FALSE);
			this.crawlResultService.save(crawlResult);
		}
		// String regex = "<a[\\s]href=\"(\\/c\\/show.+?)\"\\s>.+?</a>";
	}

	/**
	 * 解析章节列表页首页(type=7)，拼装小说所有章节列表页(type=1)
	 * Exam:http://b.easou.com/c/clist.m?esid=fAybiiXD4hXbX5-XD2&gid=3217447&nid=1003217447&p=1&gst=1&fr=3.1.2.2
	 * @todo
	 * @param crawlResult
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月25日
	 */
	private void saveParseSectionHomePage(CrawlResult crawlResult) {
		String path = crawlResult.getStorePath();
		String content = "";
		try {
			content = FileUtil.getInstance().read(path);
		} catch (IOException e1) {
			logger.error(">>--->" + e1.getMessage());
		}
		if (StringUtils.isEmpty(content)) {
			return;
		}
		String title = this.distillHtmlTitle(content);
		String[] array = title.split("_");
		String bookName = "";
		if (array != null && array.length > 0) {
			bookName = array[0];
		}
		// <a href="/c/clist.m?esid=fAybiiXD4hXbX5-XD2&amp;gid=3217447&amp;nid=1003217447&amp;or=0&amp;gst=1&amp;p=357">末页</a>
		String regexp = "<a[\\s]href=\"(\\/c\\/clist[^>]+?p=\\d{1,9})\">[^>]+?</a>";
		try {
			List<Map<String, String>> pageUrlMatchers = RegexpUtil.match(content, regexp);
			if (CollectionUtils.isNotEmpty(pageUrlMatchers) && pageUrlMatchers.size() == 2) {
				String url = pageUrlMatchers.get(1).values().iterator().next().toString();
				regexp = ".+?p=(\\d{1,9})";
				List<Map<String, String>> totalPageMatcher = RegexpUtil.match(url, regexp);
				int totalPage = 0;
				if (CollectionUtils.isNotEmpty(totalPageMatcher)) {
					String pageNum = totalPageMatcher.get(0).values().iterator().next();
					if (StringUtils.isNotEmpty(pageNum)) {
						totalPage = Integer.parseInt(pageNum);
					}
				}
				// 去除URL中的分页参数
				url = url.replaceAll("&amp;p=\\d{1,9}", "");
				if (totalPage != 0) {
					Site site = this.siteService.get(2L);
					for (int i = 1; i <= totalPage; i++) {
						String _url = url + "&amp;p=" + i;
						this.saveLink(site, _url, 1, crawlResult);
					}
				}
				crawlResult.setIsParse(Boolean.TRUE);
				// this.crawlResultService.save(crawlResult);
			} else {
				crawlResult.setIsParseSuccess(Boolean.FALSE);
				// this.crawlResultService.save(crawlResult);
			}

		} catch (Exception e) {
			logger.error(">>--->" + e.getMessage());
			crawlResult.setIsParseSuccess(Boolean.FALSE);
		} finally {
			this.crawlResultService.save(crawlResult);
		}

		// String regexp = "<a[\\s]href=\"(\\/c\\/show.+?)\"\\s>.+?</a>";
	}

	/**
	 * 解析小说章节列表页
	 * @todo type =1 ->章节明细:type=2.
	 * @param crawlResult
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月23日
	 */
	private void saveBookSectionListPageParseResult(CrawlResult crawlResult) {
		String path = crawlResult.getStorePath();
		String content = "";
		try {
			content = FileUtil.getInstance().read(path);
		} catch (IOException e1) {
			logger.error(">>--->" + e1.getMessage());
		}
		String regex = "<a[\\s]href=\"(\\/c\\/show.+?)\"\\s>.+?</a>";
		try {
			if (StringUtils.isNotEmpty(content)) {
				List<Map<String, String>> matchers = RegexpUtil.match(content, regex);
				if (CollectionUtils.isNotEmpty(matchers)) {
					Site site = siteService.get(2L);
					for (Map<String, String> match : matchers) {
						String url = match.values().iterator().next();
						// 存储 章节明细URL
						this.saveLink(site, url, 2, crawlResult);
					}
				}
				crawlResult.setIsParse(Boolean.TRUE);
				// this.crawlResultService.save(crawlResult);
			} else {
				// this.crawlResultService.remove(crawlResult);
				crawlResult.setIsParseSuccess(Boolean.FALSE);
				// this.crawlResultService.save(crawlResult);
			}
		} catch (Exception e) {
			logger.error(">>--->" + e.getMessage());
			crawlResult.setIsParseSuccess(Boolean.FALSE);
		} finally {
			this.crawlResultService.save(crawlResult);
		}
	}

	/**
	 * 解析小说章节详情
	 * @todo
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月23日
	 */
	private void saveParseSectionDetail(CrawlResult crawlResult) {
		String path = crawlResult.getStorePath();
		String content = "";
		try {
			content = FileUtil.getInstance().read(path);
		} catch (IOException e1) {
			logger.error(">>--->" + e1.getMessage());
		}
		if (StringUtils.isEmpty(content)) {
			this.crawlResultService.remove(crawlResult);
			return;
		}
		String regexp = "<p\\s\\sid=\"block1141\"\\sclass=\"easou_tit2\">(.+?)</p><div\\sid=\"block1142\"\\sclass=\"easou_con\">(.+?)</div>";
		List<Map<String, String>> matchers;
		try {
			String title = this.distillHtmlTitle(content);
			String[] array = null;
			String bookName = "";
			if (title != null) {
				array = title.split("_");
				if (array != null && array.length > 0) {
					bookName = array[0];
				}
			}
			Book book = this.bookService.getBookByName(bookName);
			if (book == null) {
				book = new Book();
				book.setName(bookName);
				this.bookService.save(book);
			}
			matchers = RegexpUtil.match(content, regexp);
			if (CollectionUtils.isNotEmpty(matchers) && matchers.size() == 1) {
				// 取得章节的标题
				Map<String, String> sectionMap = matchers.get(0);
				String sectionTitle = sectionMap.get("1");
				String sectionIndex = "0";
				regexp = ".+(\\d{1,9}).+";
				List<Map<String, String>> sectionIndexMatcher = RegexpUtil.match(sectionTitle, regexp);
				if (CollectionUtils.isNotEmpty(sectionIndexMatcher)) {
					sectionIndex = sectionIndexMatcher.get(0).values().iterator().next().toString();
				}
				// 如果从章节标题中无法取得章节编号，则从title中取章节编号
				if (StringUtils.equals(sectionIndex, "0") && array != null && array.length > 2) {
					sectionIndex = array[1];
				}
				// 取得章节内容
				String sectionContent = sectionMap.get("2");
				// 准备存储章节内容
				// 判断章节是否已存在
				Integer indexNum = Integer.parseInt(sectionIndex);
				Map<String, Object> searchParams = new HashMap<String, Object>(0);
				searchParams.put("EQ|name", sectionTitle);
				searchParams.put("EQ|book.id", book.getId());
				Page<Section> page = this.sectionService.getPage(searchParams, 1, 10);
				if (page == null || CollectionUtils.isEmpty(page.getContent())) {
					Section section = new Section();
					section.setContent(sectionContent);
					section.setName(sectionTitle);
					section.setIndexNum(indexNum);
					section.setBook(book);
					section.setCreateDate(new Date());
					this.sectionService.save(section);
				}
				crawlResult.setIsParse(Boolean.TRUE);
				crawlResult.setIsParse(Boolean.TRUE);
				// this.crawlResultService.save(crawlResult);
			} else {
				// this.crawlResultService.remove(crawlResult);
				crawlResult.setIsParseSuccess(Boolean.FALSE);
				crawlResult.setIsParse(Boolean.TRUE);
				// this.crawlResultService.save(crawlResult);
				logger.error(">>FaceYe distill Failer:store path is:" + path);
			}
		} catch (Exception e) {
			crawlResult.setIsParse(Boolean.TRUE);
			crawlResult.setIsParseSuccess(Boolean.FALSE);
			logger.error(">>--->" + e.getMessage());
		} finally {
			this.crawlResultService.save(crawlResult);
		}

	}

	/**
	 * 提取网页标题，返回小说名称
	 * @todo
	 * @param content
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月23日
	 */
	// private String[] distillHtmlTitle(String content) {
	// String[] res = null;
	// if (StringUtils.isNotEmpty(content)) {
	// try {
	// List<Map<String, String>> matchers = RegexpUtil.match(content, RegexpConstants.DISTIAL_HTML_TITILE);
	// if (CollectionUtils.isNotEmpty(matchers)) {
	// String title = matchers.get(0).values().iterator().next();
	// res = title.split("_");
	// }
	// if (res == null) {
	// logger.error(">>FaceYe distill easou novel title failer.");
	// }
	// } catch (Exception e) {
	// logger.error(">>--->" + e.getMessage());
	// }
	// }
	// return res;
	// }
	/**
	 * 提取网页标题
	 * @todo
	 * @param content
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月25日
	 */
	private String distillHtmlTitle(String content) {
		String res = "";
		if (StringUtils.isNotEmpty(content)) {
			try {
				List<Map<String, String>> matchers = RegexpUtil.match(content, RegexpConstants.DISTIAL_HTML_TITILE);
				if (CollectionUtils.isNotEmpty(matchers)) {
					String title = matchers.get(0).values().iterator().next();
					res = title;
				}
				if (res == null) {
					logger.error(">>FaceYe distill easou novel title failer.");
				}
			} catch (Exception e) {
				logger.error(">>--->" + e.getMessage());
			}
		}
		return res;
	}

	/**
	 * 保存链接明细
	 * @todo
	 * @param site
	 * @param url
	 * @param type
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月23日
	 */
	private void saveLink(Site site, String url, Integer type, CrawlResult crawlResult) {
		if (StringUtils.isNotEmpty(url)) {
			url = url.replace("&amp;", "&");
			url = site.getUrl() + url;
		} else {
			return;
		}
		Boolean isExist = linkService.isLinkExist(url);
		if (!isExist) {
			Link parent = null;
			if (crawlResult != null) {
//				parent = crawlResult.getLink();
			}
			Link link = new Link();
			link.setCreateDate(new Date());
			link.setIsCrawled(Boolean.FALSE);
			link.setIsCrawlSuccess(Boolean.FALSE);
			link.setType(type);
			link.setUrl(url);
//			link.setSite(site);
			link.setParentId(parent == null ? null : parent.getId());
			this.linkService.save(link);
		}
	}

	@Override
	protected String getDomain() {
		return null;
	}

	@Override
	protected boolean parse(CrawlResult crawlResult, String content, Integer type) {
		return false;
	}

	@Override
	protected void wrapParseResult(ParseResult parseResult) {
	}

}
