package com.faceye.component.parse.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.doc.ParseResult;
import com.faceye.component.parse.service.MetaData;
import com.faceye.component.parse.service.ParseService;
import com.faceye.component.parse.util.RegexpConstants;
import com.faceye.component.parse.util.RegexpUtil;
import com.faceye.component.spider.doc.CrawlResult;
import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.service.domain.DomainLink;
import com.faceye.component.spider.service.domain.LinkBuilder;

@Service("OSChinaParseServiceImpl")
public class OSChinaParseServiceImpl extends BaseParseServiceImpl implements ParseService {
	@Value("#{property['domain.oschina.net']}")
	private String domain = "";

	protected String getDomain() {
		return domain;
	}

	/**
	 * 对爬结果进行解析
	 * @todo
	 * @param crawlResult
	 * @param content
	 * @param type
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月4日
	 */
	protected boolean parse(CrawlResult crawlResult, String content, Integer type) {
		boolean res = false;
		if (null != type && type.intValue() == 1) {
			res = this.parseLinks(content);
		}
		if (null != type && type.intValue() == 2) {
			String mainContent = this.parseMainContent(content);
			MetaData meta = this.distillMeta(content);
			String title = this.distillTitle(content);
			if (StringUtils.isNotEmpty(title)) {
				title = title.split(" - ")[0];
			}
			if (StringUtils.isNotEmpty(mainContent)) {
				res = true;
				this.saveParseResult(crawlResult, meta, title, mainContent);
			}
		}
		return res;
	}

	

	/**
	 * Link type ==1,oschina 博客文章列表页
	 * Link tye ==2,oschina博客明细页
	 */
	// @Override
	// public void saveParseResult() {
	// Boolean isContinue = Boolean.TRUE;
	// while (isContinue) {
	// int loopStep = 0;
	// Map searchParams = new HashMap();
	// searchParams.put("EQ|link.site.id", new Integer(3));
	// searchParams.put("ISFALSE|isParse", "0");
	// searchParams.put("EQ|link.type", new Integer(1));
	// // 解析 列表页type==1
	// Page<CrawlResult> page = this.crawlResultService.getPage(searchParams, 1, 1000);
	// if (null != page && CollectionUtils.isNotEmpty(page.getContent())) {
	// for (CrawlResult crawlResult : page.getContent()) {
	// this.saveAndParse(crawlResult, new Integer(1));
	// }
	// loopStep++;
	// }
	// // 解析明细页,type==2
	// page = null;
	// searchParams.put("EQ|link.type", new Integer(2));
	// page = this.crawlResultService.getPage(searchParams, 1, 1000);
	// if (null != page && CollectionUtils.isNotEmpty(page.getContent())) {
	// for (CrawlResult crawlResult : page.getContent()) {
	// this.saveAndParse(crawlResult, new Integer(2));
	// }
	// loopStep++;
	// }
	// if (loopStep == 0) {
	// isContinue = Boolean.FALSE;
	// }
	// }
	// }

	/**
	 * 解析列表页，link.type=1
	 * @todo
	 * @param crawlResult
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年8月13日
	 */
	private boolean parseLinks(String content) {
		boolean res = false;
		Site site = this.getSite();
		List<DomainLink> links = new ArrayList<DomainLink>();
		String regexp = "";
		try {
			// 提取明细页
			// <a href="http://my.oschina.net/Jacedy/blog/300872" target='_blank' title='Lambda表达式解析'>Lambda表达式解析</a>
			List<Map<String, String>> matchs = RegexpUtil.match(content, RegexpConstants.DISTILL_OSCHINA_BLOGS_LIST_LINKS);
			if (CollectionUtils.isNotEmpty(matchs)) {
				for (Map<String, String> map : matchs) {
					String url = map.values().iterator().next();
					LinkBuilder.getInstance().build(links, url, 2);
				}
			}
			matchs = null;
			matchs = RegexpUtil.match(content, RegexpConstants.DISTILL_OSCHINA_BLOG_CATEGORY_LINKS);
			if (CollectionUtils.isNotEmpty(matchs)) {
				for (Map<String, String> map : matchs) {
					String str = map.values().iterator().next().toString();
					String u = "http://www.oschina.net" + str;
					for (int i = 1; i <= 25; i++) {
						String url = u + "&p=" + i;
						LinkBuilder.getInstance().build(links, url, 1);
					}
				}
			}
		} catch (IOException e) {
			logger.error(">>--->" + e.getMessage());
		} catch (Exception e) {
			logger.error(">>--->" + e.getMessage());
		} finally {
			this.linkService.saveDomainLinks(links, site);
		}

		// 提取分类列表页
		// <a href="/blog?type=428602#catalogs" class='tag'>移动开发</a>
		return res;
	}

