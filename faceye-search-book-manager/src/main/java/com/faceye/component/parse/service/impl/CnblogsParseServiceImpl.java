package com.faceye.component.parse.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.faceye.component.parse.doc.ParseResult;
import com.faceye.component.parse.service.MetaData;
import com.faceye.component.parse.service.ParseService;
import com.faceye.component.parse.util.HtmlUtil;
import com.faceye.component.parse.util.RegexpConstants;
import com.faceye.component.parse.util.RegexpUtil;
import com.faceye.component.search.doc.ArticleCategory;
import com.faceye.component.spider.doc.CrawlResult;
import com.faceye.component.spider.doc.Site;
import com.faceye.component.spider.service.domain.DomainLink;
import com.faceye.component.spider.service.domain.LinkBuilder;
import com.faceye.component.spider.util.FileUtil;

@Service
public class CnblogsParseServiceImpl extends BaseParseServiceImpl implements ParseService {
	@Value("#{property['domain.cnblogs.com']}")
	private String domain = "";

	protected String getDomain() {
		return domain;
	}

	

	// @Override
	// public void saveParseResult() {
	// Boolean isContinue = Boolean.TRUE;
	// while (isContinue) {
	// int emptyCount = 0;
	// Map<String, Object> searchParams = new HashMap<String, Object>();
	// // 解析首页列表
	// searchParams.put("ISFALSE|isParse", "0");
	// searchParams.put("EQ|link.type", new Integer(1));
	// searchParams.put("EQ|link.site.id", new Long(1));
	// Page<CrawlResult> page = this.crawlResultService.getPage(searchParams, 1, 1000);
	// if (page != null && CollectionUtils.isNotEmpty(page.getContent())) {
	// for (CrawlResult crawlResult : page.getContent()) {
	// this.saveAndParse(crawlResult, 1);
	// }
	// } else {
	// emptyCount++;
	// }
	// // 解析详情页
	// searchParams.clear();
	// searchParams.put("ISFALSE|isParse", "0");
	// searchParams.put("EQ|link.type", new Integer(2));
	// searchParams.put("EQ|link.site.id", new Long(1));
	// page = this.crawlResultService.getPage(searchParams, 1, 1000);
	// if (page != null && CollectionUtils.isNotEmpty(page.getContent())) {
	// for (CrawlResult crawlResult : page.getContent()) {
	// this.saveAndParse(crawlResult, 2);
	// }
	// } else {
	// emptyCount++;
	// }
	// // 解析园子表
	// // http://home.cnblogs.com/blog/page/2/
	// searchParams.clear();
	// searchParams.put("ISFALSE|isParse", "0");
	// searchParams.put("EQ|link.type", new Integer(3));
	// searchParams.put("EQ|link.site.id", new Long(1));
	// page = this.crawlResultService.getPage(searchParams, 1, 1000);
	// if (page != null && CollectionUtils.isNotEmpty(page.getContent())) {
	// for (CrawlResult crawlResult : page.getContent()) {
	// this.saveAndParse(crawlResult, 3);
	// }
	// } else {
	// emptyCount++;
	// }
	// if (emptyCount == 3) {
	// isContinue = Boolean.FALSE;
	// }
	// }
	//
	// }

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
		// 解析首页列表
		if (type != null && type.intValue() == 1) {
			res = this.saveParseLinks(content);
		} else if (type != null && type.intValue() == 2) {
		    res=this.saveParseLinks(content);
			String mainContent = this.parseMainContent(content);
			MetaData meta = this.distillMeta(content);
			String title = this.distillTitle(content);
			if (StringUtils.isNotEmpty(title)) {
				StringBuilder sb = new StringBuilder();
				String[] tArray = title.split("-");
				if (tArray != null && tArray.length >= 3) {
					for (int i = 0; i < tArray.length; i++) {
						if (i < tArray.length - 2) {
							sb.append(tArray[i]);
						}
					}
					title = sb.toString();
				}
			}
			if (StringUtils.isNotEmpty(mainContent)) {
				res = true;
				this.saveParseResult(crawlResult, meta, title, mainContent);
			}
		} else if (type != null && type.intValue() == 3) {
			res = this.saveBlogHomeBestLinks(content);
		}
		return res;
	}

	/**
	 * 解析首页列表
	 * @todo
	 * @param regexp
	 * @param crawlResult
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月10日
	 */
	private boolean saveParseLinks(String content) {
		boolean res = false;
		String regexp = "<a\\sclass=\"titlelnk\"\\shref=\"(.+?)\"\\starget=\"_blank\">.+?</a>";
		List<DomainLink> links = new ArrayList<DomainLink>();
		Site site = this.getSite();
//		if (StringUtils.isNotEmpty(content)) {
//			try {
//				List<Map<String, String>> urls = RegexpUtil.match(content, regexp);
//				if (CollectionUtils.isNotEmpty(urls)) {
//					res = true;
//					for (Map<String, String> map : urls) {
//						String url = map.values().iterator().next();
//						LinkBuilder.getInstance().build(links, url, 2);
//					}
//				}
//			} catch (Exception e) {
//				logger.error(">>--->" + e.getMessage());
//			} finally {
//				this.linkService.saveDomainLinks(links, site);
//			}
//		}
		List<Map<String,String>> urls=HtmlUtil.getInstance().getLinks(content);
		if(CollectionUtils.isNotEmpty(urls)){
			for(Map<String,String>map:urls){
				String url=map.get("1");
				if(StringUtils.isNotEmpty(url)){
					if(StringUtils.startsWith(url, "http")&&!StringUtils.contains(url, "#")&&!StringUtils.contains(url, "login.aspx")){
						//博客文章链接：http://www.cnblogs.com/cenalulu/p/4286415.html
						// http:\/\/www.cnblogs.com\/[A-Za-z0-9_].+\/p\/[0-9].+.html
						String regexp1="http:\\/\\/www.cnblogs.com\\/[A-Za-z0-9_].+\\/p\\/.+.html";
						//博客文章翻页列表链接：http://www.cnblogs.com/cenalulu/default.html?page=2
//						"http:\/\/www\.cnblogs\.com\/[a-zA-Z0-9_].+\/default\.html[.+]";
						//http:\/\/www.cnblogs.com\/[A-Za-z0-9_].+\/default.html.+
						String regexp2="http:\\/\\/www.cnblogs.com\\/[A-Za-z0-9_].+\\/default.html.+";
						//个人博客首页链接:http://www.cnblogs.com/cenalulu/
						//"http:\/\/www\.cnblogs\.com\/[a-zA-Z0-9_].+\/";
						String regexp3=regexp="http:\\/\\/www\\.cnblogs\\.com\\/[a-zA-Z0-9_].+\\/";
						if(RegexpUtil.isMatch(url, regexp1)){
							LinkBuilder.getInstance().build(links, url, 2);
						}else if(RegexpUtil.isMatch(url, regexp2)){
							LinkBuilder.getInstance().build(links, url, 1);
						}else if (RegexpUtil.isMatch(url, regexp3)){
							LinkBuilder.getInstance().build(links, url, 1);
						}
					}
				}
			}
		}
		this.linkService.saveDomainLinks(links, site);
		return res;
	}

	

	private String parseMainContent(String content) {
		String mainContent = "";
		if (StringUtils.isNotEmpty(content)) {
			try {
				List<Map<String, String>> bodyMatchs = RegexpUtil.match(content, RegexpConstants.DISTIL_CNBLOGS_BODY);
				if (CollectionUtils.isNotEmpty(bodyMatchs)) {
					mainContent = bodyMatchs.get(0).values().iterator().next();
					// 提取的内容div未闭合,增加闭合标签
					mainContent = "<div>" + mainContent;
					// 去掉内容中所有的a标签
					mainContent = HtmlUtil.getInstance().replace(mainContent, RegexpConstants.REPLACE_ALL_A_HREF);
					// 替换Img标签的正则表达式
					String imgRegexp = "<img[^>]*?>";
					mainContent = HtmlUtil.getInstance().replace(mainContent, RegexpConstants.REPLACE_ALL_IMG);
				}
			} catch (Exception e) {
				logger.error(">>--->" + e.getMessage());
			} finally {
			}
		}
		return mainContent;
	}

	/**
	 * 提取博客园首页列表中的明细URL
	 * @todo
	 * @param crawlResult
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月11日
	 */
	private boolean saveBlogHomeBestLinks(String content) {
		boolean res = false;
		Site site = this.getSite();
		List<DomainLink> links = new ArrayList<DomainLink>();
		try {
			List<Map<String, String>> matches = RegexpUtil.match(content, RegexpConstants.DISTIL_CNBLOGS_LIST_LINKS);
			res = CollectionUtils.isNotEmpty(matches);
			if (CollectionUtils.isNotEmpty(matches)) {
				for (Map<String, String> map : matches) {
					String url = map.values().iterator().next();
					if (StringUtils.isNotEmpty(url) && StringUtils.endsWith(url, "html")) {
						LinkBuilder.getInstance().build(links, url, 2);
					}
				}
			}

		} catch (Exception e) {
			logger.error(">>--->" + e.getMessage());
		} finally {
			this.linkService.saveDomainLinks(links, site);
		}
		return res;
	}

	@Override
	protected void wrapParseResult(ParseResult parseResult) {
	}

}