	/**
	 *  解析博客明细页 link.type=2
	 * @todo
	 * @param crawlResult
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年8月13日
	 */
//	private void saveAndparseDetail(CrawlResult crawlResult) {
//		String path = crawlResult.getStorePath();
//		String content;
//		try {
//			crawlResult.setIsParse(Boolean.TRUE);
//			content = FileUtil.getInstance().read(path);
//			if (StringUtils.isNotEmpty(content)) {
//				List<Map<String, String>> contentMatchs = RegexpUtil.match(content, RegexpConstants.DISTILL_OSCHINA_BLOG_DTAIL);
//				List<Map<String, String>> titleMatches = RegexpUtil.match(content, RegexpConstants.DISTIAL_HTML_TITILE);
//				MetaData meta = this.distillMeta(content);
//
//				if (CollectionUtils.isNotEmpty(contentMatchs) && CollectionUtils.isNotEmpty(titleMatches)) {
//					String fullTitle = titleMatches.get(0).values().iterator().next().toString();
//					String title = fullTitle.split(" - ")[0];
//					String detail = contentMatchs.get(0).values().iterator().next();
//
//					if (StringUtils.isNotEmpty(title) && StringUtils.isNotEmpty(detail)) {
//						title = StringUtils.trim(title);
//						detail = HtmlUtil.getInstance().replace(detail, RegexpConstants.REPLACE_ALL_A_HREF);
//						detail = HtmlUtil.getInstance().replace(detail, RegexpConstants.REPLACE_ALL_IMG);
//						boolean isStore = this.isStore(title, detail);
//						if (isStore) {
//							// 判断文章 标题是否相同，如果已存在相同标题，不再存储
//							List<ParseResult> haveSameNameParseResults = this.parseResultService.getParseResultsByName(title);
//							boolean isNotHaveSameNameParseResult = CollectionUtils.isEmpty(haveSameNameParseResults);
//							if (isNotHaveSameNameParseResult) {
//								logger.debug(">>Article will be save now:." + title);
//							}
//							if (isNotHaveSameNameParseResult) {
//								ArticleCategory category = this.buildArticleCategoryInitService.buildArticleCategory(title, detail);
//								if (null == category) {
//									// category = this.articleCategoryService.getArticleCategoryByAlias("other");
//								}
//								ParseResult parseResult = new ParseResult();
//								parseResult.setId(this.sequenceService.getNextSequence(ParseResult.class.getName()));
//								parseResult.setContent(detail);
//								parseResult.setSourceUrl(crawlResult.getLink().getUrl());
//								// parseResult.setCrawlResult(crawlResult);
//								parseResult.setCrawlResultId(crawlResult.getId());
//								parseResult.setCreateDate(new Date());
//								parseResult.setName(title);
//								parseResult.setCategoryAlias(category.getAlias());
//								parseResult.setCategoryId(category.getId());
//								parseResult.setCategoryName(category.getName());
//								parseResult.setIsPush2ProductEnv(false);
//								parseResult.setIsPush2Mongo(false);
//								parseResult.setKeywords(meta.get("keywords"));
//								parseResult.setDescription(meta.get("description"));
//								this.parseResultService.save(parseResult);
//								crawlResult.setIsParseSuccess(Boolean.TRUE);
//							}
//						}
//					}
//				}
//			}
//
//		} catch (IOException e) {
//			logger.error(">>--->" + e.getMessage());
//			crawlResult.setIsParseSuccess(Boolean.FALSE);
//		} catch (Exception e) {
//			logger.error(">>--->" + e.getMessage());
//			crawlResult.setIsParseSuccess(Boolean.FALSE);
//		} finally {
//			this.crawlResultService.save(crawlResult);
//		}
//
//	}

	private String parseMainContent(String content) {
		String mainContent = "";
		try {
			if (StringUtils.isNotEmpty(content)) {
				List<Map<String, String>> contentMatchs = RegexpUtil.match(content, RegexpConstants.DISTILL_OSCHINA_BLOG_DTAIL);
				MetaData meta = this.distillMeta(content);
				if (CollectionUtils.isNotEmpty(contentMatchs)) {
					mainContent = contentMatchs.get(0).values().iterator().next();
				}
			}

		} catch (IOException e) {
			logger.error(">>--->" + e.getMessage());
		} catch (Exception e) {
			logger.error(">>--->" + e.getMessage());
		} finally {
		}
		return mainContent;
	}

	

	@Override
	protected void wrapParseResult(ParseResult parseResult) {
	}

}
